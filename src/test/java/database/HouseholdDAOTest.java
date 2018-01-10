package database;

import classes.Household;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;

public class HouseholdDAOTest {

    DBConnector dbc = new DBConnector();
    Connection conn;
    Statement st;

    @Before
    public void setUp() throws Exception {
        conn = dbc.getConn();
        st = conn.createStatement();
    }

    @Test
    public void addNewHouseHold() throws Exception {
        Household household = new Household();
        household.setName("Kollektivet");
        household.setAdress("Bananvegen 27");

        HouseholdDAO.addNewHouseHold(household);

        String query = "SELECT * FROM Household WHERE house_name='Kollektivet' and house_address='Bananvegen 27'";
        ResultSet rs = st.executeQuery(query);

        String name = "";
        String address = "";

        while (rs.next()){
            name = rs.getString("house_name");
            address = rs.getString("house_address");
        }

        assertEquals("Kollektivet", name);
        assertEquals("Bananvegen 27", address);
    }

    @Test
    public void getHousehold() throws Exception {
    }

    @Test
    public void updateHousehold() throws Exception {
    }

    @Test
    public void deleteUser() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
        st.close();
        dbc.disconnect();
    }
}