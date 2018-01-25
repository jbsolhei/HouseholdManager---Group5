package database;

import classes.*;

import java.security.SecureRandom;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;


/**
 * <p>UserDAO class.</p>
 *
 */
public class UserDAO {

    /**
     * Used to create a new user in the database from a User object.
     *
     * @param newUser a User object
     * @return Returns false if the new user's email or telephone number already exists in the database, if else true
     */
    public static boolean addNewUser(User newUser) {
        String email = newUser.getEmail();
        String name = newUser.getName();
        String password = newUser.getPassword();
        String telephone = newUser.getTelephone();
        String profileImage = newUser.getProfileImage();
        if ("".equals(profileImage)) {
            profileImage = null;
        }
        String query = "INSERT INTO Person (email, name, password, telephone, image) VALUES (?,?,?,?,?)";



        if (userExist(email, telephone)) return false;

        String hashedPassword = HashHandler.makeHashFromPassword(password);

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setString(1, email);
            st.setString(2, name);
            st.setString(3, hashedPassword);
            st.setString(4, telephone);
            st.setString(5, profileImage);

            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Checks if a an email or a telephone number already exists in the database.
     *
     * @param email The email that you want to check if exists
     * @param telephone The telephone number that you want to check if exists
     * @return Returns true if the telephone number or the email already exists and false if not
     */
    public static boolean userExist(String email, String telephone) {

        String query = "SELECT * FROM Person WHERE email=? or telephone=?";

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)){

            st.setString(1, email);
            st.setString(2, telephone);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Get a user from a user ID.
     *
     * @param userId the user ID
     * @return a User object, or null if a user with the given ID doesn't exist.
     */
    public static User getUser(int userId) {
        String query = "SELECT * FROM Person WHERE userId = ?";
        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setInt(1, userId);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getInt("userId"));
                    user.setEmail(rs.getString("email"));
                    user.setName(rs.getString("name"));
                    user.setBio(rs.getString("bio"));
                    user.setRelationship(rs.getString("relationship"));
                    user.setGender(rs.getString("gender"));
                    user.setTelephone(rs.getString("telephone"));
                    user.setProfileImage(rs.getString("image"));
                    return user;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Used to update email, name and the telephone of a user based on email.
     * Returns false if the user does not exist and true if the update was successful.
     *
     * @param id The user's id in the database
     * @param newEmail New email
     * @param newTelephone new Telephone number
     * @param newName new Name
     * @return True or false depending on success.
     * @param bio a {@link java.lang.String} object.
     * @param relationship a {@link java.lang.String} object.
     * @param gender a {@link java.lang.String} object.
     * @param image a {@link java.lang.String} object.
     */
    public static boolean updateUser(int id, String newEmail, String newTelephone, String newName, String bio, String relationship, String gender, String image) {
        String getQuery = "SELECT * FROM Person WHERE email=? AND userId NOT LIKE ?";
        String query = "UPDATE Person SET email = ?, telephone = ?, name = ?, bio = ?, relationship = ?, gender = ?, image = ? WHERE userId = ?";
        boolean userInfoUpdated = false;

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query);
             PreparedStatement st1 = conn.prepareStatement(getQuery)) {

            st1.setString(1, newEmail);
            st1.setInt(2, id);
            int number = 0;
            try (ResultSet rs = st1.executeQuery()){
                while (rs.next()) {
                    number++;
                }
            }
            if(number == 0) {
                st.setString(1, newEmail);
                st.setString(2, newTelephone);
                st.setString(3, newName);
                st.setString(4, bio);
                st.setString(5, relationship);
                st.setString(6, gender);
                st.setString(7, image);
                st.setInt(8, id);


                int update = st.executeUpdate();

                if (update != 0) {
                    userInfoUpdated = true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userInfoUpdated;
    }

    /**
     * Used to delete users from the database based on the users email.
     *
     * @param id The user's id
     */
    public static void deleteUser(int id) {
        String query = "DELETE FROM Person WHERE userId = ?";

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setInt(1, id);
            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Used to reset a user's password when forgot. Takes the user's email, and generates a
     * random password that gets sent to the user's email. A hashed version of the new
     * password is inserted to the database.
     *
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
     *
     * @param id The user's id
     * @param newPassword The new password that the user wants
     * @return False if the user does not exist and true if successful.
     */
    public static boolean updatePassword(int id, String newPassword) {
        String query = "UPDATE Person SET password = ? WHERE userId = ?";

        boolean passwordUpdated = false;

        newPassword = HashHandler.makeHashFromPassword(newPassword);

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setString(1, newPassword);
            st.setInt(2, id);

            int update = st.executeUpdate();
            if (update != 0) {
                passwordUpdated = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return passwordUpdated;
    }

    /**
     * Used to get all the Households that a user is connected to
     *
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
            try (ResultSet rs = st.executeQuery()) {

                while (rs.next()) {
                    userExists = true;
                    households.add(HouseholdDAO.getHouseholdIdAndName(rs.getInt("houseId")));

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (userExists) return households;
        return null;
    }

    /**
     * Used to get a user's tasks based on the user's id
     *
     * @param userId The id of the user
     * @return Returns an ArrayList of todo objects
     */
    public static ArrayList<Chore> getChores(int userId) {
        String query = "SELECT * FROM Chore WHERE userId = ?";
        ArrayList<Chore> chores = new ArrayList<>();

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setInt(1, userId);

            try (ResultSet rs = st.executeQuery()) {

                while (rs.next()) {
                    Chore chore = new Chore();
                    chore.setTime(rs.getTimestamp("chore_datetime").toString().replace(" ","T"));
                    chore.setDescription(rs.getString("description"));
                    chore.setHouseId(rs.getInt("houseId"));
                    chore.setUserId(rs.getInt("userId"));
                    chore.setUser(getUser(rs.getInt("userId")));
                    chore.setChoreId(rs.getInt("choreId"));
                    chores.add(chore);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return chores;
    }

    /**
     * Method for checking if current password is correct, when changing password
     *
     * @param id the user id
     * @return a boolean, true if the password is correct, else false.
     * @param password a {@link java.lang.String} object.
     */
    public static boolean getPasswordMatch(int id, String password) {
        String query = "SELECT password FROM Person WHERE userId = ?";
        boolean correctPassword = false;

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)){

            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    if (HashHandler.passwordMatchesHash(password, rs.getString("password"))) {
                        correctPassword = true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return correctPassword;
    }

    /**
     * Used to find out if a user is a admin of a given household
     *
     * @param houseId the house ID
     * @param userId the user ID
     * @return true if the user is an admin of the household or false if the user isn't
     */
    public static boolean isAdmin(int houseId, int userId) {
        String query = "SELECT House_user.isAdmin FROM House_user WHERE houseId = ? AND userId = ?;";
        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setInt(1, houseId);
            st.setInt(2, userId);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    if (rs.getInt("isAdmin") == 1) {
                        System.out.println("userId: " + userId + " is Admin at house: " + houseId);
                        return true;
                    }
                    return false;
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
