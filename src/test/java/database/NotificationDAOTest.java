package database;

import classes.Notification;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class NotificationDAOTest {

    @Before
    public void setUp() throws Exception {
        DAOTest.setUp();
    }

    @After
    public void tearDown() throws Exception {
        DAOTest.tearDown();
    }

    @Test
    public void getNotifications() throws Exception {
        ArrayList<Notification> notifications = NotificationDAO.getNotifications(200);
        assertEquals("Du må være snill!", notifications.get(0).getMessage());
    }

    @Test
    public void deleteNotification() throws Exception {
        NotificationDAO.deleteNotification(1);

        String query = "SELECT * FROM Notification WHERE notificationId = 1";

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             Statement st = conn.createStatement()) {

            try(ResultSet rs = st.executeQuery(query)) {
                while (rs.next()){
                    assertTrue(false);
                }
                assertTrue(true);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addNotificationToDB() throws Exception {

        Notification notification = new Notification();
        notification.setUserId(200);
        notification.setHouseId(1);
        notification.setMessage("Du har blitt lagt til i en handleliste.");
        notification.setDateTime("2018-01-01 13:00:00.2");

        NotificationDAO.addNotificationToDB(notification);

        String query = "SELECT * FROM Notification WHERE notificationId = 2";

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             Statement st = conn.createStatement()) {

            try(ResultSet rs = st.executeQuery(query)) {
                while (rs.next()){
                    assertEquals(200, rs.getInt("userId"));
                    assertEquals(1, rs.getInt("houseId"));
                    assertEquals("Du har blitt lagt til i en handleliste.", rs.getString("message"));
                    assertEquals("2018-01-01 13:00:00.2", rs.getString("notificationDateTime"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}