package database;

import classes.News;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class NewsDAOTest {
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
    public void getNews() throws Exception {
        ArrayList<News> result = NewsDAO.getNews(1);
        assertEquals(1,result.size());
    }

    @Test
    public void postNews() throws Exception {
        News news = new News();
        news.setMessage("New message");
        news.setUserId(50);
        news.setHouseId(10);

        int result = NewsDAO.postNews(news);

        String query = "SELECT * FROM Message WHERE houseId=10";
        ResultSet rs = st.executeQuery(query);
        int count=0;
        while (rs.next()){
            count++;
        }

        assertEquals(1,result);
        assertEquals(2,count);
    }

    @Test
    public void deleteNews() throws Exception {
        NewsDAO.deleteNews(4);
        String query = "SELECT * FROM Message WHERE messageId=4";
        ResultSet rs = st.executeQuery(query);
        assertFalse(rs.next());
    }

}