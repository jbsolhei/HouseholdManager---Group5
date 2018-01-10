package classes;

import database.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAuth {
    /**
     * Authenticates a user login attempt. Generates and returns a Session for this user if the login
     * was successful, or null if the email/password combination was incorrect.
     * @param email the user email address
     * @param password the plain-text password
     * @return a Session object, or null.
     */
    public static Session authenticateLogin(String email, String password) {
        String query = "SELECT userId FROM Person WHERE email = ? and password = ?";

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)){

            st.setString(1, email);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("userId");
                return Sessions.generateSession(userId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
