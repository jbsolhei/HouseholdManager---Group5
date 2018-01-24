package database;

import classes.Chore;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

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

        chore.setTitle("Ta ut av oppvaskmaskinen");
        chore.setDescription("Husk å være forsiktig med serviset fra oldemor!");
        chore.setHouseId(1);
        chore.setUserId(100);
        chore.setDone(false);
        chore.setTime("2018-02-01T08:30");

        ChoreDAO.postChore(chore);

        String query = "SELECT * FROM Chore WHERE title = 'Ta ut av oppvaskmaskinen'";

        ResultSet rs = st.executeQuery(query);

        if(rs.next()) {
            assertEquals(chore.getTitle(), rs.getString("title"));
            assertEquals(chore.getDescription(), rs.getString("description"));
            assertEquals(chore.getUserId(), rs.getInt("userId"));
            assertEquals(chore.getHouseId(), rs.getInt("houseId"));
            assertEquals(0, rs.getInt("done"));

            LocalDateTime dateTime = chore.getTime(); // your ldt
            java.sql.Date sqlDate = java.sql.Date.valueOf(dateTime.toLocalDate());

            assertEquals(sqlDate, rs.getDate("chore_datetime"));
        }
        else{
            assert false;
        }
    }

    @Test
    public void getChores() {

        ArrayList<Chore> chores = ChoreDAO.getChores(10);

        assertEquals(1, chores.size());
        assertEquals("Ta ut søpla", chores.get(0).getTitle());
        assertEquals("Husk at grønn pose betyr restavfall!", chores.get(0).getDescription());
        assertEquals(51, chores.get(0).getUserId());
        assertEquals(false, chores.get(0).isDone());

        LocalDateTime localDateTime = LocalDateTime.of(2018, Month.FEBRUARY, 15, 10, 30, 0);
        java.sql.Date sqlDate = java.sql.Date.valueOf(localDateTime.toLocalDate());
        Timestamp timestamp = Timestamp.valueOf(localDateTime);

        assertEquals(sqlDate, java.sql.Date.valueOf(chores.get(0).getTime().toLocalDate()));
        assertEquals(localDateTime,chores.get(0).getTime());

    }

    @Test
    public void deleteChore() {

    }

    @Test
    public void editChore() throws Exception{
        String query = "SELECT * FROM Chore WHERE choreId = 3;";

        ResultSet rs = st.executeQuery(query);

        if(rs.next()) {
            assertEquals(0, rs.getInt("done"));
            assertEquals("Ta ut av oppvaskmaskinen", rs.getString("title"));
        }
        rs.close();

        Chore chore = new Chore();

        chore.setTitle("Vask badet");
        chore.setHouseId(1);
        chore.setUserId(10);
        chore.setDone(true); //endrer fra false til done
        chore.setTime("2018-02-14T12:00");

        ChoreDAO.editChore(chore);

        ResultSet rs2 = st.executeQuery(query);

        if(rs2.next()) {
            assertEquals(1, rs2.getInt("done"));
            assertEquals("Vask badet", rs2.getString("title"));
        }

        rs2.close();

    }
}