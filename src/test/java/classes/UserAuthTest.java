package classes;

import auth.UserAuth;
import database.DAOTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserAuthTest {

    @Before
    public void setUp() throws Exception {
        DAOTest.setUp();
    }

    @After
    public void tearDown() throws Exception {
        DAOTest.tearDown();
    }

    @Test
    public void authenticateLogin() {
        // Correct email/password, should return a Session
        assertNotNull(UserAuth.authenticateLogin("test@testesen.no", "test123"));

        // Incorrect password, should return null
        assertNull(UserAuth.authenticateLogin("test@testesen.no", "feilpassord"));
    }
}