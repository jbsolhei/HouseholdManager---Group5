package services;

import auth.*;
import classes.Household;
import classes.Todo;
import classes.User;
import database.UserDAO;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author team5
 */
@Path("/user")
public class UserService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getTest(){
        return "User service says hello!";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean addUser(User newUser) {
        return UserDAO.addNewUser(newUser);
    }

    @GET
    @Auth(AuthType.USER_READ)
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("id") int id) {
        return UserDAO.getUser(id);
    }

    @PUT
    @Auth(AuthType.USER_MODIFY)
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean updateUser(@PathParam("id") int id, User user) {
        return UserDAO.updateUser(id, user.getEmail(), user.getTelephone(), user.getName());
    }


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

    @GET
    @Auth
    @Path("/checkSession")
    public Response checkSession() {
        // @Auth checks session validity, so if we reach this statement the session is valid
        return Response.ok("Session is valid").build();
    }

    @GET
    @Auth(AuthType.USER_READ)
    @Path("/{id}/tasks")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Todo> todos(@PathParam("id") int id) {
        return UserDAO.getTasks(id);
    }

    @PUT
    @Path("/{email}/pwReset")
    public boolean resetPassword(@PathParam("email") String email) {
        return UserDAO.resetPassword(email);
    }
}
