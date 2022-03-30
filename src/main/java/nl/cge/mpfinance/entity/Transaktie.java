package nl.cge.mpfinance.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import static nl.cge.mpfinance.entity.Transaktie.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@NamedQueries({
        @NamedQuery(
                name = JQPL_FIND_TRANSAKTIES,
                query = "select t from Transaktie t")
})
public class Transaktie {

    public static final String JQPL_FIND_TRANSAKTIES = "findTransakties";

    @Id
    private String volgnr;

    @Column(nullable = false)
    private LocalDate datum;

    @Column(nullable = false, scale = 2)
    private BigDecimal bedrag;

    @Column(nullable = false, scale = 2)
    private BigDecimal saldoNaTrn;

    @Column(length = 25)
    private String tegenrekening;

    @Column(length = 100)
    private String naamTegenpartij;

    @Column(nullable = false)
    private String omschrijving;

    @Column(length = 25)
    private String budget;

    @Column(length = 12)
    private String soort;

}
