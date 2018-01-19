package database;

import classes.Stats;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StatDAO {

    public static ArrayList<Stats> getStats(int houseId, int numberOfMonthsAgo) {
        ArrayList<Stats> stats = new ArrayList<>();

        String query = "SELECT Person.name, Person.userId, MONTH(chore_date) AS 'MONTH', COUNT(choreId) FROM Person" +
                "JOIN Chore ON Chore.userId = Person.userId" +
                "WHERE houseId = 1 AND done = 1 AND chore_date > DATE_SUB(now(), INTERVAL 12 MONTH)" +
                "GROUP BY Person.name, Person.userId, MONTH;";

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)){

            ResultSet rs = st.executeQuery();
            int lastUserId = -1;
            Stats stat = null;
            while (rs.next()) {
                int userId = rs.getInt("userId");

                if (lastUserId == userId) {
                    stat.addTasksToMonth(rs.getInt("MONTH"), rs.getInt(4));
                } else if (lastUserId == -1 || lastUserId != userId) {
                    stat = new Stats();
                    stat.setUserName(rs.getString("name"));
                    stat.addTasksToMonth(rs.getInt("MONTH"), rs.getInt(4));
                }
            }

        } catch (SQLException e) {
                 e.printStackTrace();
        }

        return stats;
    }

    public static void main(String[] args) {
        ArrayList<Stats> stats = getStats(1, 12);

        for (int i = 0; i < stats.size(); i++) {
            System.out.println(stats.get(i).getUserName());
            for (int j = 0; j < 12; j++) {
                System.out.println(stats.get(i).getTasks()[i]);
            }
        }
    }
}
