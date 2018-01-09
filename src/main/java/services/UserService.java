package services;

import classes.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.awt.*;


/**
 * @author team5
 */
@Path("user")
public class UserService {

    @GET
    @Produces("text/plain")
    public String getTest(){
        return "User service says hello!";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addUser(User newUser) {

    }
}
