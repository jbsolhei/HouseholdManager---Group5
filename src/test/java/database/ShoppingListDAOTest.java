package database;

import classes.Item;
import classes.ShoppingList;
import classes.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;

/**
 * Created by camhl on 1/12/2018.
 */
public class ShoppingListDAOTest {
    DBConnector dbc = new DBConnector();
    Connection conn;
    Statement st;

    @Before
    public void setUp() throws Exception {
        conn = dbc.getConn();
        st = conn.createStatement();
    }


    @After
    public void tearDown() throws Exception {
        st.close();
        dbc.disconnect();
    }

    @Test
    public void getShoppingListAdmin() throws Exception {
        ShoppingList[] shoppingLists = ShoppingListDAO.getShoppingListsAdmin(1);
        assertEquals(5, shoppingLists.length);
    }

    @Test
    public void getShoppingListUser() throws Exception {
        ShoppingList[] shoppingLists = ShoppingListDAO.getShoppingListsUser(1, 50);
        assertEquals(1, shoppingLists.length);
    }

    @Test
    public void getUsersInShoppingList() throws Exception {
        User[] users = ShoppingListDAO.getUsersInShoppingList(1);
        assertEquals(1, users.length);
    }

    @Test
    public void createShoppingList() throws Exception {

        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setName("Tacofredag!");

        ShoppingListDAO.createShoppingList(shoppingList.getName(), 1);

        String query = "SELECT * FROM Shopping_list WHERE name='Tacofredag!' and houseId='1'";
        ResultSet rs = st.executeQuery(query);

        String name = "";
        int houseId = 0;

        while (rs.next()){
            name = rs.getString("name");
            houseId = rs.getInt("houseId");
        }

        assertEquals("Tacofredag!", name);
        assertEquals(1, houseId);


    }

    @Test
    public void deleteShoppingList() throws Exception{
        ShoppingListDAO.deleteShoppingList(1, 4);
        String query = "SELECT * FROM Shopping_list WHERE shopping_listId='4' and houseId='1'";
        ResultSet rs = st.executeQuery(query);

        boolean result = true;
        while(rs.next()){
            result = false;
        }
        assert result;
    }

    @Test
    public void getItems() throws Exception {
        Item[] items = ShoppingListDAO.getItems(1);
        assert items!=null;
        assertEquals(3,items.length);
    }

    @Test
    public void addItem() throws Exception {

        Item item = new Item();
        item.setName("Pastasaus");

        ShoppingListDAO.addItem(item, 2);

        String query = "SELECT * FROM Item WHERE name='Pastasaus' and shopping_listId='2'";
        ResultSet rs = st.executeQuery(query);

        String name = "";
        int shopping_listId = 0;

        while (rs.next()){
            name = rs.getString("name");
            shopping_listId = rs.getInt("shopping_listId");
        }

        assertEquals("Pastasaus", name);
        assertEquals(2, shopping_listId);

    }

    @Test
    public void deleteItem() throws Exception{

        Item item = new Item();
        item.setName("Pastasaus");

        ShoppingListDAO.deleteItem(2, 4);

        String query = "SELECT * FROM Item WHERE name='Pastasaus' and shopping_listId='2'";
        ResultSet rs = st.executeQuery(query);

        if(rs.next()){
            assert false;
        }
        else{
            assert true;
        }

    }
    @Test
    public void updateUsers() throws Exception {
        String[] userIds = {"1", "50"};
        ShoppingListDAO.updateUsers(userIds, 1);

        String query = "SELECT * FROM User_Shopping_list WHERE shopping_listId = 1";
        ResultSet rs = st.executeQuery(query);

        int i = 0;
        while (rs.next()) {
            i++;
        }
        assertEquals(2, i);
    }

    @Test
    public void updateCheckedBy() throws Exception {
        String query = "SELECT * FROM Item WHERE itemId = 1";

        int rtn = ShoppingListDAO.updateCheckedBy(50, 1);
        assertEquals(1, rtn);

        ResultSet rs = st.executeQuery(query);
        if (rs.next()) {
            assertEquals(50, rs.getInt("checkedBy"));
        } else assert false;

        rtn = ShoppingListDAO.updateCheckedBy(0, 1);
        assertEquals(1, rtn);

        rs = st.executeQuery(query);
        if (rs.next()) {
            assertEquals(0, rs.getInt("checkedBy"));
        } else assert false;
    }

    @Test
    public void updateArchived() throws Exception {
        String query = "SELECT * FROM Shopping_list WHERE shopping_listId = 1";

        ShoppingListDAO.updateArchived(1, true);
        ResultSet rs = st.executeQuery(query);
        if (rs.next()) {
            assertEquals(true, rs.getBoolean("archived"));
        } else assert false;

        ShoppingListDAO.updateArchived(1, false);
        rs = st.executeQuery(query);
        if (rs.next()) {
            assertEquals(false, rs.getBoolean("archived"));
        } else assert false;
    }

    @Test
    public void updateUserInShoppingList() throws Exception {
        String delete = "DELETE FROM User_Shopping_list WHERE shopping_listId = 2 AND userId = 50";
        st.executeUpdate(delete);

        String query = "SELECT * FROM User_Shopping_list WHERE shopping_listId = 2 AND userId = 50";

        int rtn = ShoppingListDAO.updateUserInShoppingList(2, 50, false);
        if (rtn != -1) assert true;
        else assert false;

        ResultSet rs = st.executeQuery(query);
        if (rs.next()) assert true;
        else assert false;

        rtn = ShoppingListDAO.updateUserInShoppingList(2, 50, true);
        if (rtn != -1) assert true;
        else assert false;

        rs = st.executeQuery(query);
        if (rs.next()) assert false;
        else assert true;
    }
}