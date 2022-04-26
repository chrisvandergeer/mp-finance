package nl.cge.mpfinance.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import nl.cge.mpfinance.entity.Transaktie;
import nl.cge.mpfinance.entity.BudgetregelMetTransaktiesDto;

import java.util.List;

public class FindGelijksoortigeTransaktiesController {

    @PersistenceContext
    private EntityManager entityManager;

    public BudgetregelMetTransaktiesDto find(String volgnummer) {
        List<Transaktie> transaktieList = entityManager.createNamedQuery(Transaktie.JPQL_FIND_GELIJKSOORTIGE, Transaktie.class)
                .setParameter("volgnr", volgnummer)
                .getResultList();
        BudgetregelMetTransaktiesDto budgetregelMetTransakties = new BudgetregelMetTransaktiesDto().setTransakties(transaktieList);
        Transaktie transaktie = transaktieList.stream().findAny().orElseGet(Transaktie::new);
        if (transaktie.getTegenrekening() != null) {
            budgetregelMetTransakties.getBudgetregel().setTegenrekening(transaktie.getTegenrekening());
        } else {
            budgetregelMetTransakties.getBudgetregel().setNaamTegenpartij(transaktie.getNaamTegenpartij());
        }
        return budgetregelMetTransakties;
    }
}
