package nl.cge.mpfinance.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import nl.cge.mpfinance.entity.Transaktie;
import nl.cge.mpfinance.entity.TransaktiesResult;

public class BudgeteerController {

    @PersistenceContext
    private EntityManager entityManager;

    public void budgeteer(TransaktiesResult budgetregelMetTransakties) {
        budgetregelMetTransakties.getTransakties().forEach(tr -> {
            Transaktie transaktie = entityManager.createNamedQuery(Transaktie.JPQL_FIND_BY_VOLGNUMMER, Transaktie.class)
                    .setParameter("volgnr", tr.getVolgnr())
                    .getSingleResult();
            transaktie.setBudget(budgetregelMetTransakties.getBudgetregel().getBudget());
            transaktie.setSoort(budgetregelMetTransakties.getBudgetregel().getSoort());
        });
        entityManager.flush();
    }
}
