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
    @Produces("text/plain")
    public String getTest(){
        return "Household service says hello!";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addHouseHold(Household newHousehold) {
        HouseholdDAO.addNewHouseHold(newHousehold);
    }

    @POST
    @Path("/{id}/users")
    @Consumes(MediaType.TEXT_PLAIN)
    public void addUserToHousehold(@PathParam("id") int house, String user){
        HouseholdDAO.addUserToHousehold(house,Integer.parseInt(user));
    }

    @POST
    @Path("/{id}/users/invite")
    @Consumes(MediaType.TEXT_PLAIN)
    public void inviteUserToHousehold(@PathParam("id") int house, String email){
        HouseholdDAO.inviteUser(house,email);
    }

    @GET
    @Path("/{id}/users")
    @Consumes(MediaType.APPLICATION_JSON)
    public User[] getMembers(@PathParam("id") int id) {
        return HouseholdDAO.getMembers(id);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Household getHousehold(@PathParam("id") int id) {
        return HouseholdDAO.getHousehold(id);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateHousehold(@PathParam("id") int id, Household newHouse){
        HouseholdDAO.updateHousehold(id,newHouse);
    }

    @DELETE
    @Path("/{id}")
    public void deleteHousehold(@PathParam("id") int id){
        HouseholdDAO.deleteHousehold(id);
    }

}