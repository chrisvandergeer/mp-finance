package nl.cge.mpfinance.boundary;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.cge.mpfinance.control.BudgetOverzichtController;

@Stateless
@Produces(MediaType.APPLICATION_JSON)
@Path("budgetoverzicht")
public class BudgetOverzichtResource {

    @Inject
    private BudgetOverzichtController budgetOverzichtController;

    @GET
    public Response geefOverzicht() {
        return Response.ok(budgetOverzichtController.maak()).build();
    }

    @Path("{budgetgroep}")
    @GET
    public Response geefOverzicht(@PathParam("budgetgroep") String budgetgroep) {
        return Response.ok(budgetOverzichtController.maak(budgetgroep)).build();
    }

    @OPTIONS
    public Response options() {
        return Response.ok().build();
    }

    @Path("{budgetgroep}")
    @OPTIONS
    public Response optionsWithPath(@PathParam("budgetgroep") String budgetgroep) {
        return Response.ok().build();
    }
}
