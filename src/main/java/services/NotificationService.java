package services;

import classes.*;
import database.NotificationDAO;
import org.apache.commons.lang3.StringEscapeUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * <p>NotificationService class.</p>
 *
 */
@Path("/notification")
public class NotificationService {

    /**
     * <p>addNotification.</p>
     *
     * @param notification a {@link classes.Notification} object.
     * @return a boolean.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean addNotification(Notification notification) {
        notification.setMessage(StringEscapeUtils.escapeHtml4(notification.getMessage()));
       return NotificationDAO.addNotificationToDB(notification);
    }

    /**
     * <p>updateNotificationStatus.</p>
     *
     * @param id a int.
     * @return a boolean.
     */
    @DELETE
    @Path("/{id}")
    public boolean deleteNotification(@PathParam("id") int id) {
        return NotificationDAO.deleteNotification(id);
    }
}
