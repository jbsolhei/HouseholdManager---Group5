package services;

import classes.LoginAttemptResponse;
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
    @Path("/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("email") String email) {
        String[] userInfo = UserDAO.getUser(email);
        User user = new User();
        user.setName(userInfo[0]);
        user.setPhone(userInfo[1]);
        user.setEmail(email);

        return user;
    }

    @GET
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public LoginAttemptResponse authenticateLogin(
            @QueryParam("email") String email,
            @QueryParam("password") String password) {

        Session session = UserAuth.authenticateLogin(email, password);
        LoginAttemptResponse response = new LoginAttemptResponse();

        if (session == null) {
            response.setSuccess(false);
        }
        else {
            response.setSuccess(true);
            response.setSessionToken(session.getToken());
        }

        return response;
    }

}
