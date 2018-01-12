package services;

import classes.*;
import database.UserDAO;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.QueryParam;
import java.util.ArrayList;

/**
 *
 * @author team5
 */
@Path("user")
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
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("id") int id) {
        User user = UserDAO.getUser(id);
        return user;
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean updateUser(@PathParam("id") int id, User user) {
        return UserDAO.updateUser(id, user.getEmail(), user.getTelephone(), user.getName());
    }

    @GET
    @Path("/hh/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Household> getHousehold(@PathParam("id") int id) {
        return UserDAO.getHouseholds(id);
    }


    @GET
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public String authenticateLogin(
            @QueryParam("email") String email,
            @QueryParam("password") String password) {

        Session session = UserAuth.authenticateLogin(email, password);

        if (session == null) {
            return "";
        }

        return session.getToken();
    }

    @GET
    @Path("/tasks/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Todo> todos(@PathParam("id") int id) {
        return UserDAO.getTasks(id);
    }

    @PUT
    @Path("/pwReset/{email}")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean resetPassword(@PathParam("email") String email) {
        return UserDAO.resetPassword(email);
    }
}
