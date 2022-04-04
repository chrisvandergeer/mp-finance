package nl.cge.mpfinance.boundary;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.cge.mpfinance.control.FindGelijksoortigeTransaktiesController;
import nl.cge.mpfinance.control.FindTransaktiesController;
import nl.cge.mpfinance.control.ImporteerTransaktiesController;

@Stateless
@Produces(MediaType.APPLICATION_JSON)
@Path("transakties")
public class TransaktieResource {

    @Inject
    private FindTransaktiesController findTransaktiesController;

    @Inject
    private FindGelijksoortigeTransaktiesController findGelijksoortigeTransaktiesController;

    @Inject
    private ImporteerTransaktiesController importeerTransaktiesController;

    @GET
    public Response findTransakties() {
        return Response.ok(findTransaktiesController.find()).build();
    }

    @Path("identiek/{volgnummer}")
    @GET
    public Response findSimilar(
            @PathParam("volgnummer") String volgnummer,
            @QueryParam("omschrijving") String omschrijving) {
        return Response.ok(findGelijksoortigeTransaktiesController.find(volgnummer, omschrijving)).build();
    }

    @Path("importeer")
    @GET
    public Response importeer() {
        importeerTransaktiesController.importeer();
        return Response.ok().build();
    }

}
