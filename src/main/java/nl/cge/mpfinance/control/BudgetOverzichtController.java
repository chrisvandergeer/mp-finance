package nl.cge.mpfinance.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import nl.cge.mpfinance.entity.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

public class BudgetOverzichtController {

    @PersistenceContext
    private EntityManager entityManager;

    public List<BudgetGroepTotaalDto> maak() {
        BudgetGroepTotaalList budgetGroepTotaalList = new BudgetGroepTotaalList();
        List<BudgetGroep> budgetGroepList = findBudgetgroepen();
        // bepalen begin en einddatum
        Periode periode = bepaalOverzichtsPeriode();
        findTransakties(periode)
                .stream()
                .collect(Collectors.groupingBy(t -> getBudgetGroep(budgetGroepList, t)))
                .forEach((key, value) -> value.forEach(t -> budgetGroepTotaalList.addBudgetMaandTotaal(
                        key,
                        t.getDatum().format(DateTimeFormatter.ofPattern("yyyy-MM")),
                        t.getBedrag())));
        budgetGroepTotaalList.fillGaps(periode);
        return budgetGroepTotaalList.getBudgetGroepTotaalDtoList();
    }

    public List<BudgetGroepTotaalDto> maak(String budgetgroepNaam) {
        BudgetGroepTotaalList budgetGroepTotaalList = new BudgetGroepTotaalList();
        List<String> budgetNamen = findBudgetnamenBijBudgetgroep(budgetgroepNaam);
        Periode periode = bepaalOverzichtsPeriode();
        findTransakties(periode, budgetNamen)
                .stream()
                .collect(Collectors.groupingBy(Transaktie::getBudget))
                .forEach((key, value) -> value.forEach(t -> budgetGroepTotaalList.addBudgetMaandTotaal(
                        key,
                        t.getDatum().format(DateTimeFormatter.ofPattern("yyyy-MM")),
                        t.getBedrag())));
        budgetGroepTotaalList.fillGaps(periode);
        return budgetGroepTotaalList.getBudgetGroepTotaalDtoList();
    }

    private List<BudgetGroep> findBudgetgroepen() {
        List<BudgetGroep> budgetGroepList = entityManager
                .createNamedQuery(BudgetGroep.JPQL_FIND_ALL, BudgetGroep.class)
                .getResultList();
        return budgetGroepList;
    }

    private List<Transaktie> findTransakties(Periode periode, List<String> budgetNamen) {
        return entityManager
                .createNamedQuery(Transaktie.JPQL_FIND_BY_BUDGETGROEP_BETWEEN_DATES, Transaktie.class)
                .setParameter("begindatum", periode.getBegindatum())
                .setParameter("einddatum", periode.getEinddatum())
                .setParameter("budgetnamen", budgetNamen)
                .getResultList();
    }

    private List<String> findBudgetnamenBijBudgetgroep(String budgetgroepNaam) {
        List<String> budgetNamen = entityManager
                .createNamedQuery(BudgetGroep.JPQL_FIND_BY_NAME, BudgetGroep.class)
                .setParameter("budgetgroep", budgetgroepNaam)
                .getSingleResult()
                .getBudgetNamen()
                .stream()
                .map(BudgetNaam::getNaam)
                .collect(Collectors.toList());
        return budgetNamen;
    }

    private Periode bepaalOverzichtsPeriode() {
        Transaktie meestRecenteTransaktie = findMeestRecenteTransaktie();
        LocalDate begindatum = meestRecenteTransaktie.getDatum().withDayOfMonth(1).minusYears(1).plusMonths(4);
        LocalDate einddatum = meestRecenteTransaktie.getDatum().with(TemporalAdjusters.lastDayOfMonth());
        Periode periode = new Periode(begindatum, einddatum);
        return periode;
    }

    private List<Transaktie> findTransakties(Periode periode) {
        return entityManager
                .createNamedQuery(Transaktie.JPQL_FIND_BETWEEN_DATES, Transaktie.class)
                .setParameter("begindatum", periode.getBegindatum())
                .setParameter("einddatum", periode.getEinddatum())
                .getResultList();
    }

    private String getBudgetGroep(List<BudgetGroep> budgetGroepList, Transaktie transaktie) {
        return budgetGroepList.stream().filter(budgetGroep -> budgetGroep.bevatBudgetnaam(transaktie.getBudget()))
                .map(BudgetGroep::getNaam)
                .findAny()
                .orElse("!geen budget!");
    }

    private Transaktie findMeestRecenteTransaktie() {
        return entityManager.createNamedQuery(Transaktie.JPQL_FIND_MEESTRECENTE_TRANSAKTIE, Transaktie.class)
                .getSingleResult();
    }
}
