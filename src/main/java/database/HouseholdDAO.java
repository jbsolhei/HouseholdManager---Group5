package database;

import classes.Household;
import classes.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

        if (householdExists) return (User[]) members.toArray();
        return null;
    }

    /**
     * Used to update name, address of a household based on id.
     * Returns false if the user does not exist and true if the update was successful.
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
}