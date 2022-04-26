package nl.cge.mpfinance.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import nl.cge.mpfinance.entity.Transaktie;
import nl.cge.mpfinance.entity.TransaktiesResult;

import java.util.List;

public class FindGelijksoortigeTransaktiesController {

    @PersistenceContext
    private EntityManager entityManager;

    public TransaktiesResult find(String volgnummer) {
        List<Transaktie> transaktieList = entityManager.createNamedQuery(Transaktie.JPQL_FIND_GELIJKSOORTIGE, Transaktie.class)
                .setParameter("volgnr", volgnummer)
                .getResultList();
        TransaktiesResult transaktiesResult = new TransaktiesResult().setTransakties(transaktieList);
        Transaktie transaktie = transaktieList.stream().findAny().orElseGet(Transaktie::new);
        if (transaktie.getTegenrekening() != null) {
            transaktiesResult.getBudgetregel().setTegenrekening(transaktie.getTegenrekening());
        } else {
            transaktiesResult.getBudgetregel().setNaamTegenpartij(transaktie.getNaamTegenpartij());
        }
        return transaktiesResult;
    }
}
