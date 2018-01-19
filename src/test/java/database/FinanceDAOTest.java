package database;

import classes.Debt;
import classes.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class FinanceDAOTest {
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
    public void getDept() {
        //User 1 has a total dept of 50,- from user 4
        double totalDept = 0;
        ArrayList<Debt> dept = FinanceDAO.getDept(100);

        for(int i = 0; i < dept.size(); i++){
            totalDept += dept.get(i).getAmount();
        }

        assertEquals(1, dept.size());
        assertEquals(50, totalDept, 0.1);
    }

    @Test
    public void getIncome() {
        //User 1 has a total income of 250,-
        // 100,- to user 2, 150,- to user 3
        double totalIncome = 0;
        ArrayList<Debt> income = FinanceDAO.getIncome(100);

        for(int i = 0; i < income.size(); i++){
            totalIncome += income.get(i).getAmount();
        }

        assertEquals(2, income.size());
        assertEquals(250, totalIncome, 0.1);
    }

    @Test
    public void updateFinance() {
        List<User> users = new ArrayList<>();
        List<User> users1 = new ArrayList<>();
        User user1 = new User();
        user1.setUserId(13);
        User user2 = new User();
        user2.setUserId(14);
        User user3 = new User();
        user3.setUserId(12);
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users1.add(user2);
        FinanceDAO.updateFinance(12, 150, users);
        FinanceDAO.updateFinance(13, 10, users1);

        String query = "SELECT * FROM Finance";
        try(PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int fromPerson = rs.getInt("fromPerson");
                int toPerson = rs.getInt("toPerson");
                double value = rs.getDouble("value");
                if(fromPerson == 13 && toPerson == 12) {
                    assertEquals(25, value, 0.01);
                } else if (fromPerson == 12 && toPerson == 13) {
                    fail();
                } else if (fromPerson == 12 && toPerson == 14) {
                    fail();
                } else if (fromPerson == 14 && toPerson == 12) {
                    fail();
                } else if (fromPerson == 14 && toPerson == 13) {
                    assertEquals(60, value, 0.01);
                } else if (fromPerson == 12) {
                    fail();
                }
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}