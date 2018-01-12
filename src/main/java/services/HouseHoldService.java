package services;

import classes.Auth;
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
    @Auth
    @Consumes(MediaType.APPLICATION_JSON)
    public void addHouseHold(Household newHousehold) {
        HouseholdDAO.addNewHouseHold(newHousehold);
    }

    @POST
    @Auth
    @Path("/{id}/users")
    @Consumes(MediaType.TEXT_PLAIN)
    public void addUserToHousehold(@PathParam("id") int house, String user){
        HouseholdDAO.addUserToHousehold(house,Integer.parseInt(user));
    }

    @POST
    @Auth
    @Path("/invited/{token}")
    @Consumes(MediaType.TEXT_PLAIN)
    public void addUserFromInvite(@PathParam("token") String token, String userId){
        HouseholdDAO.addUserFromInvite(token,Integer.parseInt(userId));
    }

    @POST
    @Auth
    @Path("/{id}/users/invite")
    @Consumes(MediaType.TEXT_PLAIN)
    public void inviteUserToHousehold(@PathParam("id") int house, String email){
        HouseholdDAO.inviteUser(house,email);
    }

    @GET
    @Auth
    @Path("/{id}/users")
    @Consumes(MediaType.APPLICATION_JSON)
    public User[] getMembers(@PathParam("id") int id) {
        return HouseholdDAO.getMembers(id);
    }

    @GET
    @Auth
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Household getHousehold(@PathParam("id") int id) {
        return HouseholdDAO.getHousehold(id);
    }

    @PUT
    @Auth
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateHousehold(@PathParam("id") int id, Household newHouse){
        HouseholdDAO.updateHousehold(id,newHouse);
    }

    @DELETE
    @Auth
    @Path("/{id}")
    public void deleteHousehold(@PathParam("id") int id){
        HouseholdDAO.deleteHousehold(id);
    }

}