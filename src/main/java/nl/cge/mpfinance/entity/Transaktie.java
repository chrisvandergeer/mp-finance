package nl.cge.mpfinance.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString
public class Transaktie {

    private String volgnr;
    private LocalDate datum;
    private BigDecimal bedrag;
    private BigDecimal saldoNaTrn;
    private String tegenrekening;
    private String naamTegenpartij;
    private String omschrijving;

}
