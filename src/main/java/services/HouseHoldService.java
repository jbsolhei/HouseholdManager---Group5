package services;

import auth.Auth;
import auth.AuthType;
import classes.Household;
import classes.User;
import database.HouseholdDAO;
import org.apache.commons.lang3.StringEscapeUtils;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * <p>HouseHoldService class.</p>
 *
 * @author team5
 */
@Path("household")
public class HouseHoldService {

    /**
     * <p>getTest.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getTest(){
        return "Household service is running!";
    }

    /**
     * <p>addHouseHold.</p>
     *
     * @param newHousehold a {@link classes.Household} object.
     * @return a int.
     */
    @POST
    @Auth
    @Consumes(MediaType.APPLICATION_JSON)
    public int addHouseHold(Household newHousehold) {
        newHousehold.setName(StringEscapeUtils.escapeHtml4(newHousehold.getName()));
        newHousehold.setAddress(StringEscapeUtils.escapeHtml4(newHousehold.getAddress()));
        return HouseholdDAO.addNewHouseHold(newHousehold);
    }

    /**
     * <p>addUserToHousehold.</p>
     *
     * @param house a int.
     * @param user a {@link java.lang.String} object.
     */
    @POST
    @Auth(AuthType.HOUSEHOLD_ADMIN)
    @Path("/{id}/users")
    @Consumes(MediaType.TEXT_PLAIN)
    public void addUserToHousehold(@PathParam("id") int house, String user){
        HouseholdDAO.addUserToHousehold(house,Integer.parseInt(user),0);
    }

    /**
     * <p>addUserFromInvite.</p>
     *
     * @param token a {@link java.lang.String} object.
     * @param user a {@link java.lang.String} object.
     * @return a {@link javax.ws.rs.core.Response} object.
     */
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

    /**
     * <p>inviteUsersToHousehold.</p>
     *
     * @param house a int.
     * @param email an array of {@link java.lang.String} objects.
     * @return a {@link javax.ws.rs.core.Response} object.
     */
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

    /**
     * <p>addAdmin.</p>
     *
     * @param id a int.
     * @param user a {@link classes.User} object.
     * @return a boolean.
     */
    @POST
    @Auth(AuthType.HOUSEHOLD_ADMIN)
    @Path("/{id}/admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean addAdmin(@PathParam("id") int id, User user) {
        return HouseholdDAO.addAdminToHousehold(id, user.getUserId());
    }

    /**
     * <p>getAdminIds.</p>
     *
     * @param id a int.
     * @return a {@link java.util.ArrayList} object.
     */
    @GET
    @Auth(AuthType.HOUSEHOLD)
    @Path("/{id}/admins")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Integer> getAdminIds(@PathParam("id") int id){
        return HouseholdDAO.getAdminIds(id);
    }

    /**
     * <p>makeAdmin.</p>
     *
     * @param id a int.
     * @param userId a int.
     * @return a boolean.
     */
    @PUT
    @Auth(AuthType.HOUSEHOLD_ADMIN)
    @Path("/{id}/admin")
    @Consumes(MediaType.TEXT_PLAIN)
    public boolean makeAdmin(@PathParam("id") int id, int userId) {
        return HouseholdDAO.makeUserAdmin(id, userId);
    }

    /**
     * <p>getMembers.</p>
     *
     * @param id a int.
     * @return an array of {@link classes.User} objects.
     */
    @GET
    @Auth(AuthType.HOUSEHOLD)
    @Path("/{id}/users")
    @Produces(MediaType.APPLICATION_JSON)
    public User[] getMembers(@PathParam("id") int id) {
        return HouseholdDAO.getMembers(id);
    }

    /**
     * <p>getHousehold.</p>
     *
     * @param id a int.
     * @param context a {@link javax.ws.rs.container.ContainerRequestContext} object.
     * @return a {@link classes.Household} object.
     */
    @GET
    @Auth(AuthType.HOUSEHOLD)
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Household getHousehold(@PathParam("id") int id,@Context ContainerRequestContext context) {
        return HouseholdDAO.getHousehold(id,(int)context.getProperty("session.userId"));
    }

    /**
     * <p>updateHousehold.</p>
     *
     * @param id a int.
     * @param newHouse a {@link classes.Household} object.
     * @return a int.
     */
    @PUT
    @Auth(AuthType.HOUSEHOLD_ADMIN)
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public int updateHousehold(@PathParam("id") int id, Household newHouse){
        newHouse.setName(StringEscapeUtils.escapeHtml4(newHouse.getName()));
        newHouse.setAddress(StringEscapeUtils.escapeHtml4(newHouse.getAddress()));
        return HouseholdDAO.updateHousehold(id,newHouse);
    }

    /**
     * <p>removeMyselfFromHousehold.</p>
     *
     * @param householdId a int.
     * @param context a {@link javax.ws.rs.container.ContainerRequestContext} object.
     * @return a {@link javax.ws.rs.core.Response} object.
     */
    @DELETE
    @Auth(AuthType.HOUSEHOLD)
    @Path("/{id}/user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeMyselfFromHousehold(@PathParam("id") int householdId,
                                              @Context ContainerRequestContext context) {
        int userId = (Integer) context.getProperty("session.userId");
        boolean success = HouseholdDAO.removeUserFromHousehold(householdId, userId);
        HashMap<String, Boolean> result = new HashMap<>();
        result.put("success", success);
        return Response.ok(result).build();
    }

    /**
     * <p>removeOthersFromHousehold.</p>
     *
     * @param householdId a int.
     * @param userId a int.
     * @return a {@link javax.ws.rs.core.Response} object.
     */
    @DELETE
    @Auth(AuthType.HOUSEHOLD_ADMIN)
    @Path("{id}/users/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeOthersFromHousehold(@PathParam("id") int householdId,
                                              @PathParam("userId") int userId) {
        boolean success = HouseholdDAO.removeUserFromHousehold(householdId, userId);
        HashMap<String, Boolean> result = new HashMap<>();
        result.put("success", success);
        return Response.ok(result).build();
    }

    /**
     * <p>deleteHousehold.</p>
     *
     * @param id a int.
     */
    @DELETE
    @Auth(AuthType.HOUSEHOLD_ADMIN)
    @Path("/{id}")
    public void deleteHousehold(@PathParam("id") int id){
        HouseholdDAO.deleteHousehold(id);
    }
}
