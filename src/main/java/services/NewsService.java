package services;

import auth.Auth;
import auth.AuthType;
import classes.News;
import database.NewsDAO;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 * @author team5
 */
@Path("household/{id}/news")
public class NewsService {
    @GET
    @Auth(AuthType.HOUSEHOLD)
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<News> getNews(@PathParam("id") int houseId){
        return NewsDAO.getNews(houseId);
    }

    @POST
    @Auth(AuthType.HOUSEHOLD)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postNews(@PathParam("id") int houseId,News news,@Context ContainerRequestContext context){
        news.setHouseId(houseId);
        news.setUserId((int)context.getProperty("session.userId"));

        if (NewsDAO.postNews(news)>0){
            return Response.ok().build();
        }
        return Response.notModified().build();
    }

    @DELETE
    @Auth(AuthType.HOUSEHOLD)
    @Path("/{msgId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteNews(@PathParam("msgId") int msgId){
        if (NewsDAO.deleteNews(msgId)>0){
            return Response.ok().build();
        }
        return Response.notModified().build();
    }
}
