package database;

import classes.Chore;
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
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

        try {
            java.util.Date utilDate = format.parse("2018/01/20");
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            System.out.println(sqlDate);

            user.setUserId(100);
            chore.setDescription("Ta ut av oppvaskmaskinen");
            chore.setHouseId(1);
            chore.setUser(user);
            chore.setDone(false);
            chore.setDate(sqlDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        ChoreDAO.postChore(chore);

        String query = "SELECT * FROM Chore WHERE houseId = 1";

        ResultSet rs = st.executeQuery(query);

        if(rs.next()) {
            assertEquals(1, rs.getInt("choreId"));
            assertEquals(chore.getDescription(), rs.getString("description"));
            assertEquals(chore.getUser().getUserId(), rs.getInt("userId"));
            assertEquals(chore.getHouseId(), rs.getInt("houseId"));
            assertEquals(0, rs.getInt("done"));
        }
        else{
            assert false;
        }
    }

    @Test
    public void getChores() {
    }

    @Test
    public void deleteChore() {
    }

    @Test
    public void editChore() {
    }
}