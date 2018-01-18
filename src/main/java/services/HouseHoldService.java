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
import java.util.ArrayList;
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
        int invitedHouseId = HouseholdDAO.addUserFromInvite(token, Integer.parseInt(user));
        HashMap<String, Object> response = new HashMap<>();
        if (invitedHouseId==-1||invitedHouseId==0){
            response.put("success", false);
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        response.put("success", true);
        response.put("houseId", invitedHouseId);
        return Response.ok(response).build();
    }

    @POST
    @Auth(AuthType.HOUSEHOLD)
    @Path("/{id}/users/invite")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response inviteUsersToHousehold(@PathParam("id") int house, String[] email){
        int howManySent = HouseholdDAO.inviteUser(house,email);
        HashMap<String, Object> response = new HashMap<>();
        if (howManySent==-1||howManySent==0){
            response.put("success", false);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        response.put("success", true);
        response.put("howManySent", howManySent);
        return Response.ok(response).build();
    }

    @POST
    @Auth(AuthType.HOUSEHOLD_ADMIN)
    @Path("/{id}/admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean addAdmin(@PathParam("id") int id, User user) {
        return HouseholdDAO.addAdminToHousehold(id, user.getUserId());
    }

    @GET
    @Auth(AuthType.HOUSEHOLD)
    @Path("/{id}/admins")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Integer> getAdminIds(@PathParam("id") int id){
        return HouseholdDAO.getAdminIds(id);
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
    @Produces(MediaType.APPLICATION_JSON)
    public User[] getMembers(@PathParam("id") int id) {
        return HouseholdDAO.getMembers(id);
    }

    @GET
    @Auth(AuthType.HOUSEHOLD)
    @Path("/{id}/tasks")
    @Produces(MediaType.APPLICATION_JSON)
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