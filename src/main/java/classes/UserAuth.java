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
    public static Session authUser(String email, String password) {
        String query = "SELECT * FROM Person WHERE email = ? and password = ?";
        boolean userAuthenticated = false;
        DBConnector dbc = new DBConnector();

        try {
            Connection conn = dbc.getConn();
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, email);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                userAuthenticated = true;
            }

            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.disconnect();
        }

        return null;
    }
}
