package database;

import classes.Item;
import classes.ShoppingList;
import classes.User;

import java.sql.*;
import java.util.ArrayList;

/**
 * <p>ShoppingListDAO class.</p>
 *
 */
public class ShoppingListDAO {

    /**
     * Get's all shopping Lists in a household for a given User
     *
     * @param houseId the house ID
     * @param userId  the user ID
     * @return an array of shopping lists, or null if an exception is thrown
     */
    public static ShoppingList[] getShoppingListsUser(int houseId, int userId) {
        ArrayList<ShoppingList> shoppingLists = new ArrayList<>();
        ShoppingList shoppingList = null;
        int shoppingListId = 0;
        String shoppingListName = "";
        boolean isArchived = false;
        String query = "SELECT User_Shopping_list.userId, Shopping_list.shopping_listId, Shopping_list.name, Shopping_list.houseId, Shopping_list.archived FROM User_Shopping_list RIGHT JOIN Shopping_list ON User_Shopping_list.shopping_listId=Shopping_list.shopping_listId WHERE Shopping_list.houseId = ? AND User_Shopping_list.userId = ? ORDER BY shopping_listId DESC;";
        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setInt(1, houseId);
            st.setInt(2, userId);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    shoppingList = new ShoppingList();

                    shoppingListId = rs.getInt("Shopping_list.shopping_listId");
                    shoppingListName = rs.getString("Shopping_list.name");
                    isArchived = rs.getBoolean("Shopping_list.archived");
                    shoppingList.setShoppingListId(shoppingListId);
                    shoppingList.setName(shoppingListName);
                    shoppingList.setArchived(isArchived);
                    shoppingLists.add(shoppingList);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return toShoppingListArray(shoppingLists);
    }

