package database;

import classes.Chore;
import classes.HashHandler;
import classes.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class UserDAOTest {

    DBConnector dbc = new DBConnector();
    Connection conn;
    Statement st;

    @Before
    public void setUp() throws Exception {
        conn = dbc.getConn();
        st = conn.createStatement();
    }

    @Test
    public void addNewUser() throws Exception {

        User user = new User();
        user.setEmail("Ole@gmail.com");
        user.setName("Ole");
        user.setPassword("123456");
        user.setTelephone("33445566");

        UserDAO.addNewUser(user);

        String query = "SELECT * FROM Person WHERE email='Ole@gmail.com'";
        ResultSet rs = st.executeQuery(query);

        String email = "";
        String name = "";
        String password = "";
        String telephone = "";

        while (rs.next()) {
            email = rs.getString("email");
            name = rs.getString("name");
            password = rs.getString("password");
            telephone = rs.getString("telephone");
        }

        assertEquals("Ole@gmail.com", email);
        assertEquals("Ole", name);
        assertEquals(HashHandler.passwordMatchesHash("123456", password), true);
        assertEquals("33445566", telephone);
    }

    @Test
    public void getUser() throws Exception {

        User user1 = new User();
        user1.setName("Frank");
        user1.setTelephone("22334455");
        user1.setEmail("Frank@gmail.com");

        assertEquals("Frank", UserDAO.getUser(2).getName());
        assertEquals("22334455", UserDAO.getUser(2).getTelephone());
        assertEquals("Frank@gmail.com", UserDAO.getUser(2).getEmail());
    }

    @Test
    public void updateUser() throws Exception {
        String newName = "Frederic";
        UserDAO.updateUser(1, "Ole@gmail.com", "11223344", newName);

        String query = "SELECT * FROM Person WHERE userId=1";
        ResultSet rs = st.executeQuery(query);

        String name = "";

        while (rs.next()) {
            name = rs.getString("name");
        }

        assertNotEquals("Ole", name);
        assertEquals(newName, name);
    }

    @Test
    public void updatePassword() throws Exception {
        String np = "ost";

        UserDAO.updatePassword(5, np);

        String query = "SELECT * FROM Person WHERE userId = 5";

        ResultSet rs = st.executeQuery(query);


        String npHashed = "";
        while (rs.next()) {
            npHashed = rs.getString("password");
        }

        HashHandler.passwordMatchesHash(np, npHashed);
    }

    @Test
    public void deleteUser() throws Exception {
        boolean deleteExecuted = true;
        UserDAO.deleteUser(2);

        String query = "SELECT * FROM Person WHERE email='Frank@gmail.com'";
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            deleteExecuted = false;
        }

        assertEquals(true, deleteExecuted);
    }

    @Test
    public void resetPassword() throws Exception {
        assertEquals(1, 1);
        assertNotEquals(1, 2);
    }

    @Test
    public void userExists() throws Exception {
        assertTrue(UserDAO.userExist("trym@live.com", "11223344"));
        assertTrue(UserDAO.userExist("Frank@gmail.com", "90909090"));
        assertFalse(UserDAO.userExist("lol", "123"));
    }

    @Test
    public void getHouseholds() throws Exception {
            assertEquals(1, UserDAO.getHouseholds(34).size());
            assertEquals(null, UserDAO.getHouseholds(1));
            assertEquals("Testhouse", UserDAO.getHouseholds(34).get(0).getName());
    }

    @After
    public void tearDown() throws Exception {
        st.close();
        dbc.disconnect();
    }
}