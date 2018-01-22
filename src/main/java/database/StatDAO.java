package database;

import classes.Stats;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StatDAO {

    /**
     * Used to get info from the database about how many trips and chores each member of the household does each month.
     * @param houseId The id of the household that you want to find info about.
     * @param numberOfMonthsAgo How far back the info goes.
     * @return An ArrayList of Stats objects.
     */
    public static ArrayList<Stats> getTaskStats(int houseId, int numberOfMonthsAgo) {
        ArrayList<Stats> stats = new ArrayList<>();

        String query =  "SELECT Person.userId, Person.name,\n" +
                        "COUNT(choreId) AS 'TASKS',\n" +
                        "MONTH(chore_date) AS 'MONTH'\n" +
                        "FROM Person\n" +
                        "LEFT JOIN House_user ON Person.userId = House_user.userId\n" +
                        "LEFT JOIN Chore ON Person.userId = Chore.userId\n" +
                        "WHERE House_user.houseId = ? AND (chore_date > DATE_SUB(now(), INTERVAL ? MONTH) OR chore_date IS NULL)\n" +
                        "GROUP BY Person.userId, Person.name, MONTH\n" +
                        "UNION\n" +
                        "SELECT Person.userId, Person.name,\n" +
                        "COUNT(shopping_tripId) AS 'TASKS',\n" +
                        "MONTH(shopping_tripDate) AS 'MONTH'\n" +
                        "FROM Person\n" +
                        "LEFT JOIN House_user ON Person.userId = House_user.userId\n" +
                        "LEFT JOIN Shopping_trip ON Person.userId = Shopping_trip.userId\n" +
                        "WHERE House_user.houseId = ? AND (shopping_tripDate > DATE_SUB(now(), INTERVAL ? MONTH) OR shopping_tripDate IS NULL)\n" +
                        "GROUP BY Person.userId, Person.name, MONTH\n" +
                        "ORDER BY userId;";

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)){

            st.setInt(1, houseId);
            st.setInt(2, numberOfMonthsAgo);
            st.setInt(3, houseId);
            st.setInt(4, numberOfMonthsAgo);

            try (ResultSet rs = st.executeQuery()) {
                int lastUserId = -1;
                Stats stat = null;

                while (rs.next()) {
                    int userId = rs.getInt("userId");

                    if (lastUserId == userId) {
                        int month = rs.getInt("MONTH");
                        if (month == 0) month = 1;
                        stat.addTasksToMonth(month, rs.getInt("TASKS"));
                    } else if (lastUserId == -1 || lastUserId != userId) {
                        stat = new Stats();
                        stat.setUserName(rs.getString("name"));
                        int month = rs.getInt("MONTH");
                        if (month == 0) month = 1;
                        stat.addTasksToMonth(month, rs.getInt("TASKS"));
                        stats.add(stat);
                    }
                    lastUserId = userId;
                }
            }


        } catch (SQLException e) {
                 e.printStackTrace();
        }

        return stats;
    }

    /**
     * Used to get info about how much money is used per month in the household.
     * @param houseId The id of the household you want to find info about.
     * @param numberOfMonthsAgo How many months back to look for data.
     * @return Returns a double[] where the index decides which month + 1, and the value at each index is how much was
     * used that month.
     */
    public static double[] getExpenseStats(int houseId, int numberOfMonthsAgo) {
        double[] expenses = new double[12];

        String query =  "SELECT shopping_tripId, MONTH(shopping_tripDate) AS 'MONTH', Shopping_trip.expence\n" +
                        "FROM Shopping_trip\n" +
                        "WHERE Shopping_trip.houseId = ? AND shopping_tripDate > DATE_SUB(now(), INTERVAL ? MONTH);";

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)){

            st.setInt(1, houseId);
            st.setInt(2, numberOfMonthsAgo);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    int month = rs.getInt("MONTH");
                    expenses[month - 1] += rs.getDouble("expence");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return expenses;
    }
}
