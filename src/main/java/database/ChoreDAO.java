package database;

import classes.Chore;
import classes.Household;
import classes.User;

import java.sql.*;
import java.util.ArrayList;

public class ChoreDAO {

    /**
     * Inserts one chore for a given householdId to the database
     * @param chore
     */
    public static void postChore(Chore chore){

        String query = "INSERT INTO Chore (description, chore_date, houseId, userId, done) VALUES (?, ?, ?, ?, ?);";
        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setString(1, chore.getDescription());
            st.setDate(2, Date.valueOf(chore.getDate()));
            st.setInt(3, chore.getHouseId());
            System.out.println(chore.getUser());
            st.setInt(4, chore.getUser().getUserId());
            if (chore.isDone()) {
                st.setInt(5, 1);
            }
            else {
                st.setInt(5, 0);
            }

            st.executeUpdate();

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

        String query = "SELECT * FROM Chore WHERE houseId = ? AND chore_date >= CURDATE();";

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setInt(1, household.getHouseId());

            try (ResultSet rs = st.executeQuery()) {

                while (rs.next()) {
                    chore = new Chore();
                    chore.setDescription(rs.getString("description"));
                    chore.setChoreId(rs.getInt("choreId"));
                    chore.setDate(rs.getDate("chore_date").toLocalDate());
                    chore.setHouseId(household.getHouseId());
                    if (rs.getInt("done") == 1) {
                        chore.setDone(true);
                    } else {
                        chore.setDone(false);
                    }
                    User user = new User();
                    user.setUserId(rs.getInt("userId"));
                    chore.setUser(user);
                    chores.add(chore);
                }

                return chores;
            }

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
        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setString(1, chore.getDescription());
            //st.setDate(2, null); //MÅ FIKSE DATE FRA JAVASCRIPT TIL JAVA TIL MYSQL

            // Bruk LocalDate
            // eks: fraDatabase.toLocalDate()
            // Og omvendt: Date.valueOf(chore.getDate())

            // Se NewsDAO for min løsning.

            //st.setInt(3, chore.getTime());
            st.setInt(2, chore.getUser().getUserId());
            if (chore.isDone()) {
                st.setInt(3, 1);
            } else {
                st.setInt(3, 0);
            }
            st.setInt(4, chore.getChoreId());

            st.executeUpdate();

        } catch(SQLException e){
            e.printStackTrace();
        }
    }
}
