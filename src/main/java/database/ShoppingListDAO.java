package database;

import classes.Item;
import classes.ShoppingList;
import classes.User;

import java.sql.*;
import java.util.ArrayList;

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
        ArrayList<Item> items = new ArrayList<>();
        int shoppingListId = 0;
        int shoppingListId2 = 0;
        String shoppingListName = "";
        boolean thereAreLists = false;
        int itemId = 0;
        String itemName;
        int checkedBy = 0;
        String query = "SELECT User_Shopping_list.userId, Shopping_list.shopping_listId, Shopping_list.name, Shopping_list.houseId, Item.itemId, Item.name, Item.checkedBy, Person.userId, Person.name FROM User_Shopping_list RIGHT JOIN Shopping_list ON User_Shopping_list.shopping_listId=Shopping_list.shopping_listId LEFT JOIN Item ON Item.shopping_listId = Shopping_list.shopping_listId LEFT JOIN Person ON Item.checkedBy = Person.userId WHERE Shopping_list.houseId = ? AND User_Shopping_list.userId = ?;";
        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setInt(1, houseId);
            st.setInt(2, userId);

            try (ResultSet rs = st.executeQuery()) {
                ShoppingList shoppingList = null;
                if (rs.next()) {
                    thereAreLists = true;
                    shoppingList = new ShoppingList();
                    Item item = new Item();
                    User userCheckedBy = null;

                    shoppingListId = rs.getInt("Shopping_list.shopping_listId");
                    shoppingListName = rs.getString("Shopping_list.name");
                    itemId = rs.getInt("Item.itemId");
                    itemName = rs.getString("Item.name");
                    checkedBy = rs.getInt("Item.checkedBy");
                    if (checkedBy != 0) {
                        userCheckedBy = new User();
                        String userName = rs.getString("Person.name");
                        userCheckedBy.setName(userName);
                    }
                    item.setItemId(itemId);
                    item.setName(itemName);
                    item.setCheckedBy(userCheckedBy);
                    items.add(item);
                    shoppingList.setShoppingListId(shoppingListId);
                    shoppingList.setName(shoppingListName);

                    while (rs.next()) {
                        shoppingListId2 = rs.getInt("Shopping_list.shopping_listId");

                        if (shoppingListId != shoppingListId2) {
                            shoppingList.setItems(toItemArray(items));
                            shoppingLists.add(shoppingList);
                            items.clear();

                            shoppingList = new ShoppingList();
                            shoppingListName = rs.getString("Shopping_list.name");
                            shoppingList.setShoppingListId(shoppingListId2);
                            shoppingList.setName(shoppingListName);
                            shoppingListId = shoppingListId2;
                        }

                        item = new Item();
                        userCheckedBy = null;
                        itemId = rs.getInt("Item.itemId");
                        itemName = rs.getString("Item.name");
                        checkedBy = rs.getInt("Item.checkedBy");
                        if (checkedBy != 0) {
                            userCheckedBy = new User();
                            String userName = rs.getString("Person.name");
                            userCheckedBy.setName(userName);
                        }
                        item.setItemId(itemId);
                        item.setName(itemName);
                        item.setCheckedBy(userCheckedBy);
                        items.add(item);
                    }
                }

                if (thereAreLists) {
                    shoppingList.setItems(toItemArray(items));
                    shoppingLists.add(shoppingList);

                    for (ShoppingList sl : shoppingLists) {
                        User[] users = ShoppingListDAO.getUsersInShoppingList(sl.getShoppingListId());
                        sl.setUsers(users);
                    }
                    return toShoppingListArray(shoppingLists);
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

            if (thereAreLists) {
                ShoppingList sl = new ShoppingList();
                sl.setName(shoppingListName);
                sl.setUsers(toUserArray(users));
                sl.setShoppingListId(shoppingListId2);
                shoppingLists.add(sl);
                users.clear();

                for (ShoppingList shoppingList : shoppingLists) {
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

    /**
     * Gives you a shopping list given the shopping list ID
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
                    shoppingList.setShoppingListId(shoppingListId);
                    shoppingList.setName(shoppingListName);
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
     * @param houseId
     * @param shopping_list_id
     */
    public static void deleteShoppingList(int houseId, int shopping_list_id) {
        String query = "DELETE FROM Shopping_list WHERE houseId = ? AND shopping_listId = ?";

        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setInt(2, shopping_list_id);
            st.setInt(1, houseId);

            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addItem(Item items, int shopping_list_id) {
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

    public static void deleteItem(int shopping_list_id, int itemId) {
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
            del_st.executeUpdate();

            for (String userId : userIds) {
                st.setInt(1, Integer.parseInt(userId));
                st.setInt(2, shoppingListId);
                int rtn = st.executeUpdate();
                if (rtn < 0) System.err.println("Could not update: " + userIds + " into shoppinglist where shoppinglistid = " + shoppingListId);
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

    /**
     * Updates the 'archived' column given the shopping list ID
     * @param shoppingListId the shopping list ID
     * @param archived the wanted column value
     */
    public static void updateArchived(int shoppingListId, boolean archived) {
        String query = "UPDATE Shopping_list SET archived = ? WHERE shopping_listId = ?;";
        try (DBConnector dbc = new DBConnector();
             Connection conn = dbc.getConn();
             PreparedStatement st = conn.prepareStatement(query)) {

            st.setBoolean(1, archived);
            st.setInt(2, shoppingListId);

            st.executeUpdate();

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

    public static void main (String[] args) {
        ShoppingListDAO.updateArchived(66, false);
        System.out.println("stop");
    }
}
