package services;

import classes.*;
import database.NotificationDAO;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/notification")
public class NotificationService {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean addNotification(Notification notification) {
       return NotificationDAO.addNotificationToDB(notification);
    }

    @PUT
    @Path("/{id}/updateStatus")
    public boolean updateNotificationStatus(@PathParam("id") int id) {
        return NotificationDAO.updateNotificationStatus(id);
    }
}
