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

        String query = "INSERT INTO Chore (description, chore_date, chore_time, houseId, userId, done) VALUES (?, ?, ?, ?, ?, ?);";
        try {
            DBConnector dbc = new DBConnector();
            Connection conn = dbc.getConn();
            PreparedStatement st = conn.prepareStatement(query);

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
     * @param household
     * @return chores
     */
    public static ArrayList<Chore> getChores(Household household){
        ArrayList<Chore> chores = new ArrayList<>();
        Chore chore;

        String query = "SELECT * FROM Chore WHERE houseId = ?;/* AND chore_date >= CURDATE();*/";

        try {
            DBConnector dbc = new DBConnector();
            Connection conn = dbc.getConn();
            PreparedStatement st = conn.prepareStatement(query);

            st.setInt(1, household.getHouseId());

            ResultSet rs = st.executeQuery();

            while(rs.next()){
                chore = new Chore();
                chore.setDescription(rs.getString("description"));
                chore.setChoreId(rs.getInt("choreId"));

                chore.setTime(rs.getTimestamp("chore_time").toLocalDateTime());

                chore.setHouseId(household.getHouseId());
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

        String query = "UPDATE Chore SET description = ?, userId = ?, done = ? WHERE choreId = ?;";
        try {
            DBConnector dbc = new DBConnector();
            Connection conn = dbc.getConn();
            PreparedStatement st = conn.prepareStatement(query);

            st.setString(1, chore.getDescription());
            //st.setDate(2, null); //MÅ FIKSE DATE FRA JAVASCRIPT TIL JAVA TIL MYSQL

            // Bruk LocalDate
            // eks: fraDatabase.toLocalDate()
            // Og omvendt: Date.valueOf(chore.getDate())

            // Se NewsDAO for min løsning.

            //st.setInt(3, chore.getTime());
            st.setInt(2, chore.getUserId());
            if (chore.isDone()) {
                st.setInt(3, 1);
            } else {
                st.setInt(3, 0);
            }
            st.setInt(4, chore.getChoreId());

            st.executeUpdate();
            st.close();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
