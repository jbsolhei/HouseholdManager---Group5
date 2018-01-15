package database;

import classes.Household;
import classes.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
    public void getMembers() throws Exception {
        User[] members = HouseholdDAO.getMembers(1);
        assert members!=null;
        assertEquals(2,members.length);
    }

    @Test
    public void addUserToHousehold() throws Exception {
        HouseholdDAO.addUserToHousehold(2,35,0);

        String query = "SELECT * FROM House_user WHERE houseId=2";
        ResultSet rs = st.executeQuery(query);

        int userid = 0;

        while (rs.next()){
            userid = rs.getInt("userId");
        }

        assertEquals(35, userid);
    }


    @Test
    public void addNewHouseHold() throws Exception {

        Household household = new Household();
        household.setName("Kollektivet");
        household.setAddress("Bananvegen 27");
        User[] admins = new User[1];
        User u1 = new User();
        u1.setUserId(1);
        u1.setName("OLE");
        admins[0] = u1;

        household.setAdmins(admins);
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
        Household temp = HouseholdDAO.getHousehold(1);
        assertEquals("Testhouse",temp.getName());
        assertEquals("Testaddress 22",temp.getAddress());
    }

    @Test
    public void updateHousehold() throws Exception {
        Household newHouse = new Household();
        newHouse.setName("Newname");
        newHouse.setAddress("Newaddress");
        HouseholdDAO.updateHousehold(2, newHouse);

        String query = "SELECT * FROM Household WHERE houseId=2";
        ResultSet rs = st.executeQuery(query);

        String name = "";
        String address = "";

        while (rs.next()){
            name = rs.getString("house_name");
            address = rs.getString("house_address");
        }

        assertEquals("Newname", name);
        assertEquals("Newaddress", address);
    }

    @Test
    public void deleteHouse() throws Exception {
        HouseholdDAO.deleteHousehold(3);

        String query = "SELECT * FROM Household WHERE houseId=3";
        ResultSet rs = st.executeQuery(query);

        while (rs.next()){
            fail();
        }
    }

    @After
    public void tearDown() throws Exception {
        st.close();
        dbc.disconnect();
    }
}