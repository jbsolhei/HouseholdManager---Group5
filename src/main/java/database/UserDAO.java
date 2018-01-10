package database;

import classes.HashHandler;
import classes.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    /**
     * Used to create a new user in the database from a User object.
     * @param newUser a User object
     */
    public static void addNewUser(User newUser) {
        String email = newUser.getEmail();
        String name = newUser.getName();
        String password = newUser.getPassword();
        String telephone = newUser.getPhone();

        String hashedPassword = HashHandler.makeHashFromPassword(password);

        String query = "INSERT INTO Person (email, name, password, telephone) VALUES (?,?,?,?)";

        DBConnector dbc = new DBConnector();

        try {
            Connection conn = dbc.getConn();
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, email);
            st.setString(2, name);
            st.setString(3, hashedPassword);
            st.setString(4, telephone);

            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.disconnect();
        }
    }

    /**
     * Get a user from a user ID.
     * @param userId the user ID
     * @return a User object, or null if a user with the given ID doesn't exist.
     */
    public static User getUser(int userId) {
        String query = "SELECT * FROM Person WHERE userId = ?";
        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("userId"));
                user.setEmail(rs.getString("email"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                user.setPhone(rs.getString("telephone"));
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Used to update email, name and the telephone of a user based on email.
     * Returns false if the user does not exist and true if the update was successful.
     * @param id The user's id in the database
     * @param newEmail New email
     * @param newTelephone new Telephone number
     * @param newName new Name
     * @return True or false depending on success.
     */
    public static boolean updateUser(int id, String newEmail, String newTelephone, String newName) {
        String query = "UPDATE Person SET email = ?, telephone = ?, name = ? WHERE userId = ?";

        boolean userInfoUpdated = false;
        DBConnector dbc = new DBConnector();

        try {
            Connection conn = dbc.getConn();
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, newEmail);
            st.setString(2, newTelephone);
            st.setString(3, newName);
            st.setInt(4, id);

            int update = st.executeUpdate();

            if (update != 0) {
                userInfoUpdated = true;
            }

            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.disconnect();
        }

        return userInfoUpdated;
    }

    /**
     * Used to delete users from the database based on the users email.
     * @param id The user's id
     */
    public static void deleteUser(int id) {
        String query = "DELETE FROM Person WHERE userId = ?";

        DBConnector dbc = new DBConnector();

        try {
            Connection conn = dbc.getConn();
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, id);
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.disconnect();
        }
    }

    /**
     * Used to update a user's password based on the his/hers email.
     *
     * @param id The user's id
     * @param newPassword The new password that the user wants
     * @return False if the user does not exist and true if successful.
     */
    public static boolean updatePassword(int id, String newPassword) {
        String query = "UPDATE Person SET password = ? WHERE userId = ?";

        boolean passwordUpdated = false;
        DBConnector dbc = new DBConnector();

        newPassword = HashHandler.makeHashFromPassword(newPassword);

        try {
            Connection conn = dbc.getConn();
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, newPassword);
            st.setInt(2, id);

            int update = st.executeUpdate();

            if (update != 0) {
                passwordUpdated = true;
            }

            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.disconnect();
        }

        return passwordUpdated;
    }
}