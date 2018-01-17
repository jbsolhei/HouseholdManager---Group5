package database;

import classes.ShoppingTrip;
import classes.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Camilla Velvin on 16.01.2018.
 */
public class ShoppingTripDAOTest {
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
    public void getShoppingTrips() throws Exception{
        String query = "SELECT * FROM Shopping_trip";
        ResultSet rs = st.executeQuery(query);
        List<String> names = new ArrayList<>();
        String name = "";
        while (rs.next()) {
            name = rs.getString("shopping_tripName");
            names.add(name);
        }
        assertEquals("Test", names.get(0));
        assertEquals("Test2", names.get(1));
    }

    @Test
    public void createShoppingTrip() throws Exception {

        ShoppingTrip shoppingTrip = new ShoppingTrip();
        shoppingTrip.setName("Test trip");
        shoppingTrip.setExpence(23);
        shoppingTrip.setComment("This is a test trip");
        shoppingTrip.setShoppingDate(LocalDate.now());
        shoppingTrip.setHouseId(1);
        shoppingTrip.setShopping_listId(1);
        shoppingTrip.setUserId(1);
        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setUserId(1);
        User user2 = new User();
        user2.setUserId(5);
        users.add(user1);
        users.add(user2);
        shoppingTrip.setContributors(users);


        ShoppingTripDAO.createShoppingTrip(shoppingTrip);

        String query = "SELECT * FROM Shopping_trip WHERE shopping_tripName='Test trip' and houseId='1'";
        ResultSet rs = st.executeQuery(query);

        String name = "";
        String comment = "";
        double expence = 0;
        int houseId = 0;

        while (rs.next()){
            name = rs.getString("shopping_tripName");
            houseId = rs.getInt("houseId");
            comment = rs.getString("comment");
            expence = rs.getDouble("expence");
        }
        String query1 = "SELECT us.userId FROM User_Shopping_trip us, Shopping_trip st WHERE " +
                "us.shopping_tripId = st.shopping_tripId AND st.shopping_tripName='Test trip' and st.houseId='1'";
        ResultSet rs1 = st.executeQuery(query1);

        List<Integer> ids = new ArrayList<>();
        int id = 0;
        while (rs1.next()) {
            id = rs1.getInt("userId");
            ids.add(id);
        }

        assertEquals(2, ids.size());
        assertEquals("Test trip", name);
        assertEquals(1, houseId);
        assertEquals("This is a test trip", comment);

    }


}
