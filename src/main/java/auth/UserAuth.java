package auth;

import classes.HashHandler;
import database.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Methods for authenticating and authorizing user actions.
 */
public class UserAuth {

    /**
     * Authenticates a user login attempt. Generates and returns a Session for this user if the login
     * was successful, or null if the email/password combination was incorrect.
     * @param email the user email address
     * @param password the plain-text password
     * @return a Session object, or null.
     */
    public static Session authenticateLogin(String email, String password) {
        String query = "SELECT userId, password FROM Person WHERE email = ?";

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)){

            st.setString(1, email);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    int userId = rs.getInt("userId");
                    if (HashHandler.passwordMatchesHash(password, rs.getString("password"))) {
                        return Sessions.generateSession(userId);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Checks if a user can read another user. This is true if both users are
     * members of the same household.
     * @param userId ID of the user to check
     * @param subjectUserId ID of the second user to check
     * @return whether the user can read the  other user
     */
    public static boolean canUserReadUser(int userId, int subjectUserId) {
        if (userId == subjectUserId) {
            return true;
        }

        String query = "SELECT t2.userId FROM House_user t1 " +
                "LEFT JOIN House_user t2 ON t1.houseId = t2.houseId " +
                "WHERE t1.userId = ? AND t2.userId = ?";

        try {
            return isInRelation(query, userId, subjectUserId);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Checks if a user can read and modify a Chore.
     * @param userId ID of the user to check
     * @param choreId the chore ID
     * @return whether the user can access the chore or not
     */
    public static boolean canUserAccessChore(int userId, int choreId) {
        String query = "SELECT House_user.userId FROM House_user WHERE House_user.userId = ? " +
                "AND House_user.houseId = (SELECT Chore.houseId FROM Chore WHERE Chore.choreId = ?)";

        try {
            return isInRelation(query, userId, choreId);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Checks if a user can access a Household, that is, if the user is part of that Household.
     * @param userId ID of the user to check
     * @param householdId the Household ID
     * @return whether the user can access the Household or not
     */
    public static boolean canUserAccessHousehold(int userId, int householdId) {
        String query = "SELECT House_user.userId FROM House_user " +
                "WHERE House_user.userId = ? AND House_user.houseId = ?";

        try {
            return isInRelation(query, userId, householdId);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Checks if a user can read and modify a ShoppingList.
     * @param userId ID of the user to check
     * @param shoppingListId the ShoppingList ID
     * @return whether the user can access the ShoppingList or not
     */
    public static boolean canUserAccessShoppingList(int userId, int shoppingListId) {
        String query = "SELECT User_Shopping_list.userId FROM User_Shopping_list " +
                "WHERE User_Shopping_list.userId = ? AND User_Shopping_list.shopping_listId = ?";

        try {
            return isInRelation(query, userId, shoppingListId);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Checks if a user is admin of a Household.
     * @param userId ID of the user to check
     * @param householdId the Household ID
     * @return whether the user is a admin or not
     */
    public static boolean isUserHouseholdAdmin(int userId, int householdId) {
        String query = "SELECT House_user.userId FROM House_user " +
                "WHERE House_user.userId = ? AND House_user.houseId = ? " +
                "AND House_user.isAdmin = TRUE";

        try {
            return isInRelation(query, userId, householdId);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Checks whether the result of the given query has at least one row or not.
     * @param query a prepared statement query
     * @param id1 the first ID in the prepared statement
     * @param id2 the second ID in the prepared statement
     * @return whether the query results in at least one row
     * @throws SQLException if there is a SQL error
     */
    private static boolean isInRelation(String query, int id1, int id2) throws SQLException {
        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setInt(1, id1);
            st.setInt(2, id2);

            try (ResultSet rs = st.executeQuery()) {
                return rs.next();
            }
        }
    }

    public static void main(String[] args) {
        Session session = authenticateLogin("joaki.xamooz@gmail.com", "2");
        System.out.println("stop");
    }
}
