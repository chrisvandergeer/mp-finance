package nl.cge.mpfinance.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
@Getter
@AllArgsConstructor
public class Periode {

    private LocalDate begindatum;
    private LocalDate einddatum;
}
