package nl.cge.mpfinance.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@Setter
public class Budgetregel {

    private String budgetregelNaam;
    private String naamTegenpartij;
    private String omschrijvingBevat;
    private String budget;
    private String soort;
}