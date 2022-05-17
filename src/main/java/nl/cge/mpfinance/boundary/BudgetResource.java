package nl.cge.mpfinance.boundary;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.cge.mpfinance.entity.BudgetDto;
import nl.cge.mpfinance.entity.BudgetGroep;
import nl.cge.mpfinance.entity.BudgetNaam;

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

    @POST
    public Response createBudget(BudgetDto budgetDto) {
        entityManager.createNamedQuery(BudgetGroep.JPQL_FIND_BY_NAME, BudgetGroep.class)
                .setParameter("budgetgroep", budgetDto.getBudgetgroep())
                .getResultList()
                .stream().findAny()
                .ifPresentOrElse(
                        budgetgroep -> {
                            BudgetNaam budgetNaam = new BudgetNaam();
                            budgetNaam.setNaam(budgetDto.getBudgetnaam());
                            entityManager.persist(budgetNaam);
                            budgetgroep.getBudgetNamen().add(budgetNaam);
                        },
                        () -> {
                            BudgetGroep budgetGroep = new BudgetGroep();
                            budgetGroep.setNaam(budgetDto.getBudgetgroep());
                            entityManager.persist(budgetGroep);
                            BudgetNaam budgetNaam = new BudgetNaam();
                            budgetNaam.setNaam(budgetDto.getBudgetnaam());
                            entityManager.persist(budgetNaam);
                            budgetGroep.getBudgetNamen().add(budgetNaam);
                        });
        entityManager.flush();
        return Response.ok().build();
    }

    @OPTIONS
    public Response options() {
        return Response.ok().build();
    }
}
