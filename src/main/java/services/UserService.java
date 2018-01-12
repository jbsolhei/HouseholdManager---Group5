package services;

import classes.*;
import database.UserDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
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
    @Path("/{id}/hh")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Household> getHousehold(@PathParam("id") int id) {
        return UserDAO.getHouseholds(id);
    }


    @GET
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

    @Path("/{id}/tasks")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Todo> todos(@PathParam("id") int id) {
        return UserDAO.getTasks(id);
    }

    @PUT
    @Path("/{email}/pwReset")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean resetPassword(@PathParam("email") String email) {
        return UserDAO.resetPassword(email);
    }
}
