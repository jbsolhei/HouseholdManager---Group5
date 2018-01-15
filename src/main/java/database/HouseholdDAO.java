package database;

import classes.*;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;

public class HouseholdDAO {

    /**
     * Used to create a new user in the database from a User object.
     * @param newHouseHold the household object
     */
    public static void addNewHouseHold(Household newHouseHold) {
        String name = newHouseHold.getName();
        String address = newHouseHold.getAdress();

        String query = "INSERT INTO Household (house_name,house_address) VALUES (?,?)";

        DBConnector dbc = new DBConnector();

        try {
            Connection conn = dbc.getConn();
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, name);
            st.setString(2,address);

            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.disconnect();
        }
    }

    /**
     * Used to get household info (name and address) from database based on the households id.
     * Returns an array of Strings with the name on index 0 and the address number on
     * index 1.
     * Returns null if the id does not exist in the database.
     * @param id the id of the house.
     * @return String[]
     */
    public static Household getHousehold(int id) { // TODO: more data
        String name = "";
        String address = "";
        User[] members = getMembers(id);
        User[] admins = getAdmins(id);
        ShoppingList[] shoppingLists = ShoppingListDAO.getShoppingLists(id);

        Household household = new Household();
        household.setAdmins(admins);
        household.setResidents(members);
        household.setShoppingLists(shoppingLists);
        boolean householdExists = false;


        String query = "SELECT house_name, house_address FROM Household WHERE houseId = ?";
        DBConnector dbc = new DBConnector();
        PreparedStatement st;

        try {
            Connection conn = dbc.getConn();
            st = conn.prepareStatement(query);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                name = rs.getString("house_name");
                address = rs.getString("house_address");
                household.setName(name);
                household.setAdress(address);
                householdExists = true;
            }

            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.disconnect();
        }
        if (householdExists) return household;
        return null;
    }

    /**
     * Used to get all members of a household from database based on the households id.
     * Returns an array of users.
     * Returns null if the id does not exist in the database or no members are found.
     * @param id the id of the house.
     * @return User[]
     */
    public static User[] getMembers(int id) {
        ArrayList<User> members = new ArrayList<>();
        boolean householdExists = false;

        String query = "SELECT userId FROM House_user WHERE houseId = ?";
        DBConnector dbc = new DBConnector();
        PreparedStatement st;

        try {
            Connection conn = dbc.getConn();
            st = conn.prepareStatement(query);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                members.add(UserDAO.getUser(rs.getInt("userId")));
                householdExists = true;
            }

            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.disconnect();
        }

        User[] data = new User[members.size()];
        for (int i = 0; i < members.size(); i++) {
            data[i] = members.get(i);
        }

        if (householdExists) return data;
        return null;
    }

    /**
     * Used to update name and/or address of a household based on id.
     * Returns false if the household does not exist and true if the update was successful.
     * @param id the id of the house.
     * @param newHouse the new data to update
     */
    public static boolean updateHousehold(int id, Household newHouse) {
        String query = "";

        String newName = newHouse.getName();
        String newAddress = newHouse.getAdress();

        if (newName.equals("")){
            query = "UPDATE Household SET house_address = ? WHERE houseId = ?";
        } else if (newAddress.equals("")){
            query = "UPDATE Household SET house_name = ? WHERE houseId = ?";
        } else {
            query = "UPDATE Household SET house_name = ?, house_address = ? WHERE houseId = ?";
        }

        boolean householdInfoUpdated = false;
        DBConnector dbc = new DBConnector();

        try {
            Connection conn = dbc.getConn();
            PreparedStatement st = conn.prepareStatement(query);
            if (newName.equals("")) {
                st.setString(1, newAddress);
                st.setInt(2, id);
            } else if (newAddress.equals("")){
                st.setString(1, newName);
                st.setInt(2, id);
            } else {
                st.setString(1, newName);
                st.setString(2, newAddress);
                st.setInt(3, id);
            }

            int update = st.executeUpdate();

            if (update != 0) {
                householdInfoUpdated = true;
            }

            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.disconnect();
        }

        return householdInfoUpdated;
    }

    /**
     * Used to delete household from the database based on the household id.
     * @param id the id of the household
     */
    public static void deleteHousehold(int id) {
        String query = "DELETE FROM Household WHERE houseId = ?";

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
     * Used to add new users to the household.
     * @param house the id of the house.
     * @param user the id of the user
     */
    public static void addUserToHousehold(int house, int user){
        String query = "INSERT INTO House_user (houseId,userId) VALUES (?,?)";

        DBConnector dbc = new DBConnector();

        try {
            Connection conn = dbc.getConn();
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, house);
            st.setInt(2,user);

            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.disconnect();
        }
    }

    /**
     * Used to add new users to the household from an invite link.
     * @param token the invite token
     * @param userId the id of the user
     */
    public static int addUserFromInvite(String token, int userId){
        int tokenResult = InviteHandler.verifyToken(token);
        if (tokenResult!=0){
            addUserToHousehold(tokenResult,userId);
            InviteHandler.removeToken(token);
            return tokenResult;
        }
        return -1;
    }

    /**
     * Used to send and invite email to a user.
     * @param houseId the id of the house
     * @param email the email of the user.
     */
    public static void inviteUser(int houseId, String email) {
        Household house = getHousehold(houseId);

        if (house!=null) {
            SecureRandom random = new SecureRandom();
            byte randomBytes[] = new byte[32];
            random.nextBytes(randomBytes);
            String token = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);

            String query = "INSERT INTO Invite_token (token,houseId,email) VALUES (?,?,?)";

            DBConnector dbc = new DBConnector();

            try {
                Connection conn = dbc.getConn();
                PreparedStatement st = conn.prepareStatement(query);
                st.setString(1, token);
                st.setInt(2, houseId);
                st.setString(3, email);

                st.executeUpdate();
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                dbc.disconnect();
            }

            String[] to = {email};
            Email.sendMail(to, "Household Manager Invitation",
                    "You have been invited to " + house.getName() + "!\n" +
                            "Click here to accept:\n" +
                            "http://localhost:8080/hhapp/login.html?invite=" + token);
        }
    }

    public static User[] getAdmins(int houseId) {
        String query = "SELECT House_user.userId, House_user.isAdmin FROM House_user WHERE houseId = ?";
        int counter = 0;
        ArrayList<User> admins = new ArrayList<>();

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setInt(1, houseId);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                if (rs.getInt("isAdmin") > 0) {
                    admins.add(UserDAO.getUser(rs.getInt("userId")));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        User[] list = new User[admins.size()];
        for (int i = 0; i < admins.size(); i++) {
            list[i] = admins.get(i);
        }

        return list;
    }


    //TODO: getTodosForHouseHold need some more pimping to include a timestamp in the date, as well as a "checked" or "done" attribute.
    public static Todo[] getTodosForHousehold(int houseId){
        ArrayList<Todo> todos = new ArrayList<>();
        boolean householdExists = false;

        String query = "SELECT taskId FROM Task WHERE houseId = ?";
        DBConnector dbc = new DBConnector();
        PreparedStatement st;

        try {
            Connection conn = dbc.getConn();
            st = conn.prepareStatement(query);
            st.setInt(1, houseId);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Todo todo = new Todo();
                todo.setdescription(rs.getString("description"));
                todo.setHouseId(houseId);
                todo.setTaskId(rs.getInt("taskId"));
                todo.setUserId(rs.getInt("userId"));
                todo.setDate(rs.getDate("date"));
                todos.add(todo);
                householdExists = true;
            }

            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.disconnect();
        }

        Todo[] data = new Todo[todos.size()];
        for (int i = 0; i < todos.size(); i++) {
            data[i] = todos.get(i);
        }
        if(householdExists) return data;
        return null;
    }
}