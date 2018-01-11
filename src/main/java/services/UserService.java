package services;

import classes.*;
import database.UserDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;

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
            return Response.ok(response).build();
        }
    }

    @DELETE
    @Auth
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(@Context HttpHeaders request) {
        Sessions.invalidateSession(AuthenticationFilter.retrieveTokenFromHeader(request.getHeaderString("Authorization")));

        HashMap<String, Object> response = new HashMap<>();
        response.put("success", true);
        return Response.ok(response).build();
    }

    @GET
    @Auth
    @Path("/test")
    public Response authTest() {
        return Response.ok("Du klarte det! Du kom deg inn på en side som krever autentisering!").build();
    }
}
