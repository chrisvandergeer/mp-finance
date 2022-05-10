package nl.cge.mpfinance.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import nl.cge.mpfinance.entity.Budgetregel;
import nl.cge.mpfinance.entity.BudgetregelMetTransaktiesDto;
import nl.cge.mpfinance.entity.FindTransaktiesDto;
import nl.cge.mpfinance.entity.Transaktie;

import java.util.ArrayList;
import java.util.List;

public class FindTransaktiesController {

    @PersistenceContext
    private EntityManager entityManager;

    public BudgetregelMetTransaktiesDto find(FindTransaktiesDto findTransaktiesDto) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Transaktie> criteriaQuery = criteriaBuilder.createQuery(Transaktie.class);
        Root<Transaktie> transaktieRoot = criteriaQuery.from(Transaktie.class);
        List<Predicate> predicateList = new ArrayList<>();
        if (findTransaktiesDto.getTegenrekening() != null && !"".equals(findTransaktiesDto.getTegenrekening().trim())) {
            predicateList.add(criteriaBuilder.equal(transaktieRoot.get("tegenrekening"), findTransaktiesDto.getTegenrekening()));
        }
        if (findTransaktiesDto.getNaamTegenpartij() != null && !"".equals(findTransaktiesDto.getNaamTegenpartij().trim())) {
            predicateList.add(criteriaBuilder.equal(transaktieRoot.get("naamTegenpartij"), findTransaktiesDto.getNaamTegenpartij()));
        }
        if (findTransaktiesDto.getOmschrijving() != null && !"".equals(findTransaktiesDto.getOmschrijving().trim())) {
            predicateList.add(criteriaBuilder.like(transaktieRoot.get("omschrijving"), "%" + findTransaktiesDto.getOmschrijving() + "%"));
        }
        criteriaQuery.where(predicateList.toArray(new Predicate[]{}));
        criteriaQuery.orderBy(criteriaBuilder.desc(transaktieRoot.get("volgnr")));
        return new BudgetregelMetTransaktiesDto().setTransakties(entityManager.createQuery(criteriaQuery)
                        .setMaxResults(200)
                        .getResultList())
                .setBudgetregel(new Budgetregel()
                        .setOmschrijving(findTransaktiesDto.getOmschrijving())
                        .setTegenrekening(findTransaktiesDto.getTegenrekening())
                        .setNaamTegenpartij(findTransaktiesDto.getNaamTegenpartij()));
    }
}
