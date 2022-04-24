package nl.cge.mpfinance.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import nl.cge.mpfinance.entity.Budgetregel;
import nl.cge.mpfinance.entity.Transaktie;
import nl.cge.mpfinance.entity.TransaktiesResult;

import java.util.ArrayList;
import java.util.List;

public class FindGelijksoortigeTransaktiesController {

    @PersistenceContext
    private EntityManager entityManager;

    public TransaktiesResult find(String volgnummer, String naamTegenpartij, String omschrijving) {
        TransaktiesResult transaktiesResult = new TransaktiesResult();
        transaktiesResult.setBudgetregel(new Budgetregel());

        Transaktie transaktie = findTransaktieByVolgnummer(volgnummer);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Transaktie> criteriaQuery = criteriaBuilder.createQuery(Transaktie.class);
        Root<Transaktie> itemRoot = criteriaQuery.from(Transaktie.class);
        List<Predicate> predicateList = new ArrayList<>();
        if (transaktie.getTegenrekening() != null) {
            transaktiesResult.getBudgetregel().setTegenrekening(transaktie.getTegenrekening());
            predicateList.add(criteriaBuilder.equal(itemRoot.get("tegenrekening"), transaktie.getTegenrekening()));
        } else {
            String naamTegenpartijFilter = naamTegenpartij != null ? naamTegenpartij : transaktie.getNaamTegenpartij();
            transaktiesResult.getBudgetregel().setNaamTegenpartij(naamTegenpartijFilter);
            predicateList.add(criteriaBuilder.equal(itemRoot.get("naamTegenpartij"), naamTegenpartijFilter));
        }
        criteriaQuery.where(predicateList.toArray(new Predicate[]{}));
        List<Transaktie> transaktieList = entityManager.createQuery(criteriaQuery).getResultList();
        return transaktiesResult.setTransakties(transaktieList);
    }

    private Transaktie findTransaktieByVolgnummer(String volgnummer) {
        return entityManager.createNamedQuery(Transaktie.JPQL_FIND_BY_VOLGNUMMER, Transaktie.class)
                .setParameter("volgnr", volgnummer)
                .getSingleResult();
    }

//    public TransaktiesResult find(String volgnummer, String omschrijving) {
//        List<Transaktie> transaktieList = entityManager.createNamedQuery(Transaktie.JPQL_FIND_GELIJKSOORTIGE, Transaktie.class)
//                .setParameter("volgnummer", volgnummer)
//                .getResultList();
//        TransaktiesResult transaktiesResult = new TransaktiesResult()
//                .setTransakties(transaktieList)
//                .setBudgetregel(new Budgetregel()
//                        .setNaamTegenpartij(transaktieList.stream()
//                                .findAny()
//                                .map(Transaktie::getNaamTegenpartij)
//                                .orElse(null))
//                        .setOmschrijvingBevat(omschrijving));
//        if (omschrijving != null) {
//            transaktiesResult.setTransakties(transaktieList.stream()
//                    .filter(tr -> tr.getOmschrijving().toUpperCase().contains(omschrijving.toUpperCase()))
//                    .collect(Collectors.toList()));
//        }
//        return transaktiesResult;
//    }

}
