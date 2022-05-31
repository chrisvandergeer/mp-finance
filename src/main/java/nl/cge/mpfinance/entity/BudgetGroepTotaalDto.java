package nl.cge.mpfinance.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.TreeSet;

@Getter
@Setter
public class BudgetGroepTotaalDto {

    private String naam;
    private Set<BudgetMaandTotaalDto> budgetMaandTotaalDtoList = new TreeSet<>();
}
