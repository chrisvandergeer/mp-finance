package nl.cge.mpfinance.boundary;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.cge.mpfinance.control.BudgeteerController;
import nl.cge.mpfinance.control.FindGelijksoortigeTransaktiesController;
import nl.cge.mpfinance.control.FindTransaktiesController;
import nl.cge.mpfinance.control.ImporteerTransaktiesController;
import nl.cge.mpfinance.entity.BudgetregelMetTransaktiesDto;
import nl.cge.mpfinance.entity.FindTransaktiesDto;

@Stateless
@Produces(MediaType.APPLICATION_JSON)
@Path("transakties")
public class TransaktieResource {

    @Inject
    private FindTransaktiesController findTransaktiesController;

    @Inject
    private FindGelijksoortigeTransaktiesController findGelijksoortigeTransaktiesController;

    @Inject
    private BudgeteerController budgeteerController;

    @Inject
    private ImporteerTransaktiesController importeerTransaktiesController;

    @GET
    public Response findTransakties(@BeanParam FindTransaktiesDto findTransaktiesDto) {
        System.out.println(findTransaktiesDto);
        return Response.ok(findTransaktiesController.find(findTransaktiesDto)).build();
    }

    @Path("{volgnummer}")
    @GET
    public Response findSimilar(
            @PathParam("volgnummer") String volgnummer) {
        return Response.ok(findGelijksoortigeTransaktiesController.find(volgnummer)).build();
    }

    @POST
    public Response budgeteer(BudgetregelMetTransaktiesDto budgetregelMetTransakties) {
        budgeteerController.budgeteer(budgetregelMetTransakties);
        return Response.ok().build();
    }

    @Path("importeer")
    @GET
    public Response importeer() {
        importeerTransaktiesController.importeer();
        return Response.ok().build();
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

}
