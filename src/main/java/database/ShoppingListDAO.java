package database;

import classes.Item;
import classes.ShoppingList;
import classes.User;

import java.sql.*;
import java.util.ArrayList;

public class ShoppingListDAO {
    /**
     * Get all shoppingslists in a household.
     * @param houseId the house ID
     * @return an Arraylist of shoppinglists, or null if a user with the given ID doesn't exist.
     */
    public static ShoppingList[] getShoppingLists(int houseId) {
        ArrayList<ShoppingList> shoppingLists = new ArrayList<>();
        ArrayList<User> users = new ArrayList<>();
        int shoppingListId = 0;
        int shoppingListId2 = 0;
        String shoppingListName = "";
        int userId = 0;
        String personName = "";
        String email = "";
        String telephone = "";
        boolean thereAreLists = false;

        String query = "SELECT Shopping_list.shopping_listId, Shopping_list.name, User_Shopping_list.userId, Person.name, Person.email, Person.telephone FROM Shopping_list LEFT JOIN User_Shopping_list ON Shopping_list.shopping_listId = User_Shopping_list.shopping_listId LEFT JOIN Person ON User_Shopping_list.userId = Person.userId WHERE Shopping_list.houseId = ? ORDER BY Shopping_list.shopping_listId;";
        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setInt(1, houseId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    thereAreLists = true;
                    shoppingListId = rs.getInt("shopping_listId");

                    if (shoppingListId != shoppingListId2 && shoppingListId2 != 0) {
                        ShoppingList sl = new ShoppingList();
                        sl.setName(shoppingListName);
                        sl.setUsers(toUserArray(users));
                        sl.setShoppingListId(shoppingListId2);
                        shoppingLists.add(sl);
                        users.clear();
                    }
                    shoppingListName = rs.getString("shopping_list.name");

                    userId = rs.getInt("userId");

                    if (userId != 0) {
                        personName = rs.getString("Person.name");
                        email = rs.getString("email");
                        telephone = rs.getString("telephone");

                        User user = new User();
                        user.setUserId(userId);
                        user.setName(personName);
                        user.setEmail(email);
                        user.setTelephone(telephone);

                        users.add(user);
                    }

                    shoppingListId2 = shoppingListId;
                }
            }

