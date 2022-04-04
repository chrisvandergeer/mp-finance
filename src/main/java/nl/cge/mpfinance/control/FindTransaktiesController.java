package nl.cge.mpfinance.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import nl.cge.mpfinance.entity.Transaktie;
import nl.cge.mpfinance.entity.TransaktiesResult;

public class FindTransaktiesController {

    @PersistenceContext
    private EntityManager entityManager;

    public TransaktiesResult find() {
        return new TransaktiesResult()
                .setTransakties(entityManager
                        .createNamedQuery(Transaktie.JQPL_FIND_TRANSAKTIES, Transaktie.class)
                        .setMaxResults(200)
                        .getResultList());
    }
}
