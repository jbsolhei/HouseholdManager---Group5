package database;

import classes.*;
import classes.Email;
import classes.HashHandler;
import classes.Household;
import classes.User;
import java.security.SecureRandom;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;


public class UserDAO {

    /**
     * Used to create a new user in the database from a User object.
     * @param newUser a User object
     * @return Returns false if the new user's email or telephone number already exists in the database, if else true
     */
    public static boolean addNewUser(User newUser) {
        String email = newUser.getEmail();
        String name = newUser.getName();
        String password = newUser.getPassword();
        String telephone = newUser.getTelephone();

        if (userExist(email, telephone)) return false;

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

        return true;
    }

    /**
     * Checks if a an email or a telephone number already exists in the database.
     * @param email The email that you want to check if exists
     * @param telephone The telephone number that you want to check if exists
     * @return Returns true if the telephone number or the email already exists and false if not
     */
    public static boolean userExist(String email, String telephone) {

        String query = "SELECT * FROM Person WHERE email='"+email+"' or telephone='"+telephone+"'";

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             Statement st = conn.createStatement()) {

            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
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
                // user.setPassword(rs.getString("password"));
                user.setTelephone(rs.getString("telephone"));
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
     * Used to reset a user's password when forgot. Takes the user's email, and generates a
     * random password that gets sent to the user's email. A hashed version of the new
     * password is inserted to the database.
     * @param email The email of the user who's password is going to be reset.
     * @return Returns true if the password was successfully reset. Returns false if the email is non existent. in
     * the database.
     */
    public static boolean resetPassword(String email) {

        SecureRandom random = new SecureRandom();
        byte randomBytes[] = new byte[8];
        random.nextBytes(randomBytes);
        String newPassword = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
        String newHashedPassword = HashHandler.makeHashFromPassword(newPassword);

        String query = "UPDATE Person SET password = ? WHERE email = ?";
        int resetSuccessful = 0;

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setString(1, newHashedPassword);
            st.setString(2, email);

            resetSuccessful = st.executeUpdate();
            System.out.println(resetSuccessful);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (resetSuccessful == 0) return false;

        String to = email;
        Email.sendMail(to, "Forgot Password","Here is your new randomly generated password: " + newPassword + "\n" +
                 "Please log in to your Household Manager account and change your password as soon as possible.\n");
        return true;
    }

    /**
     * Used to update a user's password based on the his/hers email.
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

    /**
     * Used to get all the Households that a user is connected to
     * @param userId The id of the user
     * @return An ArrayList of Household objects
     */
    public static ArrayList<Household> getHouseholds(int userId) {
        ArrayList<Household> households = new ArrayList<>();
        boolean userExists = false;
        String query = "SELECT houseId FROM House_user WHERE userId=?";

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                userExists = true;
                households.add(HouseholdDAO.getHousehold(rs.getInt("houseId")));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (userExists) return households;
        return null;
    }

    /**
     * Used to get a user's tasks based on the user's id
     * @param userId The id of the user
     * @return Returns an ArrayList of todo objects
     */
    public static ArrayList<Todo> getTasks(int userId) {
        String query = "SELECT * FROM Task WHERE userId = ?";
        ArrayList<Todo> todos = new ArrayList<>();

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setInt(1, userId);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Todo todo = new Todo();
                todo.setDate(rs.getDate("date"));
                todo.setDescription(rs.getString("description"));
                todo.setHouseId(rs.getInt("houseId"));
                todo.setUserId(rs.getInt("userId"));
                todo.setTaskId(rs.getInt("taskId"));

                todos.add(todo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return todos;
    }
}