    /**
     * Get all shoppingslists in a household.
     *
     * @param houseId the house ID
     * @return an Arraylist of shoppinglists, or null if a user with the given ID doesn't exist.
     */
    public static ShoppingList[] getShoppingListsAdmin(int houseId) {
        ArrayList<ShoppingList> shoppingLists = new ArrayList<>();
        ShoppingList shoppingList = null;
        int shoppingListId = 0;
        String shoppingListName = "";
        boolean isArchved = false;

        String query = "SELECT Shopping_list.shopping_listId, Shopping_list.name, Shopping_list.archived FROM Shopping_list WHERE Shopping_list.houseId = ? ORDER BY Shopping_list.shopping_listId DESC;";
        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setInt(1, houseId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    shoppingList = new ShoppingList();

                    shoppingListId = rs.getInt("shopping_listId");
                    shoppingListName = rs.getString("shopping_list.name");
                    isArchved = rs.getBoolean("Shopping_list.archived");

                    shoppingList.setShoppingListId(shoppingListId);
                    shoppingList.setName(shoppingListName);
                    shoppingList.setArchived(isArchved);

                    shoppingLists.add(shoppingList);
                }
            }
                return toShoppingListArray(shoppingLists);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Gives you a shopping list given the shopping list ID
     *
     * @param shoppingListId the shopping list ID
     * @return a ShoppingList object
     */
    public static ShoppingList getShoppingList(int shoppingListId) {
        ShoppingList shoppingList = new ShoppingList();
        ArrayList<User> users = new ArrayList<>();
        String query = "SELECT Shopping_list.shopping_listId, Shopping_list.name, Shopping_list.archived, User_Shopping_list.userId, Person.name, Person.name FROM Shopping_list RIGHT JOIN User_Shopping_list ON Shopping_list.shopping_listId=User_Shopping_list.shopping_listId RIGHT JOIN Person ON User_Shopping_list.userId = Person.userId WHERE Shopping_list.shopping_listId = ?;";
        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setInt(1, shoppingListId);
            try (ResultSet rs = st.executeQuery()) {
                User user = new User();
                if (rs.next()) {
                    String shoppingListName = rs.getString("Shopping_list.name");
                    boolean isArchived = rs.getBoolean("Shopping_list.archived");
                    shoppingList.setShoppingListId(shoppingListId);
                    shoppingList.setName(shoppingListName);
                    shoppingList.setArchived(isArchived);
                    int userId = rs.getInt("userId");
                    String personName = rs.getString("Person.name");
                    user.setUserId(userId);
                    user.setName(personName);
                    users.add(user);
                    user = new User();

                    while (rs.next()) {
                        userId = rs.getInt("userId");
                        personName = rs.getString("Person.name");
                        user.setUserId(userId);
                        user.setName(personName);
                        users.add(user);
                        user = new User();
                    }
                    shoppingList.setUsers(toUserArray(users));
                }
            }
            shoppingList.setItems(ShoppingListDAO.getItems(shoppingListId));
            return shoppingList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Finds all users that is can view a shopping list
     *
     * @param shoppingListId the shopping list ID
     * @return an array of users, or null if an exception is thrown
     */
    public static User[] getUsersInShoppingList(int shoppingListId) {
        ArrayList<User> users = new ArrayList<>();
        int userId;
        String email;
        String name;
        String telephone;

        String query = "SELECT User_Shopping_list.userId, Person.* FROM User_Shopping_list  INNER JOIN Person ON User_Shopping_list.userId = Person.userId WHERE shopping_listId = ?";

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setInt(1, shoppingListId);

            try(ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    userId = rs.getInt("Person.userId");
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get all the items in one specific shopping list
     *
     * @param shopping_listId the shopping list ID
     * @return an array of items or null of an exception is thrown
     */
    public static Item[] getItems(int shopping_listId) {
        ArrayList<Item> items = new ArrayList<>();
        int itemId;
        String itemName;
        int userId;
        String personName;

        String query = "SELECT Item.shopping_listId, itemId, Item.name, checkedBy, Person.name FROM Item LEFT JOIN Person ON Item.checkedBy=Person.userId WHERE shopping_listId = ?;";

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
                        personName = rs.getString("Person.name");
                        user.setName(personName);
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
            return null;
        }
    }

    /**
     * Creates a new shopping list with no items and no associated users
     *
     * @param shoppingList the name of the shopping list
     * @param houseId the house ID
     * @return the automatically generated shopping list ID
     */
    public static int createShoppingList(String shoppingList, int houseId) {
        String name = shoppingList;
        String query = "INSERT INTO Shopping_list (name, houseId) VALUES (?,?)";

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            st.setString(1, name);
            st.setInt(2, houseId);
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

    /**
     * Delete
     *
     * @param houseId a int.
     * @param shopping_list_id a int.
     * @return a int.
     */
    public static int deleteShoppingList(int houseId, int shopping_list_id) {
        String query = "DELETE FROM Shopping_list WHERE houseId = ? AND shopping_listId = ?";

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setInt(2, shopping_list_id);
            st.setInt(1, houseId);

            return st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * * Inserts a row to the Item table, and associates it with a shopping list
     *
     * @param itemName the name of the item to be added to the database
     * @param shopping_list_id the shopping list ID of the shopping list to be associated with the item
     * @return -1 if an error occurred, returns 1 if no errors occurred
     */
    public static int addItem(String itemName, int shopping_list_id) {
        String query = "INSERT INTO Item(name, shopping_listId) VALUES (?, ?);";

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setString(1, itemName);
            st.setInt(2, shopping_list_id);
            return st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Deletes a row in the Item table given the item ID and associated shopping list ID
     *
     * @param shopping_list_id the shopping list ID of the associated shopping list
     * @param itemId the item ID of the item to be deleted
     * @return a int.
     */
    public static int deleteItem(int shopping_list_id, int itemId) {
        String query = "DELETE FROM Item WHERE shopping_listId = ? AND itemId = ?;";

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setInt(1, shopping_list_id);
            st.setInt(2, itemId);
            return st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Deletes and inserts rows into the User_Shopping_list
     *
     * @param userIds an array of user IDs.
     * @param shoppingListId a int.
     */
    public static void updateUsers(int[] userIds, int shoppingListId) {
        String delete = "DELETE FROM User_Shopping_list WHERE shopping_listId = ?";
        String query = "INSERT INTO User_Shopping_list (userId, shopping_listId) VALUES (?, ?);";

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement del_st = conn.prepareStatement(delete);
             PreparedStatement st = conn.prepareStatement(query)) {

            del_st.setInt(1, shoppingListId);
            del_st.executeUpdate();

            for (int userId : userIds) {
                st.setInt(1, userId);
                st.setInt(2, shoppingListId);
                int rtn = st.executeUpdate();
                if (rtn < 0) System.err.println("Could not update: " + userId + " into shoppinglist where shoppinglistid = " + shoppingListId);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates a column in the Item table, and sets the checkedBy to a userId
     *
     * @param userId the user ID of the user who checked off the item
     * @param itemId the item ID of the item that has been checked off
     * @return number of rows that have been altered, or -1 if an error occurred
     */
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

    /**
     * Updates the 'archived' column given the shopping list ID
     *
     * @param shoppingListId the shopping list ID
     * @param archived the wanted column value
     * @return a int.
     */
    public static int updateArchived(int shoppingListId, boolean archived) {
        String query = "UPDATE Shopping_list SET archived = ? WHERE shopping_listId = ?;";
        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setBoolean(1, archived);
            st.setInt(2, shoppingListId);

            return st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Updates a row in the User_Shopping_list table
     * if 'delete' == true, a row will be deleted
     * if not a row will be added
     *
     * @param shoppingListId the shopping list ID
     * @param userId the user ID
     * @param delete decides if the method should delete or add
     * @return -1 if an error occurs, 1 if no errors occurred
     */
    public static int updateUserInShoppingList(int shoppingListId, int userId, boolean delete) {
        String query;
        if (delete) query = "DELETE FROM User_Shopping_list WHERE userId=? AND shopping_listId=?";
        else query = "INSERT INTO User_Shopping_list (userId, shopping_listId) VALUES (?, ?)";

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {
            st.setInt(1, userId);
            st.setInt(2, shoppingListId);

            return st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
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
     * <p>main.</p>
     *
     * @param args an array of {@link java.lang.String} objects.
     */
    public static void main (String[] args) {
        int rtn = ShoppingListDAO.updateArchived(69, false);
        System.out.println("stop");
    }
}
