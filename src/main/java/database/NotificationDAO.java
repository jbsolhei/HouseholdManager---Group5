package database;

import classes.Notification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NotificationDAO {

    /**
     * Used to get a users notifications based on userId.
     * @param userId The user's id.
     * @return Returns an ArrayList with Notification objects.
     */
    public static ArrayList<Notification> getNotifications(int userId) {

        String query = "SELECT * FROM Notification WHERE userId = ? and isRead = FALSE";
        ArrayList<Notification> notifications = new ArrayList<>();

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setInt(1, userId);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Notification notification = new Notification();
                    notification.setUserId(rs.getInt("userId"));
                    notification.setHouseId(rs.getInt("houseId"));
                    notification.setNotificationId(rs.getInt("notificationId"));
                    notification.setMessage(rs.getString("message"));
                    notification.setDateTime(rs.getString("notificationDateTime"));
                    notification.setRead(rs.getBoolean("isRead"));

                    notifications.add(notification);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return notifications;
    }

    /**
     * Updates the isRead column of a notification to true.
     * @param notificationId The id of the notification that you want to update.
     * @return Returns true if successful, and false if not.
     */
    public static boolean updateNotificationStatus(int notificationId) {
        String query = "UPDATE Notification SET isRead = TRUE WHERE notificationId = ?";
        int updated = -1;

        try(DBConnector dbc = new DBConnector();
            Connection conn = dbc.getConn();
            PreparedStatement st = conn.prepareStatement(query)) {

            st.setInt(1, notificationId);

            updated = st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (updated <= 0) return false;
        return true;
    }

    /**
     * Adds a notification to the database.
     * @param notification A Notification Object.
     * @return Returns True if successful and False if not.
     */
    public static boolean addNotificationToDB(Notification notification) {
        String query = "INSERT INTO Notification (userId, houseId, message, notificationDateTime) VALUES (?, ?, ?, ?)";
        int added = -1;

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query))  {

            st.setInt(1, notification.getUserId());
            st.setInt(2, notification.getHouseId());
            st.setString(3, notification.getMessage());
            st.setString(4, notification.getDateTime());

            added = st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (added <= 0) return false;
        return true;
    }
}
