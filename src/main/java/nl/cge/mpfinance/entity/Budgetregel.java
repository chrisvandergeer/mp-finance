package nl.cge.mpfinance.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@Setter
@ToString
public class Budgetregel {

    private String budgetregelNaam;
    private String tegenrekening;
    private String naamTegenpartij;
    private String omschrijving;
    private String budget;
    private String soort;
}
