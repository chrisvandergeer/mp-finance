package nl.cge.mpfinance.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.SneakyThrows;
import nl.cge.mpfinance.entity.Transaktie;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ImporteerTransaktiesController {

    public static final String PATH_TO_TRANSAKTIE_CSVS = "/Users/Geerc01/ws/mygit/tr";
    @PersistenceContext
    private EntityManager entityManager;

    public void importeer() {
        File[] files = new File(PATH_TO_TRANSAKTIE_CSVS).listFiles();
        if (files != null) {
            List<Transaktie> transaktieList = Arrays.stream(files)
                    .sorted()
                    .map(this::assemble)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
            transaktieList.forEach(tr -> entityManager.persist(tr));
        }
    }

    @SneakyThrows
    private List<Transaktie> assemble(File transaktieFile) {
        return Files.readAllLines(transaktieFile.toPath()).stream()
                .skip(1)
                .map(this::assemble)
                .collect(Collectors.toList());
    }

    private Transaktie assemble(String transaktieRegel) {
        String[] splittedTransaktieRegel = transaktieRegel.split("\",\"");
        return new Transaktie()
                .setVolgnr(splittedTransaktieRegel[3])
                .setDatum(LocalDate.parse(splittedTransaktieRegel[4], DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .setBedrag(toBigDecimal(splittedTransaktieRegel[6]))
                .setSaldoNaTrn(toBigDecimal(splittedTransaktieRegel[7]))
                .setTegenrekening(getNullableString(splittedTransaktieRegel, 8))
                .setNaamTegenpartij(splittedTransaktieRegel[9])
                .setOmschrijving(splittedTransaktieRegel[19]);
    }

    private String getNullableString(String[] splittedTransaktieRegel, int idx) {
        String result = splittedTransaktieRegel[idx];
        return result.trim().equals("") ? null : result;
    }

    private BigDecimal toBigDecimal(String bedrag) {
        return new BigDecimal(bedrag.replace("+","").replace(",", "."));
    }
}
