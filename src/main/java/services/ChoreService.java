package services;
import auth.Auth;
import auth.AuthType;
import classes.Chore;
import database.ChoreDAO;
import org.apache.commons.lang3.StringEscapeUtils;

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
    @Consumes(MediaType.APPLICATION_JSON)
    public void postChore(@PathParam("id") int houseId, Chore chore){
        chore.setTitle(StringEscapeUtils.escapeHtml4(chore.getTitle()));
        chore.setDescription(StringEscapeUtils.escapeHtml4(chore.getDescription()));
        ChoreDAO.postChore(chore);
    }

    @PUT
    @Auth(AuthType.HOUSEHOLD)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public int editChore(@PathParam("id") int houseId, Chore chore){
        chore.setTitle(StringEscapeUtils.escapeHtml4(chore.getTitle()));
        chore.setDescription(StringEscapeUtils.escapeHtml4(chore.getDescription()));
        return ChoreDAO.editChore(chore);
    }

    @DELETE
    @Auth(AuthType.HOUSEHOLD)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{choreId}")
    public void deleteChore(@PathParam("choreId") int choreId){
        ChoreDAO.deleteChore(choreId);
    }
}