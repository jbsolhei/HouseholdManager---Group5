package services;
import auth.Auth;
import auth.AuthType;
import classes.Chore;
import database.ChoreDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

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

    /*@GET
    @Auth(AuthType.HOUSEHOLD)
    @Path("{choreId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Chore> getChores(@PathParam("choreId") int choreId){ // TODO: Kansje lag denne?
        return ChoreDAO.getCore(choreId);
    }*/

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
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{choreId}")
    public void deleteChore(@PathParam("choreId") int choreId){
        ChoreDAO.deleteChore(choreId);
    }
}