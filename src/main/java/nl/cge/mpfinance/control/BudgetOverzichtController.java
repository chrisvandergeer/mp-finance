package nl.cge.mpfinance.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import nl.cge.mpfinance.entity.BudgetGroep;
import nl.cge.mpfinance.entity.BudgetGroepTotaalList;
import nl.cge.mpfinance.entity.Transaktie;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

public class BudgetOverzichtController {

    @PersistenceContext
    private EntityManager entityManager;

    public BudgetGroepTotaalList maak() {
        BudgetGroepTotaalList budgetGroepTotaalList = new BudgetGroepTotaalList();
        List<BudgetGroep> budgetGroepList = entityManager
                .createNamedQuery(BudgetGroep.JPQL_FIND_ALL, BudgetGroep.class)
                .getResultList();
        Transaktie meestRecenteTransaktie = findMeestRecenteTransaktie();
        findTransaktiesVanAfgelopenJaar(meestRecenteTransaktie)
                .stream()
                .filter(t -> t.getBudget() != null && !t.getBudget().trim().equals(""))
                .collect(Collectors.groupingBy(t -> getBudgetGroep(budgetGroepList, t)))
                .forEach((key, value) -> value.forEach(t -> budgetGroepTotaalList.addBudgetMaandTotaal(
                        key,
                        t.getDatum().format(DateTimeFormatter.ofPattern("yyyy-MM")),
                        t.getBedrag())));
        return budgetGroepTotaalList;
    }

    private List<Transaktie> findTransaktiesVanAfgelopenJaar(Transaktie meestRecenteTransaktie) {
        return entityManager
                .createNamedQuery(Transaktie.JPQL_FIND_BETWEEN_DATES, Transaktie.class)
                .setParameter("begindatum", meestRecenteTransaktie.getDatum().withDayOfMonth(1).minusYears(1).plusMonths(1))
                .setParameter("einddatum", meestRecenteTransaktie.getDatum().with(TemporalAdjusters.lastDayOfMonth()))
                .getResultList();
    }

    private String getBudgetGroep(List<BudgetGroep> budgetGroepList, Transaktie transaktie) {
        return budgetGroepList.stream().filter(budgetGroep -> budgetGroep.bevatBudgetnaam(transaktie.getBudget()))
                .map(BudgetGroep::getNaam)
                .findAny()
                .orElse("");
    }

    private List<Transaktie> findTransaktiesVoorMaand(LocalDate begindatum) {
        return entityManager.createNamedQuery(Transaktie.JPQL_FIND_BETWEEN_DATES, Transaktie.class)
                .setParameter("begindatum", begindatum)
                .setParameter("einddatum", begindatum.with(TemporalAdjusters.lastDayOfMonth()))
                .getResultList();
    }

    private Transaktie findMeestRecenteTransaktie() {
        return entityManager.createNamedQuery(Transaktie.JPQL_FIND_MEESTRECENTE_TRANSAKTIE, Transaktie.class)
                .getSingleResult();
    }
}
