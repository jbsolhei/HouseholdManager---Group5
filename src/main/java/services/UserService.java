package services;

import classes.Session;
import classes.User;
import classes.UserAuth;
import database.UserDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.QueryParam;

/**
 *
 * @author team5
 */
@Path("/user")
public class UserService {

    @GET
    @Produces("text/plain")
    public String getTest(){
        return "User service says hello!";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addUser(User newUser) {
        UserDAO.addNewUser(newUser);
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

}
