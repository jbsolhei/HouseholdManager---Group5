package database;

import classes.Debt;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

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
}