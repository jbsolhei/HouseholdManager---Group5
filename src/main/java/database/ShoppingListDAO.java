package database;

import classes.Item;
import classes.ShoppingList;
import classes.User;

import java.sql.*;
import java.util.ArrayList;

public class ShoppingListDAO {
    /**
     * Get all shoppingslists in a household.
     * @param houseId the user ID
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

        String query = "SELECT Shopping_list.shopping_listId, Shopping_list.name, User_Shopping_list.userId, Person.name, Person.email, Person.telephone FROM Shopping_list LEFT JOIN User_Shopping_list ON Shopping_list.shopping_listId = User_Shopping_list.shopping_listId LEFT JOIN Person ON User_Shopping_list.userId = Person.userId WHERE Shopping_list.houseId = ? ORDER BY Shopping_list.shopping_listId;";
        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setInt(1, houseId);
            ResultSet rs = st.executeQuery();


            while(rs.next()) {
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

        String query = "SELECT Item.*, Person.* FROM Item LEFT JOIN Person ON Item.checkedBy = Person.userId WHERE shopping_listId = ?";

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setInt(1, shopping_listId);
            ResultSet rs = st.executeQuery();

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
     */
  public static void createShoppingList(ShoppingList shoppingList, int houseId){
        String name = shoppingList.getName();

        String query = "INSERT INTO Shopping_list (name, houseId) VALUES (?,?)";

        DBConnector dbc = new DBConnector();

        try {
            Connection conn = dbc.getConn();
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, name);
            st.setInt(2,houseId);

            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.disconnect();
        }
    }


    public static void deleteShoppingList(int houseId, int shopping_list_id){

        String query = "DELETE FROM Shopping_list WHERE houseId = ? AND shopping_listId = ?";

        DBConnector dbc = new DBConnector();

        try {
            Connection conn = dbc.getConn();
            PreparedStatement st = conn.prepareStatement(query);

            st.setInt(2, shopping_list_id);
            st.setInt(1,houseId);

            st.executeUpdate();
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.disconnect();
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
        DBConnector dbc = new DBConnector();
        String query = "";

        try {
            Connection conn = dbc.getConn();

            query = "INSERT INTO Item(name, shopping_listId) VALUES (?, ?);";

            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, items.getName());
            //st.setInt(2, 0);
            st.setInt(2, shopping_list_id);
            st.executeUpdate();
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.disconnect();
        }
    }

    public static void deleteItem(int shopping_list_id, int itemId){
        DBConnector dbc = new DBConnector();
        String query = "";

        try {
            Connection conn = dbc.getConn();

            query = "DELETE FROM Item WHERE shopping_listId = ? AND itemId = ?;";

            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, shopping_list_id);
            //st.setInt(2, 0);
            st.setInt(2, itemId);
            st.executeUpdate();
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbc.disconnect();
        }
    }

    public static void updateUsers(int[] userIds, int shoppingListId) {
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
                st.setInt(1, userIds[i]);
                st.setInt(2, shoppingListId);
                int rtn = st.executeUpdate();
                if (rtn < 0) System.err.println("Could not update: " + userIds[i] + " into shoppinglist where shoppinglistid = " + shoppingListId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        User[] users = ShoppingListDAO.getShoppingListUsers(3);
        System.out.println("stop");
    }
}
