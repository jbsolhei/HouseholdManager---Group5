package database;

import classes.Household;
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
import static org.junit.Assert.fail;

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
    public void createShoppingList() throws Exception {

        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setName("Tacofredag!");

        ShoppingListDAO.createShoppingList(shoppingList, 1);

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
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setName("Tacofredag!");

        ShoppingListDAO.deleteShoppingList(1, 4);
        String query = "SELECT * FROM Shopping_list WHERE name='Tacofredag!' and houseId='1'";
        ResultSet rs = st.executeQuery(query);

        String name = "";
        int houseId = 0;

        if(rs.next()){
            assert false;
        } else {
            assert true;
        }
    }

    @Test
    public void getItems() throws Exception {
        Item[] items = ShoppingListDAO.getItems(1);
        assert items!=null;
        assertEquals(3,items.length);
    }

    @Test
    public void getShoppingLists() throws Exception {
        ShoppingList[] shoppingLists = ShoppingListDAO.getShoppingLists(1);
        assert shoppingLists != null;

        assertEquals(3,shoppingLists.length);
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


}