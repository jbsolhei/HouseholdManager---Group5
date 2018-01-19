package database;

import classes.Debt;
import classes.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FinanceDAO {

    public static ArrayList<Debt> getDept(int userId) {
        ArrayList<Debt> depts = new ArrayList<>();
        int fromPerson = 0;
        double value = 0;
        User theOtherUser;
        String query = "SELECT * FROM Finance WHERE fromPerson = ?";

        try {
            DBConnector dbc = new DBConnector();
            Connection conn = dbc.getConn();
            PreparedStatement st = conn.prepareStatement(query);

            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                fromPerson = rs.getInt("toPerson");
                value = rs.getDouble("value");

                theOtherUser = new User();
                theOtherUser.setUserId(userId);
                depts.add(new Debt(value, theOtherUser));

            }
            rs.close();
            st.close();
            return depts;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList<Debt> getIncome(int userId){
        ArrayList<Debt> income = new ArrayList<>();
        int toPerson = 0;
        double value = 0;
        User theOtherUser;
        String query = "SELECT * FROM Finance WHERE toPerson = ?";
        try {
            DBConnector dbc = new DBConnector();
            Connection conn = dbc.getConn();
            PreparedStatement st = conn.prepareStatement(query);

            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                userId = rs.getInt("fromPerson");
                value = rs.getDouble("value");

                theOtherUser = new User();
                theOtherUser.setUserId(userId);
                income.add(new Debt(value, theOtherUser));

            }

            rs.close();
            st.close();
            return income;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
