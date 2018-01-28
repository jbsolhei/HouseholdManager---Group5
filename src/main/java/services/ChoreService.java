package services;
import auth.Auth;
import auth.AuthType;
import classes.Chore;
import database.ChoreDAO;
import org.apache.commons.lang3.StringEscapeUtils;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

/**
 * The rest service for chores
 *
 * @author team5
 */
@Path("household/{id}/chores")
public class ChoreService {

    /**
     * Gets all chores for a house
     *
     * @param houseId the house id. (from path)
     * @return an {@link java.util.ArrayList} of {@link classes.Chore}.
     */
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

    /**
     * Posts a chore to the database
     *
     * @param houseId the house id. (from path)
     * @param chore a {@link classes.Chore} object.
     * @return result of the postChore() method
     */
    @POST
    @Auth(AuthType.HOUSEHOLD)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public int postChore(@PathParam("id") int houseId, Chore chore){
        chore.setTitle(StringEscapeUtils.escapeHtml4(chore.getTitle()));
        chore.setDescription(StringEscapeUtils.escapeHtml4(chore.getDescription()));
        return ChoreDAO.postChore(chore);
    }

    /**
     * Edits an existing chore
     *
     * @param houseId the house id. (from path)
     * @param chore a {@link classes.Chore} object.
     * @return a int.
     */
    @PUT
    @Auth(AuthType.HOUSEHOLD)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public int editChore(@PathParam("id") int houseId, Chore chore){
        chore.setTitle(StringEscapeUtils.escapeHtml4(chore.getTitle()));
        chore.setDescription(StringEscapeUtils.escapeHtml4(chore.getDescription()));
        return ChoreDAO.editChore(chore);
    }

    /**
     * Marks a chore as 'complete'
     *
     * @param houseId the house id. (from path)
     * @param chore a {@link classes.Chore} object.
     * @param context context from current session. (from auth header)
     * @return a int.
     */
    @PUT
    @Path("/check")
    @Auth(AuthType.HOUSEHOLD)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public int checkChore(@PathParam("id") int houseId, Chore chore,@Context ContainerRequestContext context){
        if (chore.getUserId()!=(int)context.getProperty("session.userId"))return 0;
        return ChoreDAO.checkChore(chore);
    }

    /**
     * Deletes a chore
     *
     * @param choreId the chore id.
     */
    @DELETE
    @Auth(AuthType.HOUSEHOLD)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{choreId}")
    public void deleteChore(@PathParam("choreId") int choreId){
        ChoreDAO.deleteChore(choreId);
    }
}
