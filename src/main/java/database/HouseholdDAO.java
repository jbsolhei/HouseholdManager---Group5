package database;

import classes.Email;
import classes.Household;
import classes.User;

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
            st.setString(2, address);

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
        Household household = new Household();
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
     * Used to send and invite email to a user.
     * @param email the email of the house.
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
                            "http://localhost:8080/hhapp/login.html?token=" + token);
        }
    }

    public static int[] getAdmins(int houseId) {
        String query = "SELECT House_user.userId, House_user.isAdmin FROM House_user WHERE houseId = ?";
        int counter = 0;
        int[] admins = null;

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setInt(1, houseId);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                if (rs.getInt("isAdmin") > 0) {
                    counter++;
                }
            }

            rs = st.executeQuery();
            admins = new int[counter];
            counter = 0;
            while (rs.next()) {
                if (rs.getInt("isAdmin") > 0) {
                    admins[counter] = rs.getInt("userId");
                    counter++;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return admins;
    }

    public static boolean addAdminToHousehold(int houseId, int userId) {
        String query = "INSERT "
    }
}