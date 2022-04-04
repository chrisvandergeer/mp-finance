package nl.cge.mpfinance.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import nl.cge.mpfinance.entity.Budgetregel;
import nl.cge.mpfinance.entity.Transaktie;
import nl.cge.mpfinance.entity.TransaktiesResult;

import java.util.List;
import java.util.stream.Collectors;

public class FindGelijksoortigeTransaktiesController {

    @PersistenceContext
    private EntityManager entityManager;

    public TransaktiesResult find(String volgnummer, String omschrijving) {
        List<Transaktie> transaktieList = entityManager.createNamedQuery(Transaktie.JPQL_FIND_GELIJKSOORTIGE_TRANSAKTIES, Transaktie.class)
                .setParameter("volgnummer", volgnummer)
                .getResultList();
        TransaktiesResult transaktiesResult = new TransaktiesResult()
                .setTransakties(transaktieList)
                .setBudgetregel(new Budgetregel()
                        .setNaamTegenpartij(transaktieList.stream()
                                .findAny()
                                .map(Transaktie::getNaamTegenpartij)
                                .orElse(null))
                        .setOmschrijvingBevat(omschrijving));
        if (omschrijving != null) {
            transaktiesResult.setTransakties(transaktieList.stream()
                    .filter(tr -> tr.getOmschrijving().toUpperCase().contains(omschrijving.toUpperCase()))
                    .collect(Collectors.toList()));
        }
        return transaktiesResult;
    }

}
