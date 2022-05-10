package nl.cge.mpfinance.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class BudgetNaam {

    @Id
    private String naam;
}
