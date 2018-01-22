package database;

import classes.Chore;
import classes.Household;
import classes.User;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class ChoreDAO {

    /**
     * Inserts one chore for a given householdId to the database
     * @param chore
     */
    public static void postChore(Chore chore){

        String query = "INSERT INTO Chore (description, chore_date, chore_time, houseId, userId, done, title, ) VALUES (?, ?, ?, ?, ?, ?, ?);";
        try {
            DBConnector dbc = new DBConnector();
            Connection conn = dbc.getConn();
            PreparedStatement st = conn.prepareStatement(query);

            st.setString(7, chore.getTitle());
            st.setString(1, chore.getDescription());
            LocalDateTime dateTime = chore.getTime(); // your ldt
            java.sql.Date sqlDate = java.sql.Date.valueOf(dateTime.toLocalDate());
            st.setDate(2, sqlDate);

            Timestamp timestamp = Timestamp.valueOf(dateTime);
            st.setTimestamp(3, timestamp);

            st.setInt(4, chore.getHouseId());
            System.out.println(chore.getUserId());
            st.setInt(5, chore.getUserId());
            if(chore.isDone()) {
                st.setInt(6, 1);
            }
            else {
                st.setInt(6, 0);
            }

            st.executeUpdate();

            st.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Returns all the chores for a given household where the date is >= today
     * Missing date because of the transformation from sql to java to javascript is missing..
     * @param householdId
     * @return chores
     */
    public static ArrayList<Chore> getChores(int householdId){
        ArrayList<Chore> chores = new ArrayList<>();
        Chore chore;

        String query = "SELECT * FROM Chore WHERE houseId = ? AND chore_date >= CURDATE();";

        try {
            DBConnector dbc = new DBConnector();
            Connection conn = dbc.getConn();
            PreparedStatement st = conn.prepareStatement(query);

            st.setInt(1, householdId);

            ResultSet rs = st.executeQuery();

            while(rs.next()){
                chore = new Chore();
                chore.setTitle(rs.getString("title"));
                chore.setDescription(rs.getString("description"));
                chore.setChoreId(rs.getInt("choreId"));

                chore.setTime(rs.getTimestamp("chore_time").toLocalDateTime());

                chore.setHouseId(householdId);
                if(rs.getInt("done") == 1){
                    chore.setDone(true);
                } else {
                    chore.setDone(false);
                }
                chore.setUserId(rs.getInt("userId"));
                chores.add(chore);
            }

            return chores;


        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Deletes a chore from the database
     * @param choreId
     */
    public static void deleteChore(int choreId){
        String query = "DELETE FROM Chore WHERE choreId = ?;";

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setInt(1, choreId);

            st.executeUpdate();

            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the chore in the database with the same id as the chore in param.
     * @param chore
     */
    public static void editChore(Chore chore){

        String query = "UPDATE Chore SET title = ?, description = ?, chore_time = ?, userId = ?, done = ? WHERE choreId = ?;";
        try {
            DBConnector dbc = new DBConnector();
            Connection conn = dbc.getConn();
            PreparedStatement st = conn.prepareStatement(query);

            st.setString(1, chore.getTitle());
            st.setString(2, chore.getDescription());
            st.setTimestamp(3, Timestamp.valueOf(chore.getTime()));
            st.setInt(4, chore.getUserId());
            if (chore.isDone()) {
                st.setInt(5, 1);
            } else {
                st.setInt(5, 0);
            }
            st.setInt(6, chore.getChoreId());

            st.executeUpdate();
            st.close();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
