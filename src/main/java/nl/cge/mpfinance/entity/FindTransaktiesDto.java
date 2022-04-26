package nl.cge.mpfinance.entity;

import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FindTransaktiesDto {

    @QueryParam("tegenrekening")
    private String tegenrekening;

    @QueryParam("naamTegenpartij")
    private String naamTegenpartij;

    @QueryParam("omschrijving")
    private String omschrijving;

}
