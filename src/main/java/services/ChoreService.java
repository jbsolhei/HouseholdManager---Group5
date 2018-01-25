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
 * <p>ChoreService class.</p>
 *
 * @author team5
 */
@Path("household/{id}/chores")
public class ChoreService {

    /**
     * <p>getChores.</p>
     *
     * @param houseId a int.
     * @return a {@link java.util.ArrayList} object.
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
     * <p>postChore.</p>
     *
     * @param houseId a int.
     * @param chore a {@link classes.Chore} object.
     */
    @POST
    @Auth(AuthType.HOUSEHOLD)
    @Consumes(MediaType.APPLICATION_JSON)
    public void postChore(@PathParam("id") int houseId, Chore chore){
        chore.setTitle(StringEscapeUtils.escapeHtml4(chore.getTitle()));
        chore.setDescription(StringEscapeUtils.escapeHtml4(chore.getDescription()));
        ChoreDAO.postChore(chore);
    }

    /**
     * <p>editChore.</p>
     *
     * @param houseId a int.
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
     * <p>checkChore.</p>
     *
     * @param houseId a int.
     * @param chore a {@link classes.Chore} object.
     * @param context a {@link javax.ws.rs.container.ContainerRequestContext} object.
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
     * <p>deleteChore.</p>
     *
     * @param choreId a int.
     */
    @DELETE
    @Auth(AuthType.HOUSEHOLD)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{choreId}")
    public void deleteChore(@PathParam("choreId") int choreId){
        ChoreDAO.deleteChore(choreId);
    }
}
