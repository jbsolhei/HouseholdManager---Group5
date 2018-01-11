package services;

import classes.Household;
import classes.User;
import database.HouseholdDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author team5
 */
@Path("household")
public class HouseHoldService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getTest(){
        return "Household service says hello!";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addHouseHold(Household newHousehold) {
        HouseholdDAO.addNewHouseHold(newHousehold);
    }

    @GET
    @Path("/{id}/users")
    @Consumes(MediaType.APPLICATION_JSON)
    public User[] getMembers(@PathParam("id") String id) {
        return HouseholdDAO.getMembers(Integer.parseInt(id));
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Household getHousehold(@PathParam("id") String id) {
        return HouseholdDAO.getHousehold(Integer.parseInt(id));
    }
}