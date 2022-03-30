package nl.cge.mpfinance.boundary;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.SneakyThrows;
import nl.cge.mpfinance.entity.Transaktie;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Produces(MediaType.APPLICATION_JSON)
@Path("transakties")
public class TransaktieResource {

    @Path("importeer")
    @GET
    public Response assemble() {
        List<Transaktie> result = new ArrayList<>();
        File[] files = new File("/home/chrisvandergeer/transakties").listFiles();
        if (files != null) {
            result = Arrays.stream(files)
                    .sorted()
                    .map(this::assemble)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
        }
        return Response.ok(result).build();
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
        return Transaktie.builder()
                .volgnr(splittedTransaktieRegel[3])
                .datum(LocalDate.parse(splittedTransaktieRegel[4], DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .bedrag(toBigDecimal(splittedTransaktieRegel[6]))
                .saldoNaTrn(toBigDecimal(splittedTransaktieRegel[7]))
                .tegenrekening(splittedTransaktieRegel[8])
                .naamTegenpartij(splittedTransaktieRegel[9])
                .omschrijving(splittedTransaktieRegel[19])
                .build();
    }

    private BigDecimal toBigDecimal(String bedrag) {
        return new BigDecimal(bedrag.replace("+","").replace(",", "."));
    }

    @GET
    public Response getTransakties() {
        Transaktie transaktie = Transaktie.builder()
                .naamTegenpartij("Chris")
                .volgnr("3")
                .build();
        return Response.ok(transaktie).build();
    }


}
