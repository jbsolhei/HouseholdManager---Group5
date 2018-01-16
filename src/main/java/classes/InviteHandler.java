package classes;

import database.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InviteHandler {
    public static int verifyToken(String token){
        String query = "SELECT * FROM Invite_token WHERE token=?";
        int house = 0;
        DBConnector dbc = new DBConnector();

        try {
            Connection conn = dbc.getConn();
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, token);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                house = rs.getInt("houseId");
            }

            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.disconnect();
        }

        return house;
    }

    public static void removeToken(String token){
        String query = "DELETE FROM Invite_token WHERE token = ?";
        DBConnector dbc = new DBConnector();

        try {
            Connection conn = dbc.getConn();
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, token);
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.disconnect();
        }
    }
}
