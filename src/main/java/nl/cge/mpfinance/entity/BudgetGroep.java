package nl.cge.mpfinance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NamedQueries(value = {
        @NamedQuery(
                name = BudgetGroep.JPQL_FIND_ALL,
                query = "select bg from BudgetGroep bg"),
        @NamedQuery(
                name = BudgetGroep.JPQL_FIND_BY_NAME,
                query = "select bg from BudgetGroep bg where bg.naam = :budgetgroep")
})
public class BudgetGroep {

    public static final String JPQL_FIND_ALL = "BudgetGroep.findAll";
    public static final String JPQL_FIND_BY_NAME = "BudgetGroep.findByName";

    @Id
    private String naam;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "budgetGroep")
    private List<BudgetNaam> budgetNamen = new ArrayList<>();

    public boolean bevatBudgetnaam(String budgetnaam) {
        return budgetNamen.stream().anyMatch(b -> b.getNaam().equals(budgetnaam));
    }
}
