package database;

import classes.ShoppingList;
import classes.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

                return toShoppingListArray(shoppingLists);

            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
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

    public static void main (String[] args) {
        ShoppingList[] shoppingLists = ShoppingListDAO.getShoppingLists(1);
        System.out.println("stop");
    }
}
