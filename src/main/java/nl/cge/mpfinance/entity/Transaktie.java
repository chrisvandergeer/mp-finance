package nl.cge.mpfinance.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;

import static nl.cge.mpfinance.entity.Transaktie.*;

@Accessors(chain = true)
@Getter
@Setter
@ToString
@Entity
@Table(
        indexes = {
                @Index(name = "idx_transaktie_naamTegenpartij", columnList = "naamTegenpartij")
        }
)
@NamedQueries({
        @NamedQuery(
                name = JQPL_FIND_ALL,
                query = "select t from Transaktie t order by t.volgnr desc"),
        @NamedQuery(
                name = JPQL_FIND_BY_VOLGNUMMER,
                query = "select t from Transaktie t where t.volgnr = :volgnr"
        ),
        @NamedQuery(
                name = JPQL_FIND_GELIJKSOORTIGE,
                query = "select t from Transaktie t where t.naamTegenpartij = " +
                        "(select it.naamTegenpartij from Transaktie it where it.volgnr = :volgnummer) " +
                        "order by t.datum desc"
        )
})
public class Transaktie {

    public static final String JQPL_FIND_ALL = "Transaktie.findTransakties";
    public static final String JPQL_FIND_BY_VOLGNUMMER = "Transaktie.findByVolgnummer";
    public static final String JPQL_FIND_GELIJKSOORTIGE = "Transaktie.findGelijksoortigeTransakties";

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
