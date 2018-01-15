package services;

import classes.*;
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
    public void addHouseHold(Household newHousehold) {
        HouseholdDAO.addNewHouseHold(newHousehold);
    }

    @POST
    @Auth
    @Path("/{id}/users")
    @Consumes(MediaType.TEXT_PLAIN)
    public void addUserToHousehold(@PathParam("id") int house, String user){
        HouseholdDAO.addUserToHousehold(house,Integer.parseInt(user),0);
    }

    @POST
    @Auth
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
    @Path("/{id}/tasks")
    @Consumes(MediaType.APPLICATION_JSON)
    public Todo[] getTodosForHousehold(@PathParam("id") int id){
        return HouseholdDAO.getTodosForHousehold(id);
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