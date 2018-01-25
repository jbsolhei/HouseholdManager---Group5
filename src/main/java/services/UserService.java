package services;

import auth.*;
import classes.*;
import auth.Auth;
import auth.AuthType;
import auth.Session;
import auth.UserAuth;
import classes.Chore;
import classes.Debt;
import classes.Household;
import classes.User;
import database.ChoreDAO;
import database.FinanceDAO;
import database.NotificationDAO;
import database.UserDAO;
import org.apache.commons.lang3.StringEscapeUtils;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * <p>UserService class.</p>
 *
 * @author team5
 */
@Path("/user")
public class UserService {

    /**
     * <p>getTest.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getTest(){
        return "User service says hello!";
    }

    /**
     * <p>addUser.</p>
     *
     * @param newUser a {@link classes.User} object.
     * @return a boolean.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean addUser(User newUser) {
        newUser.setName(StringEscapeUtils.escapeHtml4(newUser.getName()));
        newUser.setTelephone(StringEscapeUtils.escapeHtml4(newUser.getTelephone()));
        return UserDAO.addNewUser(newUser);
    }

    /**
     * <p>getUser.</p>
     *
     * @param id a int.
     * @return a {@link classes.User} object.
     */
    @GET
    @Auth(AuthType.USER_READ)
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("id") int id) {
        return UserDAO.getUser(id);
    }

    /**
     * <p>updateUser.</p>
     *
     * @param id a int.
     * @param user a {@link classes.User} object.
     * @return a boolean.
     */
    @PUT
    @Auth(AuthType.USER_MODIFY)
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean updateUser(@PathParam("id") int id, User user) {
        user.setName(StringEscapeUtils.escapeHtml4(user.getName()));
        user.setTelephone(StringEscapeUtils.escapeHtml4(user.getTelephone()));
        return UserDAO.updateUser(id, user.getEmail(), user.getTelephone(), user.getName(), user.getBio(), user.getRelationship(), user.getGender(), user.getProfileImage());
    }

    /**
     * <p>getPasswordMatch.</p>
     *
     * @param id a int.
     * @param password a {@link java.lang.String} object.
     * @return a boolean.
     */
    @POST
    @Path("/{id}/checkPassword")
    @Consumes(MediaType.TEXT_PLAIN)
    public boolean getPasswordMatch(@PathParam("id") int id, String password) {
        return UserDAO.getPasswordMatch(id, password);
    }

    /**
     * <p>updatePassword.</p>
     *
     * @param id a int.
     * @param changePasswordContainer a {@link classes.ChangePasswordContainer} object.
     * @return a boolean.
     */
    @PUT
    @Auth(AuthType.USER_MODIFY)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}/updatePassword")
    public boolean updatePassword(@PathParam("id") int id, ChangePasswordContainer changePasswordContainer) {
        if(UserDAO.getPasswordMatch(id, changePasswordContainer.getOldPassword())) {
            return UserDAO.updatePassword(id, changePasswordContainer.getNewPassword());
        } else {
            return false;
        }
    }


    /**
     * <p>getHousehold.</p>
     *
     * @param id a int.
     * @return a {@link java.util.ArrayList} object.
     */
    @GET
    @Auth(AuthType.USER_MODIFY)
    @Path("/{id}/hh")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Household> getHousehold(@PathParam("id") int id) {
        ArrayList<Household> households = UserDAO.getHouseholds(id);
        if (households == null) {
            return new ArrayList<>();
        }
        return households;
    }

    /*
    @GET
    @Auth(AuthType.USER_READ)
    @Path("/{id}/hhs/onlyUserAndName")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Household> getHousehold(@PathParam("id") int id) {
        return UserDAO.getHHOnlyNameAndId(id);
    }
    */

    /*
    @GET
    @Auth(AuthType.USER_READ)
    @Path("/{id}/hhs/onlyUserAndName")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Household> getHousehold(@PathParam("id") int id) {
        return UserDAO.getHHOnlyNameAndId(id);
    }
    */


    /**
     * <p>authenticateLogin.</p>
     *
     * @param credentials a {@link classes.User} object.
     * @return a {@link javax.ws.rs.core.Response} object.
     */
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticateLogin(User credentials) {
        System.out.println("Login attempt by " + credentials.getEmail());

        Session session = UserAuth.authenticateLogin(credentials.getEmail(), credentials.getPassword());

        if (session == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        else {
            HashMap<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("sessionToken", session.getToken());
            response.put("userId",session.getUserId());
            return Response.ok(response).build();
        }
    }

    /**
     * <p>logout.</p>
     *
     * @param context a {@link javax.ws.rs.container.ContainerRequestContext} object.
     * @return a {@link javax.ws.rs.core.Response} object.
     */
    @DELETE
    @Auth
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(@Context ContainerRequestContext context) {
        Sessions.invalidateSession((String) context.getProperty("session.token"));

        HashMap<String, Object> response = new HashMap<>();
        response.put("success", true);
        return Response.ok(response).build();
    }

    /**
     * <p>checkSession.</p>
     *
     * @return a {@link javax.ws.rs.core.Response} object.
     */
    @GET
    @Auth
    @Path("/checkSession")
    public Response checkSession() {
        // @Auth checks session validity, so if we reach this statement the session is valid
        return Response.ok("Session is valid").build();
    }

    /**
     * <p>todos.</p>
     *
     * @param id a int.
     * @return a {@link java.util.ArrayList} object.
     */
    @GET
    @Auth(AuthType.USER_READ)
    @Path("/{id}/chores")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Chore> todos(@PathParam("id") int id) {
        return ChoreDAO.getUserChores(id);
    }

    /**
     * <p>resetPassword.</p>
     *
     * @param email a {@link java.lang.String} object.
     * @return a boolean.
     */
    @POST
    @Path("/pwReset")
    @Consumes(MediaType.TEXT_PLAIN)
    public boolean resetPassword(String email) {
        return UserDAO.resetPassword(email);
    }

    /**
     * <p>getDebt.</p>
     *
     * @param id a int.
     * @return a {@link java.util.ArrayList} object.
     */
    @GET
    @Auth(AuthType.USER_MODIFY)
    @Path("/{id}/debt")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Debt> getDebt(@PathParam("id") int id){
        return FinanceDAO.getDebt(id);
    }

    /**
     * <p>getIncome.</p>
     *
     * @param id a int.
     * @return a {@link java.util.ArrayList} object.
     */
    @GET
    @Auth(AuthType.USER_MODIFY)
    @Path("/{id}/income")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Debt> getIncome(@PathParam("id") int id){
        return FinanceDAO.getIncome(id);
    }

    /**
     * <p>settlePayment.</p>
     *
     * @param fromUser a int.
     * @param toUser a int.
     */
    @DELETE
    @Path("/{id}/debt/{toUser}")
    @Produces(MediaType.APPLICATION_JSON)
    public void settlePayment(@PathParam("id") int fromUser, @PathParam("toUser") int toUser){
       FinanceDAO.deleteDebt(fromUser, toUser);
    }

    /**
     * <p>getNotifications.</p>
     *
     * @param id a int.
     * @return a {@link java.util.ArrayList} object.
     */
    @GET
    @Path("/{id}/notifications")
    //@Auth(AuthType.USER_MODIFY)
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Notification> getNotifications(@PathParam("id") int id) {
        return NotificationDAO.getNotifications(id);
    }
}
