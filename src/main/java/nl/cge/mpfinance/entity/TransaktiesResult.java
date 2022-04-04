package nl.cge.mpfinance.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)
@Getter
@Setter
public class TransaktiesResult {

    private List<Transaktie> transakties;
    private Budgetregel budgetregel;
}
