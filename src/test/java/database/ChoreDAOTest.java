package database;

import classes.Chore;
import classes.Household;
import classes.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ChoreDAOTest {
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
    public void postChore() throws Exception{
        ArrayList<Chore> chores = new ArrayList<>();
        Chore chore = new Chore();
        User user = new User();


            user.setUserId(100);
            chore.setDescription("Ta ut av oppvaskmaskinen");
            chore.setHouseId(1);
            chore.setUser(user);
            chore.setDone(false);
            chore.setDate("2018/01/20");


        ChoreDAO.postChore(chore);

        String query = "SELECT * FROM Chore WHERE houseId = 1";

        ResultSet rs = st.executeQuery(query);

        if(rs.next()) {
            assertEquals(2, rs.getInt("choreId"));
            assertEquals(chore.getDescription(), rs.getString("description"));
            assertEquals(chore.getUser().getUserId(), rs.getInt("userId"));
            assertEquals(chore.getHouseId(), rs.getInt("houseId"));
            assertEquals(0, rs.getInt("done"));
            assertEquals(chore.getDate(), rs.getDate("chore_date"));
        }
        else{
            assert false;
        }
    }

    @Test
    public void getChores() {
        Household household = new Household();
        household.setHouseId(10);
        ArrayList<Chore> chores = ChoreDAO.getChores(household);

        assertEquals(1, chores.size());
        assertEquals("Ta ut søpla", chores.get(0).getDescription());
        assertEquals(51, chores.get(0).getUser().getUserId());
        assertEquals(false, chores.get(0).isDone());
        assertEquals("2018-01-20", chores.get(0).getDate().toString());

    }

    @Test
    public void deleteChore() {

    }

    @Test
    public void editChore() throws Exception{
        String query = "SELECT * FROM Chore WHERE choreId = 2;";

        ResultSet rs = st.executeQuery(query);

        if(rs.next()) {
            assertEquals(0, rs.getInt("done"));
            assertEquals("Ta ut av oppvaskmaskinen", rs.getString("description"));
        }
        rs.close();

        Chore chore = new Chore();
        User user = new User();

        user.setUserId(100);
        chore.setDescription("Vask badet");
        chore.setHouseId(1);
        chore.setUser(user);
        chore.setDone(true); //endrer fra false til done
        chore.setDate("2018/01/20");

        ChoreDAO.editChore(chore);

        ResultSet rs2 = st.executeQuery(query);

        if(rs2.next()) {
            assertEquals(1, rs2.getInt("done"));
            assertEquals("Vask badet", rs2.getString("description"));
        }

        rs2.close();

    }
}