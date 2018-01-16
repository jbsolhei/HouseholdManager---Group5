package services;

import auth.Auth;
import auth.AuthType;
import classes.Household;
import classes.Todo;
import classes.User;
import database.HouseholdDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;

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
    public int addHouseHold(Household newHousehold) {
        return HouseholdDAO.addNewHouseHold(newHousehold);
    }

    @POST
    @Auth(AuthType.HOUSEHOLD_ADMIN)
    @Path("/{id}/users")
    @Consumes(MediaType.TEXT_PLAIN)
    public void addUserToHousehold(@PathParam("id") int house, String user){
        HouseholdDAO.addUserToHousehold(house,Integer.parseInt(user),0);
    }

    @POST
    @Auth(AuthType.DEFAULT)
    @Path("/invited/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public Response addUserFromInvite(@PathParam("token") String token, String user){
        int result = HouseholdDAO.addUserFromInvite(token, Integer.parseInt(user));
        if (result==-1){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        HashMap<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("houseId", result);
        return Response.ok(response).build();
    }

    @POST
    @Auth(AuthType.HOUSEHOLD_ADMIN)
    @Path("/{id}/users/invite")
    @Consumes(MediaType.APPLICATION_JSON)
    public void inviteUsersToHousehold(@PathParam("id") int house, String[] email){
        HouseholdDAO.inviteUser(house,email);
    }

    @POST
    @Auth(AuthType.HOUSEHOLD_ADMIN)
    @Path("/{id}/admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean addAdmin(@PathParam("id") int id, User user) {
        return HouseholdDAO.addAdminToHousehold(id, user.getUserId());
    }

    @PUT
    @Auth(AuthType.HOUSEHOLD_ADMIN)
    @Path("/{id}/admin")
    @Consumes(MediaType.TEXT_PLAIN)
    public boolean makeAdmin(@PathParam("id") int id, int userId) {
        return HouseholdDAO.makeUserAdmin(id, userId);
    }

    @GET
    @Auth(AuthType.HOUSEHOLD)
    @Path("/{id}/users")
    @Consumes(MediaType.APPLICATION_JSON)
    public User[] getMembers(@PathParam("id") int id) {
        return HouseholdDAO.getMembers(id);
    }

    @GET
    @Auth(AuthType.HOUSEHOLD)
    @Path("/{id}/tasks")
    @Consumes(MediaType.APPLICATION_JSON)
    public Todo[] getTodosForHousehold(@PathParam("id") int id){
        return HouseholdDAO.getTodosForHousehold(id);
    }

    @GET
    @Auth(AuthType.HOUSEHOLD)
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Household getHousehold(@PathParam("id") int id) {
        return HouseholdDAO.getHousehold(id);
    }

    @PUT
    @Auth(AuthType.HOUSEHOLD_ADMIN)
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateHousehold(@PathParam("id") int id, Household newHouse){
        int result = HouseholdDAO.updateHousehold(id,newHouse);
    }

    @DELETE
    @Auth(AuthType.HOUSEHOLD_ADMIN)
    @Path("/{id}")
    public void deleteHousehold(@PathParam("id") int id){
        HouseholdDAO.deleteHousehold(id);
    }

}