package nl.cge.mpfinance.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BudgetGroepTotaalList {

    private List<BudgetGroepTotaalDto> budgetGroepTotaalDtoList = new ArrayList<>();


    public void addBudgetMaandTotaal(String budgetGroepnaam, String maand, BigDecimal bedrag) {
        BudgetGroepTotaalDto budgetGroepTotaalDto = budgetGroepTotaalDtoList.stream()
                .filter(budgetGroepTotaal -> budgetGroepTotaal.getNaam().equals(budgetGroepnaam))
                .findAny()
                .orElseGet(() -> addBudgetGroepTotaal(budgetGroepnaam));
        BudgetMaandTotaalDto budgetMaandTotaalDto = budgetGroepTotaalDto.getBudgetMaandTotaalDtoList().stream()
                .filter(budgetMaandTotaal -> budgetMaandTotaal.getMaand().equals(maand))
                .findAny()
                .orElseGet(() -> addBudgetMaandTotaal(budgetGroepTotaalDto, maand));
        budgetMaandTotaalDto.addBedrag(bedrag);
    }

    private BudgetMaandTotaalDto addBudgetMaandTotaal(BudgetGroepTotaalDto budgetGroepTotaalDto, String maand) {
        BudgetMaandTotaalDto budgetMaandTotaalDto = new BudgetMaandTotaalDto();
        budgetMaandTotaalDto.setMaand(maand);
        budgetMaandTotaalDto.setTotaalBedrag(BigDecimal.ZERO);
        budgetGroepTotaalDto.getBudgetMaandTotaalDtoList().add(budgetMaandTotaalDto);
        return budgetMaandTotaalDto;
    }

    private BudgetGroepTotaalDto addBudgetGroepTotaal(String budgetGroepnaam) {
        BudgetGroepTotaalDto budgetGroepTotaalDto = new BudgetGroepTotaalDto();
        budgetGroepTotaalDto.setNaam(budgetGroepnaam);
        budgetGroepTotaalDtoList.add(budgetGroepTotaalDto);
        return budgetGroepTotaalDto;
    }

    public void fillGaps(Periode periode) {
        for (LocalDate datum = periode.getBegindatum(); !datum.isAfter(periode.getEinddatum()); datum = datum.plusMonths(1)) {
            String datumKey = datum.format(DateTimeFormatter.ofPattern("yyyy-MM"));
            budgetGroepTotaalDtoList.forEach(bml -> {
                boolean found = bml.getBudgetMaandTotaalDtoList().stream().anyMatch(bm -> bm.getMaand().equals(datumKey));
                if (!found) {
                    addBudgetMaandTotaal(bml.getNaam(), datumKey, BigDecimal.ZERO);
                }
            });
        }
    }
}
