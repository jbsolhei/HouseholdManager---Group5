package services;

import classes.*;
import database.NotificationDAO;
import org.apache.commons.lang3.StringEscapeUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/notification")
public class NotificationService {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean addNotification(Notification notification) {
        notification.setMessage(StringEscapeUtils.escapeHtml4(notification.getMessage()));
       return NotificationDAO.addNotificationToDB(notification);
    }

    @PUT
    @Path("/{id}/updateStatus")
    public boolean updateNotificationStatus(@PathParam("id") int id) {
        return NotificationDAO.updateNotificationStatus(id);
    }
}
