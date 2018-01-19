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
                        "LEFT JOIN Chore ON Person.userId = Chore.userId\n" +
                        "WHERE Chore.houseId = ? AND chore_date > DATE_SUB(now(), INTERVAL ? MONTH)\n" +
                        "GROUP BY Person.userId, Person.name, MONTH\n" +
                        "UNION\n" +
                        "SELECT Person.userId, Person.name,\n" +
                        "COUNT(shopping_tripId) AS 'TASKS',\n" +
                        "MONTH(shopping_tripDate) AS 'MONTH'\n" +
                        "FROM Person\n" +
                        "LEFT JOIN Shopping_trip ON Person.userId = Shopping_trip.userId\n" +
                        "WHERE Shopping_trip.houseId = ? AND shopping_tripDate > DATE_SUB(now(), INTERVAL ? MONTH)\n" +
                        "GROUP BY Person.userId, Person.name, MONTH\n" +
                        "ORDER BY userId;";

        //TODO: Spør om det er sånn her try/catch burde se ut
        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)){

            st.setInt(1, houseId);
            st.setInt(2, numberOfMonthsAgo);
            st.setInt(3, houseId);
            st.setInt(4, numberOfMonthsAgo);
            ResultSet rs = st.executeQuery();

            int lastUserId = -1;
            Stats stat = null;

            while (rs.next()) {
                int userId = rs.getInt("userId");

                if (lastUserId == userId) {
                    stat.addTasksToMonth(rs.getInt("MONTH"), rs.getInt("TASKS"));
                } else if (lastUserId == -1 || lastUserId != userId) {
                    stat = new Stats();
                    stat.setUserName(rs.getString("name"));
                    stat.addTasksToMonth(rs.getInt("MONTH"), rs.getInt("TASKS"));
                    stats.add(stat);
                }
                lastUserId = userId;
            }

        } catch (SQLException e) {
                 e.printStackTrace();
        }

        return stats;
    }
}
