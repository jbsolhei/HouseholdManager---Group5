package services;
import auth.Auth;
import auth.AuthType;
import classes.Household;
import classes.Chore;
import classes.User;
import database.ChoreDAO;
import database.HouseholdDAO;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author team5
 */
@Path("household/{id}/chores")
public class ChoreService {

    @GET
    @Auth(AuthType.HOUSEHOLD)
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Chore> getChores(@PathParam("id") int houseId){
        return ChoreDAO.getChores(houseId);
    }

    @POST
    @Auth(AuthType.HOUSEHOLD)
    @Produces(MediaType.APPLICATION_JSON)
    public void postChore(@PathParam("id") int houseId, Chore chore){
        System.out.println(chore.getTitle());
        System.out.println(chore.getTime());
        ChoreDAO.postChore(chore);
    }

    @PUT
    @Auth(AuthType.HOUSEHOLD)
    @Produces(MediaType.APPLICATION_JSON)
    public void editChore(@PathParam("id") int houseId, Chore chore){
        ChoreDAO.editChore(chore);
    }

    @DELETE
    @Auth(AuthType.HOUSEHOLD)
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteChore(@PathParam("id") int houseId, int choreId){
        ChoreDAO.deleteChore(choreId);
    }
}