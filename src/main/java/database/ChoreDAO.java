package database;

import classes.Chore;

import java.sql.*;
import java.util.ArrayList;

/**
 * <p>ChoreDAO class.</p>
 *
 */
public class ChoreDAO {

    /**
     * Inserts one chore for a given householdId to the database
     *
     * @param chore the {@link classes.Chore} object to insert.
     */
    public static void postChore(Chore chore){

        String query = "INSERT INTO Chore (description, chore_datetime, houseId, userId, done, title) VALUES (?, ?, ?, ?, ?, ?);";

        try (DBConnector dbc = new DBConnector();
            Connection conn = dbc.getConn();
            PreparedStatement st = conn.prepareStatement(query)){

            st.setString(6, chore.getTitle());
            st.setString(1, chore.getDescription());
            st.setTimestamp(2, Timestamp.valueOf(chore.getTime()));

            st.setInt(3, chore.getHouseId());
            st.setInt(4, chore.getUserId());
            if(chore.isDone()) {
                st.setInt(5, 1);
            }
            else {
                st.setInt(5, 0);
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
     *
     * @param householdId The id of the household.
     * @return an {@link java.util.ArrayList} of chores.
     */
    public static ArrayList<Chore> getChores(int householdId){
        ArrayList<Chore> chores = new ArrayList<>();
        Chore chore;
        String query = "SELECT * FROM Chore WHERE houseId = ?;";

        try (DBConnector dbc = new DBConnector();
            Connection conn = dbc.getConn();
            PreparedStatement st = conn.prepareStatement(query)){

            st.setInt(1, householdId);

            try(ResultSet rs = st.executeQuery()) {

                while (rs.next()) {
                    chore = new Chore();
                    chore.setTitle(rs.getString("title"));
                    chore.setDescription(rs.getString("description"));
                    chore.setChoreId(rs.getInt("choreId"));

                    chore.setTime(rs.getTimestamp("chore_datetime").toString().replace(" ","T"));

                    chore.setHouseId(householdId);
                    if (rs.getInt("done") == 1) {
                        chore.setDone(true);
                    } else {
                        chore.setDone(false);
                    }
                    chore.setUserId(rs.getInt("userId"));
                    chore.setUser(UserDAO.getUser(rs.getInt("userId")));
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
     * Returns a list of all chores for a user
     *
     * @param userId the userid.
     * @return an {@link java.util.ArrayList} of {@link classes.Chore} objects.
     */
    public static ArrayList<Chore> getUserChores(int userId){
        ArrayList<Chore> chores = new ArrayList<>();


        String query = "SELECT * FROM Chore WHERE userId = ?;";

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)){

            st.setInt(1, userId);

            try(ResultSet rs = st.executeQuery()) {

                while (rs.next()) {
                    Chore chore;
                    chore = new Chore();
                    chore.setTitle(rs.getString("title"));
                    chore.setDescription(rs.getString("description"));
                    chore.setChoreId(rs.getInt("choreId"));

                    chore.setTime(rs.getTimestamp("chore_datetime").toString().replace(" ","T"));

                    chore.setHouseId(rs.getInt("houseId"));
                    if (rs.getInt("done") == 1) {
                        chore.setDone(true);
                    } else {
                        chore.setDone(false);
                    }
                    chore.setUser(UserDAO.getUser(userId));
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
     *
     * @param choreId the id of the chore to delete.
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
     *
     * @param chore the edited {@link classes.Chore} object.
     * @return -1 for fail, 0 for no edits, 1 for success.
     */
    public static int editChore(Chore chore){

        String query = "UPDATE Chore SET title = ?, description = ?, chore_datetime = ?, userId = ?, done = ? WHERE choreId = ?;";
        try (DBConnector dbc = new DBConnector();
            Connection conn = dbc.getConn();
            PreparedStatement st = conn.prepareStatement(query)){

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

            return st.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * "Cheks" a chore that is done.
     *
     * @param chore the chore to inspect
     * @return -1 or 0 for error, 1 if success
     */
    public static int checkChore(Chore chore){
        String query = "UPDATE Chore SET done = ? WHERE choreId = ?;";
        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)){

            if (chore.isDone()) {
                st.setInt(1, 1);
            } else {
                st.setInt(1, 0);
            }
            st.setInt(2, chore.getChoreId());

            return st.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
        return -1;
    }
}
