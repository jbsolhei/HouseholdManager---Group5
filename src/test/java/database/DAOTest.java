package database;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UserDAOTest.class,
        HouseholdDAOTest.class,
        ShoppingListDAOTest.class,
        ShoppingTripDAOTest.class,
        NewsDAOTest.class
})

public class DAOTest {

    static Connection conn;

    @BeforeClass
    public static void setUp() throws Exception {
        DBConnector.DB_URL = "jdbc:h2:mem:test";
        DBConnector.USE_CONNECTION_POOLING = false;
        DriverManager.getConnection("jdbc:h2:mem:test;INIT=RUNSCRIPT FROM 'classpath:scripts/databasescript.sql'\\;RUNSCRIPT FROM 'classpath:scripts/testInserts.sql';DB_CLOSE_DELAY=-1;");
    }

    @AfterClass
    public static void tearDown() throws Exception {
        DBConnector dbc = new DBConnector();
        conn = dbc.getConn();

        String query = "SHUTDOWN";
        Statement st = conn.createStatement();
        st.executeUpdate(query);
        st.close();
        conn.close();
        dbc.close();
    }
}