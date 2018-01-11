package database;

import classes.HashHandler;
import classes.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

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

        while (rs.next()){
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
        user1.setName("Trym");
        user1.setTelephone("11223344");
        user1.setEmail("trym@gmail.com");
        User user2 = null;

        assertEquals("Trym", UserDAO.getUser(1).getName());
        assertEquals("11223344", UserDAO.getUser(1).getTelephone());
        assertEquals("trym@gmail.com", UserDAO.getUser(1).getEmail());
        assertEquals(user2, UserDAO.getUser(4));
    }

    @Test
    public void updateUser() throws Exception {
        String newName = "Frederic";
        String email = "Ole@gmail.com";
        UserDAO.updateUser(3,"Ole@gmail.com", "11223344", newName);

        String query = "SELECT * FROM Person WHERE email='Ole@gmail.com'";
        ResultSet rs = st.executeQuery(query);

        String name = "";

        while (rs.next()){
            name = rs.getString("name");
        }

        assertNotEquals("Ole", name);
        assertEquals(newName, name);
    }

    @Test
    public void updatePassword() throws Exception {
        String newPassword = "654321";
        String email = "Ole@gmail.com";

        UserDAO.updatePassword(3, newPassword);

        String query = "SELECT * FROM Person WHERE email='Ole@gmail.com'";
        ResultSet rs = st.executeQuery(query);

        String password = "";

        while (rs.next()){
            password = rs.getString("password");
        }

        assertEquals(HashHandler.passwordMatchesHash("654321", password), true);
    }

    @Test
    public void deleteUser() throws Exception {
        String email = "Frank@gmail.com";
        boolean deleteExecuted = true;
        UserDAO.deleteUser(2);

        String query = "SELECT * FROM Person WHERE email='Frank@gmail.com'";
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            deleteExecuted = false;
        }

        assertEquals(true, deleteExecuted);
    }

    @After
    public void tearDown() throws Exception {
        st.close();
        dbc.disconnect();
    }
}