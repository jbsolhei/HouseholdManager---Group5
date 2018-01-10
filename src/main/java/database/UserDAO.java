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

        /*
        Fiks passordhashing til passordet!!!!!!!!!!!!!!!!
         */

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
     * Used to get user info (name and telephone) from database based on the users email.
     * Returns an array of Strings with the name on index 0 and the telephone number on
     * index 1.
     * Returns null if the email does not exist in the database.
     * @param id the user's id in the database
     * @return String[] an array of info
     */
    public static String[] getUser(int id) {
        String name = "";
        String telephone = "";
        String email = "";
        String[] userInfo = new String[3];
        boolean userExists = false;


        String query = "SELECT name, telephone, email FROM Person WHERE id = ?";
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
                email = rs.getString("email");
                userExists = true;
            }

            userInfo[0] = name;
            userInfo[1] = telephone;
            userInfo[2] = email;
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
     * @param id The user's id in the database
     * @param newEmail New email
     * @param newTelephone new Telephone number
     * @param newName new Name
     * @return True or false depending on success.
     */
    public static boolean updateUser(int id, String newEmail, String newTelephone, String newName) {
        String query = "UPDATE Person SET email = ?, telephone = ?, name = ? WHERE id = ?";
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
        String query = "DELETE FROM Person WHERE id = ?";

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
        String query = "UPDATE Person SET password = ? WHERE id = ?";
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