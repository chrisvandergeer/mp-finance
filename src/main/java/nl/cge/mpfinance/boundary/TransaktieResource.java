package nl.cge.mpfinance.boundary;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.cge.mpfinance.control.FindTransaktiesController;
import nl.cge.mpfinance.control.ImporteerTransaktiesController;
import nl.cge.mpfinance.entity.Transaktie;

import java.util.List;

@Stateless
@Produces(MediaType.APPLICATION_JSON)
@Path("transakties")
public class TransaktieResource {

    @Inject
    private FindTransaktiesController findTransaktiesController;

    @Inject
    private ImporteerTransaktiesController importeerTransaktiesController;

    @GET
    public Response findTransakties() {
        return Response.ok(findTransaktiesController.find()).build();
    }

    @Path("importeer")
    @GET
    public Response importeer() {
        importeerTransaktiesController.importeer();
        return Response.ok().build();
    }

}
