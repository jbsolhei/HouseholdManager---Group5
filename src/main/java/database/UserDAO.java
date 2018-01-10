package database;

import classes.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    /**
     * Used to create a new user in the database from a User object.
     * @param newUser
     */
    public static void addNewUser(User newUser) {
        String email = newUser.getEmail();
        String name = newUser.getName();
        String password = newUser.getPassword();
        String telephone = newUser.getPhone();

        /*
        Fiks passordhashing til passordet!!!!!!!!!!!!!!!!
         */

        String hashedPassword = "";

        String query = "INSERT INTO Person (email, name, password, telephone) VALUES (?,?,?,?)";

        DBConnector dbc = new DBConnector();

        try {
            Connection conn = dbc.getConn();
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, email);
            st.setString(2, name);
            st.setString(3, password);
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
     * Used to get user info (name and telephone) from database based on the users email.
     * Returns an array of Strings with the name on index 0 and the telephone number on
     * index 1.
     * Returns null if the email does not exist in the database.
     * @param email
     * @return String[]
     */
    public static String[] getUser(String email) {
        String name = "";
        String telephone = "";
        String[] userInfo = new String[2];
        boolean userExists = false;


        String query = "SELECT name, telephone FROM Person WHERE email = ?";
        DBConnector dbc = new DBConnector();
        PreparedStatement st;

        try {
            Connection conn = dbc.getConn();
            st = conn.prepareStatement(query);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                name = rs.getString("name");
                telephone = rs.getString("telephone");
                userExists = true;
            }

            userInfo[0] = name;
            userInfo[1] = telephone;
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.disconnect();
        }
        if (userExists) return userInfo;
        return null;
    }

    /**
     * Used to update email, name and the telephone of a user based on email.
     * Returns false if the user does not exist and true if the update was successful.
     * @param email
     * @param newEmail
     * @param newTelephone
     * @param newName
     * @return
     */
    public static boolean updateUser(String email, String newEmail, String newTelephone, String newName) {
        String query = "UPDATE Person SET email = ?, telephone = ?, name = ? WHERE email = ?";
        boolean userInfoUpdated = false;
        DBConnector dbc = new DBConnector();

        try {
            Connection conn = dbc.getConn();
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, newEmail);
            st.setString(2, newTelephone);
            st.setString(3, newName);
            st.setString(4, email);

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
     * @param email
     */
    public static void deleteUser(String email) {
        String query = "DELETE FROM Person WHERE email = ?";

        DBConnector dbc = new DBConnector();

        try {
            Connection conn = dbc.getConn();
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, email);
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
     * Returns false if the user does not exist and true if successful.
     * @param email
     * @param newPassword
     * @return
     */
    public static boolean updatePassword(String email, String newPassword) {
        String query = "UPDATE Person SET password = ? WHERE email = ?";
        boolean passwordUpdated = false;
        DBConnector dbc = new DBConnector();

        try {
            Connection conn = dbc.getConn();
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, newPassword);
            st.setString(2, email);

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