            if (thereAreLists){
                ShoppingList sl = new ShoppingList();
                sl.setName(shoppingListName);
                sl.setUsers(toUserArray(users));
                sl.setShoppingListId(shoppingListId2);
                shoppingLists.add(sl);
                users.clear();

                for (ShoppingList shoppingList: shoppingLists) {
                    Item[] items = getItems(shoppingList.getShoppingListId());
                    shoppingList.setItems(items);
                }
                return toShoppingListArray(shoppingLists);
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static boolean isAdmin(int houseId, int userId) {
        String query = "SELECT House_user.isAdmin FROM House_user WHERE houseId = ? AND userId = ?;";
        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setInt(1, houseId);
            st.setInt(2, userId);

            try (ResultSet rs = st.executeQuery()){
                if (rs.next()) {
                    if (rs.getInt("isAdmin") == 1){
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

    public static ShoppingList[] getShoppingLists(int houseId, int userId) {
        ArrayList<ShoppingList> shoppingLists = new ArrayList<>();
        ArrayList<Item> items = new ArrayList<>();
        int shoppingListId = 0;
        int shoppingListId2 = 0;
        String shoppingListName = "";
        boolean thereAreLists = false;
        int itemId = 0;
        String itemName;
        int checkedBy = 0;
        String query = "SELECT usl.userId, sl.*, Item.*, p.* FROM User_Shopping_list AS usl INNER JOIN Shopping_list AS sl ON usl.shopping_listId = sl.shopping_listId INNER JOIN Item ON Item.shopping_listId = sl.shopping_listId LEFT JOIN Person AS p ON Item.checkedBy = p.userId WHERE sl.houseId = ? AND usl.userId = ?;";
        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            if (ShoppingListDAO.isAdmin(houseId, userId)) return ShoppingListDAO.getShoppingLists(houseId);

            st.setInt(1, houseId);
            st.setInt(2, userId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    thereAreLists = true;
                    shoppingListId = rs.getInt("sl.shopping_listId");

                    if (shoppingListId != shoppingListId2 && shoppingListId2 != 0) {
                        ShoppingList sl = new ShoppingList();
                        sl.setName(shoppingListName);
                        sl.setItems(toItemArray(items));
                        sl.setShoppingListId(shoppingListId2);
                        shoppingLists.add(sl);
                        items.clear();
                    }
                    shoppingListName = rs.getString("sl.name");

                    itemId = rs.getInt("itemId");
                    itemName = rs.getString("Item.name");
                    checkedBy = rs.getInt("checkedBy");

                    Item item = new Item();
                    User checkerboi = new User();

                    if (checkedBy != 0) {
                        int checkerboiId;
                        String email;
                        String personName;
                        String telephone;

                        checkerboiId = rs.getInt("p.userId");
                        email = rs.getString("email");
                        personName = rs.getString("p.name");
                        telephone = rs.getString("telephone");

                        checkerboi.setUserId(checkerboiId);
                        checkerboi.setName(personName);
                        checkerboi.setEmail(email);
                        checkerboi.setTelephone(telephone);

                        item.setCheckedBy(checkerboi);
                    } else {
                        item.setCheckedBy(null);
                    }

                    item.setItemId(itemId);
                    item.setName(itemName);

                    items.add(item);

                    shoppingListId2 = shoppingListId;
                }
            }

            if (thereAreLists){
                ShoppingList sl = new ShoppingList();
                sl.setName(shoppingListName);
                sl.setItems(toItemArray(items));
                sl.setShoppingListId(shoppingListId2);
                shoppingLists.add(sl);
                items.clear();

                for (ShoppingList shoppingList: shoppingLists) {
                    User[] canView = ShoppingListDAO.getShoppingListUsers(shoppingList.getShoppingListId());
                    shoppingList.setUsers(canView);
                }
                return toShoppingListArray(shoppingLists);
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static User[] getShoppingListUsers(int shoppingListId) {
        ArrayList<User> users = new ArrayList<>();
        int userId;
        String email;
        String name;
        String telephone;

        String query = "SELECT usl.userId, p.* FROM User_Shopping_list AS usl INNER JOIN Person AS p ON usl.userId = p.userId WHERE shopping_listId = ?";

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setInt(1, shoppingListId);
            ResultSet rs = st.executeQuery();


            while (rs.next()) {
                User user = new User();
                userId = rs.getInt("p.userId");
                email = rs.getString("email");
                name = rs.getString("name");
                telephone = rs.getString("telephone");

                user.setUserId(userId);
                user.setEmail(email);
                user.setName(name);
                user.setTelephone(telephone);

                users.add(user);
            }
            return toUserArray(users);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get all the items in one spesific shopping list
     * @param shopping_listId
     * @return an array of items
     */
    public static Item[] getItems(int shopping_listId) {
        ArrayList<Item> items = new ArrayList<>();
        int itemId;
        String itemName;
        int userId;
        String email;
        String personName;
        String telephone;

        String query = "SELECT Item.*, Person.* FROM Item LEFT JOIN Person ON Item.checkedBy = Person.userId WHERE shopping_listId = ?;";

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setInt(1, shopping_listId);
            try (ResultSet rs = st.executeQuery()) {

                while (rs.next()) {
                    Item item = new Item();
                    User user = null;

                    itemId = rs.getInt("itemId");
                    itemName = rs.getString("Item.name");
                    userId = rs.getInt("checkedBy");

                    if (userId != 0) {
                        user = new User();
                        user.setUserId(userId);
                        email = rs.getString("email");
                        personName = rs.getString("Person.name");
                        telephone = rs.getString("telephone");
                        user.setEmail(email);
                        user.setName(personName);
                        user.setTelephone(telephone);
                    }

                    item.setItemId(itemId);
                    item.setName(itemName);
                    item.setCheckedBy(user);

                    items.add(item);
                }
            }

            return toItemArray(items);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Creates a new shopping list with no items
     * @param shoppingList
     * @param houseId
     * @return shoppingListId
     */
  public static int createShoppingList(String shoppingList, int houseId){
        String name = shoppingList;
        String query = "INSERT INTO Shopping_list (name, houseId) VALUES (?,?)";

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            st.setString(1, name);
            st.setInt(2,houseId);
            st.executeUpdate();

            try (ResultSet resultSet = st.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    public static void deleteShoppingList(int houseId, int shopping_list_id){
        String query = "DELETE FROM Shopping_list WHERE houseId = ? AND shopping_listId = ?";

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setInt(2, shopping_list_id);
            st.setInt(1,houseId);

            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /*public static void addItems(Item[] items, int shopping_list_id){
        DBConnector dbc = new DBConnector();
        String query = "";

        try {
            Connection conn = dbc.getConn();

            for(int i = 0; i < items.length; i++){
                if(items[i] != null){
                    query = "INSERT INTO Item(name, checkedBy, shopping_listId) VALUES (?, ?, ?) WHERE shopping_listId = ?;";

                    PreparedStatement st = conn.prepareStatement(query);
                    st.setString(1, items[i].getName());
                    st.setInt(2, 0);
                    st.setInt(3, shopping_list_id);
                    st.executeUpdate();
                    st.close();
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.disconnect();
        }
    }*/

    public static void addItem(Item items, int shopping_list_id){
        String query = "INSERT INTO Item(name, shopping_listId) VALUES (?, ?);";

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setString(1, items.getName());
            //st.setInt(2, 0);
            st.setInt(2, shopping_list_id);
            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteItem(int shopping_list_id, int itemId){
        String query = "DELETE FROM Item WHERE shopping_listId = ? AND itemId = ?;";

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setInt(1, shopping_list_id);
            //st.setInt(2, 0);
            st.setInt(2, itemId);
            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateUsers(String[] userIds, int shoppingListId) {
        String delete = "DELETE FROM User_Shopping_list WHERE shopping_listId = ?";
        String query = "INSERT INTO User_Shopping_list (userId, shopping_listId) VALUES (?, ?);";

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement del_st = conn.prepareStatement(delete);
             PreparedStatement st = conn.prepareStatement(query)) {

            del_st.setInt(1, shoppingListId);
            int dels = del_st.executeUpdate();
            System.out.println("deleted " + dels + " rows");

            for (int i = 0; i < userIds.length; i++) {
                st.setInt(1, Integer.parseInt(userIds[i]));
                st.setInt(2, shoppingListId);
                int rtn = st.executeUpdate();
                if (rtn < 0) System.err.println("Could not update: " + userIds[i] + " into shoppinglist where shoppinglistid = " + shoppingListId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int updateCheckedBy(int userId, int itemId) {
        String query;
        if (userId == 0) {
            query = "UPDATE Item SET checkedBy = NULL WHERE itemId = ?";
        } else {
            query = "UPDATE Item SET checkedBy = ? WHERE itemId = ?";
        }
        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            if (userId == 0) {
                st.setInt(1, itemId);
            } else {
                st.setInt(1, userId);
                st.setInt(2, itemId);
            }

            return st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    private static User[] toUserArray(ArrayList<User> users) {
        User[] userArray = new User[users.size()];
        for (int i = 0; i < users.size(); i++) {
            userArray[i] = users.get(i);
        }
        return userArray;
    }

    private static ShoppingList[] toShoppingListArray(ArrayList<ShoppingList> shoppingLists) {
        ShoppingList[] shoppingListArray = new ShoppingList[shoppingLists.size()];
        for (int i = 0; i < shoppingLists.size(); i++) {
            shoppingListArray[i] = shoppingLists.get(i);
        }
        return shoppingListArray;
    }

    private static Item[] toItemArray(ArrayList<Item> items) {
        Item[] itemsArray = new Item[items.size()];
        for (int i = 0; i < items.size(); i++) {
            itemsArray[i] = items.get(i);
        }
        return itemsArray;
    }

        /**
         * Runs a PreparedStatement with INSERT-queries and returns the auto-generated
         * primary keys.
         * Note: Does not automatically close connection.
         *
         * @return a Result with primary keys, or null if an error occurred

    public static Result insertAndGetKeys(String query, String[] params){
        PreparedStatement statement = getConnection(query);
        prepare(statement, params);
        return executeAndGetKeys(statement);
    }

        /**
         * Execute a prepated statement, commits it, and returns the auto-generated primary keys.
         * Note: Does not automatically close connection
         * @param statement the PreparedStatement
         * @return a Result containing generated primary keys, or null if something went wrong
         */
    private static Result executeAndGetKeys(PreparedStatement statement) {
        Result result = null;
        try {
            if (statement == null)
                return null;
            statement.execute();
            result = new Result(statement.getGeneratedKeys(), statement);
            statement.getConnection().commit();
        } catch (SQLException e) {
            System.out.println("Error in executing query\n" + statement.toString() + "\n\n" + e.getMessage());
            CleanUp.closeStatement(statement);
        }
        return result;
    }

    /**
     * Add the parameters to the statement
     *
     * @param statement the statement to prepare the data for
     * @param params the data to put into the statement
     */
    private static void prepare(PreparedStatement statement, String[] params){
        try {
            for(int i = 0; i < params.length; i++){
                statement.setString(i + 1, params[i]);
            }
        } catch (SQLException e) {
            System.out.println("Error in preparing statement\n\n" + e.getMessage());
        }
    }

    public static void main (String[] args) {
        ShoppingList[] rtn = ShoppingListDAO.getShoppingLists(1, 28);
        System.out.println("stop");
    }
}
