package nl.cge.mpfinance.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import nl.cge.mpfinance.entity.Transaktie;

import java.util.List;

public class FindTransaktiesController {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Transaktie> find() {
        return entityManager
                        .createNamedQuery(Transaktie.JQPL_FIND_ALL, Transaktie.class)
                        .setMaxResults(200)
                        .getResultList();
    }
}
