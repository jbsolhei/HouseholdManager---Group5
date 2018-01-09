package services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HouseHoldServiceTest {
    private HouseHoldService hs;

    @Before
    public void setUp() throws Exception {
        hs = new HouseHoldService();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getTestTest() throws Exception {
        assert hs.getTest().equals("Household service says hello!");
    }

}