package services;

import classes.Household;
import database.HouseholdDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author team5
 */
@Path("household")
public class HouseHoldService {

    @GET
    @Produces("text/plain")
    public String getTest(){
        return "Household service says hello!";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addHouseHold(Household newHousehold) {
        HouseholdDAO.addNewHouseHold(newHousehold);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Household getHousehold(@PathParam("id") String id) {
        String[] householdInfo = HouseholdDAO.getHousehold(Integer.parseInt(id));
        Household household = new Household();
        household.setName(householdInfo[0]);
        household.setAdress(householdInfo[1]);

        return household;
    }
}