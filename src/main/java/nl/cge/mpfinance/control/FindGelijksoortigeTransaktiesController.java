package nl.cge.mpfinance.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import nl.cge.mpfinance.entity.Transaktie;

import java.util.List;

public class FindGelijksoortigeTransaktiesController {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Transaktie> find(String volgnummer) {
        return entityManager.createNamedQuery(Transaktie.JPQL_FIND_GELIJKSOORTIGE_TRANSAKTIES, Transaktie.class)
                .setParameter("volgnummer", volgnummer)
                .getResultList();
    }

}
