package database;

import classes.Household;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    public static String[] getHousehold(int id) {
        String name = "";
        String address = "";
        String[] householdInfo = new String[2];
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
                householdExists = true;
            }

            householdInfo[0] = name;
            householdInfo[1] = address;
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.disconnect();
        }
        if (householdExists) return householdInfo;
        return null;
    }

    /**
     * Used to update name, address of a household based on id.
     * Returns false if the user does not exist and true if the update was successful.
     * @param id the id of the house.
     * @param newName the new name
     * @param newAddress the new address
     */
    public static boolean updateHousehold(int id, String newName, String newAddress) {
        String query = "UPDATE Household SET house_name = ?, house_address = ? WHERE houseId = ?";
        boolean householdInfoUpdated = false;
        DBConnector dbc = new DBConnector();

        try {
            Connection conn = dbc.getConn();
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, newName);
            st.setString(2, newAddress);
            st.setInt(3, id);

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
    public static void deleteUser(int id) {
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
}