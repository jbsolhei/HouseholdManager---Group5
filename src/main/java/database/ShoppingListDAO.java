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
        String shoppingListName = "";
        int userId = 0;
        String personName = "";
        String email = "";
        String telephone = "";

        String query = "SELECT Shopping_list.shopping_listId, Shopping_list.name, User_Shopping_list.userId, Person.name, Person.email, Person.telephone FROM Shopping_list RIGHT JOIN User_Shopping_list ON Shopping_list.shopping_listId = User_Shopping_list.shopping_listId RIGHT JOIN Person ON User_Shopping_list.userId = Person.userId WHERE Shopping_list.houseId = ? ORDER BY Shopping_list.shopping_listId;";
        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setInt(1, houseId);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                shoppingListId = rs.getInt("shopping_listId");
                shoppingListName = rs.getString("Shopping_list.name");
                userId = rs.getInt("userId");
                personName = rs.getString("Person.name");
                email = rs.getString("email");
                telephone = rs.getString("telephone");

                User user = new User();
                user.setUserId(userId);
                user.setName(personName);
                user.setEmail(email);
                user.setPhone(telephone);

                users.add(user);

                while (rs.next()) {
                    int newShoppingListId = rs.getInt("shopping_listId");
                    shoppingListName = rs.getString("Shopping_list.name");
                    userId = rs.getInt("userId");
                    personName = rs.getString("Person.name");
                    email = rs.getString("email");
                    telephone = rs.getString("telephone");

                    if (newShoppingListId != shoppingListId) {
                        ShoppingList sl = new ShoppingList();
                        sl.setName(shoppingListName);
                        sl.setParticipants(toUserArray(users));
                        sl.setShoppingListId(shoppingListId);
                        shoppingLists.add(sl);
                        users.clear();
                    }

                    user = new User();
                    user.setUserId(userId);
                    user.setName(personName);
                    user.setEmail(email);
                    user.setPhone(telephone);

                    users.add(user);
                }

                ShoppingList sl = new ShoppingList();
                sl.setShoppingListId(shoppingListId);
                sl.setParticipants(toUserArray(users));
                sl.setName(shoppingListName);

                shoppingLists.add(sl);

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
                    user.setPhone(telephone);
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

    public static int createShoppingList(String name, int houseId, int[] userIds) {
        String query_sl = "INSERT INTO Shopping_list (name, houseId) VALUES (?, ?);";
        String query_user_sl = "INSERT INTO User_Shopping_list (userId, shopping_listId) VALUES (?, ?);";
        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st_user_sl = conn.prepareStatement(query_user_sl);
             PreparedStatement st_sl = conn.prepareStatement(query_sl, Statement.RETURN_GENERATED_KEYS)) {

            st_sl.setString(1, name);
            st_sl.setInt(2, houseId);

            int res = st_sl.executeUpdate();
            ResultSet rs = st_sl.getGeneratedKeys();

            for (int i = 0; i < userIds.length; i++) {
                st_user_sl.setInt(1, userIds[i]);
                st_user_sl.setInt(2, 8);
                st_user_sl.executeUpdate(query_user_sl);
            }

            return 8;
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
        int[] userIds = {1, 2, 3};
        int key = ShoppingListDAO.createShoppingList("Innflyttingsfest", 1, userIds);
        System.out.println("stop");
    }
}
