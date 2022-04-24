package nl.cge.mpfinance.boundary;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.cge.mpfinance.control.FindGelijksoortigeTransaktiesController;
import nl.cge.mpfinance.control.FindTransaktiesController;
import nl.cge.mpfinance.control.ImporteerTransaktiesController;
import nl.cge.mpfinance.entity.TransaktiesResult;

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

    @OPTIONS
    public Response options() {
        return Response.ok("this is not an option").build();
    }

    @Path("{volgnummer}")
    @OPTIONS
    public Response options2(@PathParam("volgnummer") String volgnummer) {
        return Response.ok("this is not an option").build();
    }

    @Path("{volgnummer}")
    @GET
    public Response findSimilar(
            @PathParam("volgnummer") String volgnummer,
            @QueryParam("naamTegenpartij") String naamTegenpartij,
            @QueryParam("omschrijving") String omschrijving) {
        return Response.ok(findGelijksoortigeTransaktiesController.find(volgnummer, naamTegenpartij, omschrijving)).build();
    }

    @POST
    public Response budgeteer(TransaktiesResult budgeteerCommand) {
        System.out.println(budgeteerCommand);
        return Response.ok().build();
    }

    @Path("importeer")
    @GET
    public Response importeer() {
        importeerTransaktiesController.importeer();
        return Response.ok().build();
    }

}
