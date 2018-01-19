package database;

import classes.*;

import java.security.SecureRandom;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;

public class HouseholdDAO {

    /**
     * Used to create a new household in the database from a household object.
     *
     * @param newHouseHold the household object
     * @return The id of the new Household. -1 If something went wrong.
     */
    public static int addNewHouseHold(Household newHouseHold) {
        String name = newHouseHold.getName();
        String address = newHouseHold.getAddress();
        User[] admins = newHouseHold.getAdmins();
        int adminId = admins[0].getUserId();
        int houseId = -1;

        String query = "INSERT INTO Household (house_name, house_address) VALUES (?, ?)";

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            st.setString(1, name);
            st.setString(2, address);
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            while (rs.next()) {
                houseId = rs.getInt(1);
            }
            st.close();
            query = "INSERT INTO House_user (houseId, userId, isAdmin) VALUES (?, ?, 1)";

            try (PreparedStatement st2 = conn.prepareStatement(query)) {
                st2.setInt(1, houseId);
                st2.setInt(2, adminId);
                st2.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return houseId;
    }


    /**
     * Used to get household name from database based on the households id.
     * Returns the household ID and name in a Household-object, with every additional
     * fields not set / null.
     *
     * @param id the id of the house.
     * @return a Household object, or null if the household doesn't exist in the database
     */
    public static Household getHouseholdIdAndName(int id) {
        String name;
        String address;
        Household household = new Household();

        String query = "SELECT house_name,house_address FROM Household WHERE houseId = ?";

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {

                if (rs.next()) {
                    name = rs.getString("house_name");
                    address = rs.getString("house_address");
                    household.setHouseId(id);
                    household.setName(name);
                    household.setAddress(address);
                    return household;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Used to get household info (name and address) from database based on the households id.
     * Returns an array of Strings with the name on index 0 and the address number on
     * index 1.
     * Returns null if the id does not exist in the database.
     *
     * @param id the id of the house.
     * @param uid the userId (needed for auth)
     * @return Household the household object
     */
    public static Household getHousehold(int id,int uid) {
        ArrayList<User> users = new ArrayList<>();
        ArrayList<User> admins = new ArrayList<>();

        boolean householdExists = false;

        String query = "SELECT * FROM (House_user NATURAL JOIN Person) NATURAL JOIN Household WHERE Household.houseId = ?";

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                Household household = new Household();
                while (rs.next()) {
                    household.setName(rs.getString("house_name"));
                    household.setAddress(rs.getString("house_address"));
                    User toAdd = new User();
                    toAdd.setName(rs.getString("name"));
                    toAdd.setEmail(rs.getString("email"));
                    toAdd.setUserId(rs.getInt("userId"));
                    toAdd.setTelephone(rs.getString("telephone"));
                    users.add(toAdd);
                    if (rs.getBoolean("isAdmin")){
                        admins.add(toAdd);
                    }
                }
                household.setHouseId(id);
                User[] userList = new User[users.size()];
                for (int i = 0; i < users.size(); i++) {
                    userList[i] = users.get(i);
                }
                household.setResidents(userList);
                User[] adminList = new User[admins.size()];
                for (int i = 0; i < admins.size(); i++) {
                    adminList[i] = admins.get(i);
                }
                household.setAdmins(adminList);
                household.setShoppingLists(ShoppingListDAO.getShoppingLists(id,uid));
                return household;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Used to get all members of a household from database based on the households id.
     * Returns an array of users.
     * Returns null if the id does not exist in the database or no members are found.
     *
     * @param id the id of the house.
     * @return User[]
     */
    public static User[] getMembers(int id) {
        ArrayList<User> members = new ArrayList<>();
        boolean householdExists = false;

        String query = "SELECT userId FROM House_user WHERE houseId = ?";

    try (DBConnector dbc = new DBConnector();
         Connection conn = dbc.getConn();
         PreparedStatement st = conn.prepareStatement(query)){

            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {

                while (rs.next()) {
                    members.add(UserDAO.getUser(rs.getInt("userId")));
                    householdExists = true;
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
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
     *
     * @param id       the id of the house.
     * @param newHouse the new data to update
     */
    public static int updateHousehold(int id, Household newHouse) {
        String query;

        String newName = newHouse.getName();
        String newAddress = newHouse.getAddress();

        if (newName.equals("")) {
            query = "UPDATE Household SET house_address = ? WHERE houseId = ?";
        } else if (newAddress.equals("")) {
            query = "UPDATE Household SET house_name = ? WHERE houseId = ?";
        } else {
            query = "UPDATE Household SET house_name = ?, house_address = ? WHERE houseId = ?";
        }

        int updateResult = -1;

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            if (newName.equals("")) {
                st.setString(1, newAddress);
                st.setInt(2, id);
            } else if (newAddress.equals("")) {
                st.setString(1, newName);
                st.setInt(2, id);
            } else {
                st.setString(1, newName);
                st.setString(2, newAddress);
                st.setInt(3, id);
            }

            updateResult = st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updateResult;
    }

    /**
     * Used to delete household from the database based on the household id.
     *
     * @param id the id of the household
     */
    public static void deleteHousehold(int id) {
        String query = "DELETE FROM Household WHERE houseId = ?";

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)){

            st.setInt(1, id);
            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Used to add new users to the household.
     *
     * @param house   the id of the house.
     * @param user    the id of the user
     * @param isAdmin 1/0 is user is admin
     */
    public static void addUserToHousehold(int house, int user, int isAdmin) {
        String query = "INSERT INTO House_user (houseId,userId,isAdmin) VALUES (?,?,?)";

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)){

            st.setInt(1, house);
            st.setInt(2, user);
            st.setInt(3, isAdmin);

            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Used to add new users to the household from an invite link.
     *
     * @param token  the invite token
     * @param userId the id of the user
     */
    public static int addUserFromInvite(String token, int userId) {
        int invitedHouseId = InviteHandler.verifyToken(token);
        if (invitedHouseId != 0) {
            addUserToHousehold(invitedHouseId, userId, 0);
            InviteHandler.removeToken(token);
            return invitedHouseId;
        }
        return -1;
    }

    /**
     * Used to send and invite email to a user.
     *
     * @param houseId the id of the house
     * @param email   the email of the user.
     */
    public static int inviteUser(int houseId, String[] email) {
        Household house = getHouseholdIdAndName(houseId);
        ArrayList<String> tokens = new ArrayList<>();
        ArrayList<String> emails = new ArrayList<>();

        if (house != null && email != null) {
            SecureRandom random = new SecureRandom();
            String query = "";

            for (int i = 0; i < email.length; i++) {
                boolean dupe = false;

                query = "SELECT * FROM (Person NATURAL JOIN House_user) JOIN Invite_token ON Person.email = Invite_token.email WHERE (House_user.houseId=? AND Person.email=?) OR (Invite_token.email=? AND Invite_token.houseId=?);";

                try (DBConnector dbc = new DBConnector();
                     Connection conn = dbc.getConn();
                     PreparedStatement st = conn.prepareStatement(query)) {

                    st.setInt(1, houseId);
                    st.setString(2,email[i]);
                    st.setString(3,email[i]);
                    st.setInt(4,houseId);

                    try (ResultSet rs = st.executeQuery()) {
                        while (rs.next()) {
                            dupe = true;
                            System.out.println("User in household or already invited");
                        }
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                if (!dupe) {
                    byte randomBytes[] = new byte[32];
                    random.nextBytes(randomBytes);
                    String token = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
                    emails.add(email[i]);
                    tokens.add(token);

                    query = "INSERT INTO Invite_token (token,houseId,email) VALUES (?,?,?);";

                    try (DBConnector dbc = new DBConnector();
                         Connection conn = dbc.getConn();
                         PreparedStatement st = conn.prepareStatement(query)) {

                        st.setString(1, token);
                        st.setInt(2, houseId);
                        st.setString(3, email[i]);
                        st.executeUpdate();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

            for (int i = 0; i < emails.size(); i++) {
                String to = emails.get(i);
                Email.sendMail(to, "Household Manager Invitation",
                        "You have been invited to " + house.getName() + "!\n" +
                                "Click here to accept:\n" +
                                "http://localhost:8080/hhapp/login.html?invite=" + tokens.get(i));
            }
        }
        return emails.size();
    }


    /**
     * Used to get list of all household admins.
     *
     * @param houseId the id of the house
     * @return User[] with all the admin user objects
     */
    public static User[] getAdmins(int houseId) {
        String query = "SELECT House_user.userId, House_user.isAdmin FROM House_user WHERE houseId = ?";
        int counter = 0;
        ArrayList<User> admins = new ArrayList<>();

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setInt(1, houseId);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    if (rs.getInt("isAdmin") > 0) {
                        admins.add(UserDAO.getUser(rs.getInt("userId")));
                    }
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


    /**
     * Used to get all todolists for a household
     *
     * @param houseId the id of the house
     * @return array with all the todos.
     */
    public static Todo[] getTodosForHousehold(int houseId) {
        ArrayList<Todo> todos = new ArrayList<>();
        boolean householdExists = false;

        String query = "SELECT * FROM Chore WHERE houseId = ?";

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setInt(1, houseId);
            try (ResultSet rs = st.executeQuery()) {

                while (rs.next()) {
                    Todo todo = new Todo();
                    todo.setDescription(rs.getString("description"));
                    todo.setHouseId(houseId);
                    todo.setTaskId(rs.getInt("choreId"));
                    todo.setUser(UserDAO.getUser(rs.getInt("userId")));
                    todo.setDate(rs.getDate("chore_date"));
                    todos.add(todo);
                    householdExists = true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Todo[] data = new Todo[todos.size()];
        for (int i = 0; i < todos.size(); i++) {
            data[i] = todos.get(i);
        }
        if (householdExists) return data;
        return null;
    }


    /**
     * Make existing user in household admin.
     *
     * @param houseId the id of the house
     * @param userId the users id.
     * @return boolean of success
     */
    public static boolean makeUserAdmin(int houseId,int userId){
        String query = "UPDATE House_user SET isAdmin = 1 WHERE userId = ? AND houseId = ?;";
        int updateResult = 0;

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setInt(1, userId);
            st.setInt(2, houseId);

            updateResult = st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (updateResult == 0) return false;
        return true;
    }

    /**
     * Add user to household and make admin.
     *
     * @param houseId the id of the house
     * @param userId the users id.
     * @return bollean success
     */
    public static boolean addAdminToHousehold(int houseId, int userId) {
        String query = "INSERT INTO House_user (houseId, userId, isAdmin) VALUES (?, ?, 1)";
        int insertDone = 0;

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setInt(1, houseId);
            st.setInt(2, userId);

            insertDone = st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (insertDone == 0) return false;
        return true;
    }

    /**
     * get UuserIds of all household admins.
     *
     * @param houseId the id of the house
     * @return arrayList of all admin userIds.
     */
    public static ArrayList<Integer> getAdminIds(int houseId) {
        String query = "SELECT userId FROM House_user WHERE houseId=? AND isAdmin=1";
        ArrayList<Integer> adminIds = new ArrayList<>();

        try {
            DBConnector dbc = new DBConnector();
            Connection conn = dbc.getConn();
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, houseId);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    adminIds.add(rs.getInt("userId"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adminIds;
    }
}