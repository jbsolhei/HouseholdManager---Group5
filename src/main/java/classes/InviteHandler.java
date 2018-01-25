package classes;

import database.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>InviteHandler class.</p>
 *
 */
public class InviteHandler {
    /**
     * <p>verifyToken.</p>
     *
     * @param token a {@link java.lang.String} object.
     * @return a int.
     */
    public static int verifyToken(String token){
        String query = "SELECT * FROM Invite_token WHERE token=?";
        int house = 0;

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setString(1, token);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    house = rs.getInt("houseId");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return house;
    }

    /**
     * <p>removeToken.</p>
     *
     * @param token a {@link java.lang.String} object.
     */
    public static void removeToken(String token){
        String query = "DELETE FROM Invite_token WHERE token = ?";

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setString(1, token);
            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
