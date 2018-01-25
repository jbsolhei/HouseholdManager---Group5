package services;

import auth.Auth;
import auth.AuthType;
import classes.News;
import database.NewsDAO;
import org.apache.commons.lang3.StringEscapeUtils;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 * <p>NewsService class.</p>
 *
 * @author team5
 */
@Path("household/{id}/news")
public class NewsService {
    /**
     * <p>getNews.</p>
     *
     * @param houseId a int.
     * @return a {@link java.util.ArrayList} object.
     */
    @GET
    @Auth(AuthType.HOUSEHOLD)
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<News> getNews(@PathParam("id") int houseId){
        return NewsDAO.getNews(houseId);
    }

    /**
     * <p>postNews.</p>
     *
     * @param houseId a int.
     * @param news a {@link classes.News} object.
     * @param context a {@link javax.ws.rs.container.ContainerRequestContext} object.
     * @return a {@link javax.ws.rs.core.Response} object.
     */
    @POST
    @Auth(AuthType.HOUSEHOLD)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postNews(@PathParam("id") int houseId,News news,@Context ContainerRequestContext context){
        news.setHouseId(houseId);
        news.setUserId((int)context.getProperty("session.userId"));

        news.setMessage(StringEscapeUtils.escapeHtml4(news.getMessage()));

        if (NewsDAO.postNews(news)>0){
            return Response.ok().build();
        }
        return Response.notModified().build();
    }

    /**
     * <p>deleteNews.</p>
     *
     * @param msgId a int.
     * @return a {@link javax.ws.rs.core.Response} object.
     */
    @DELETE
    @Auth(AuthType.HOUSEHOLD_ADMIN)
    @Path("/{msgId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteNews(@PathParam("msgId") int msgId){
        if (NewsDAO.deleteNews(msgId)>0){
            return Response.ok().build();
        }
        return Response.notModified().build();
    }
}
