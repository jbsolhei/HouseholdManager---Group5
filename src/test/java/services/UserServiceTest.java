package services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserServiceTest {
    private UserService us;

    @Before
    public void setUp() throws Exception {
        us = new UserService();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getTestTest() throws Exception {
        assert us.getTest().equals("User service says hello!");
    }

}