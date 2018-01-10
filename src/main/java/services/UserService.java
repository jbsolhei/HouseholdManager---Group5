package services;

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

    @GET
    @Path("/auth")
    public String authenticateUser(
            @QueryParam("email") String email,
            @QueryParam("password") String password) {

        String token = UserAuth.authUser(email, password);

        return token;
    }
}
