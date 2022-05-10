package nl.cge.mpfinance.boundary;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.OPTIONS;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.cge.mpfinance.entity.BudgetGroep;

@Stateless
@Produces(MediaType.APPLICATION_JSON)
@Path("budgetten")
public class BudgetResource {

    @PersistenceContext
    private EntityManager entityManager;

    @GET
    public Response getBudgetten() {
        return Response.ok(entityManager.createNamedQuery(BudgetGroep.JPQL_FIND_ALL, BudgetGroep.class).getResultList())
                .build();
    }

    @OPTIONS
    public Response options() {
        return Response.ok().build();
    }
}
