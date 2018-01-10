package database;

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
        user.setPhone("11223344");

        UserDAO.addNewUser(user);

        String query = "SELECT * FROM Person WHERE name='Ole' and email='Ole@gmail.com' and password='123456' and telephone='11223344'";
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
        assertEquals("123456", password);
        assertEquals("11223344", telephone);
    }

    @Test
    public void getUser() throws Exception {
        String existingEmail = "trym@gmail.com";
        String nonExistingEmail = "frederic@gmail.com";

        String[] info1 = {"Trym", "11223344"};
        String[] info2 = null;

        assertArrayEquals(info1, UserDAO.getUser(existingEmail));
        assertArrayEquals(info2, UserDAO.getUser(nonExistingEmail));
    }

    @Test
    public void updateUser() throws Exception {
        String newName = "Frederic";
        String email = "Ole@gmail.com";
        UserDAO.updateUser(email,"Ole@gmail.com", "11223344", newName);

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

        UserDAO.updatePassword(email, newPassword);

        String query = "SELECT * FROM Person WHERE email='Ole@gmail.com'";
        ResultSet rs = st.executeQuery(query);

        String password = "";

        while (rs.next()){
            password = rs.getString("password");
        }

        assertNotEquals("123456", password);
        assertEquals(newPassword, password);
    }

    @Test
    public void deleteUser() throws Exception {
        String email = "Frank@gmail.com";
        boolean deleteExecuted = true;
        UserDAO.deleteUser(email);

        String query = "SELECT * FROM Person WHERE email='Frank@gmail.com'";
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            deleteExecuted = false;
        }

        assertEquals(true, deleteExecuted);
    }

    @After
    public void teatDown() throws Exception {
        st.close();
        dbc.disconnect();
    }
}