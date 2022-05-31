package nl.cge.mpfinance.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BudgetMaandTotaalDto implements Comparable<BudgetMaandTotaalDto> {

    private String maand;
    private BigDecimal totaalBedrag;

    public void addBedrag(BigDecimal bedrag) {
        totaalBedrag = totaalBedrag.add(bedrag);
    }

    @Override
    public int compareTo(BudgetMaandTotaalDto o) {
        return maand.compareTo(o.getMaand());
    }
}
