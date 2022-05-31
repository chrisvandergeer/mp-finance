package nl.cge.mpfinance.boundary;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
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
}
