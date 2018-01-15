package auth;

import database.DAOTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
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

    @Test
    public void canUserAccessTask() {
        // Test Testesen, "Vask badet"
        assertTrue(UserAuth.canUserAccessTask(50, 100));

        // Trym, "Vask badet"
        assertFalse(UserAuth.canUserAccessTask(1, 100));
    }

    @Test
    public void canUserAccessHousehold() {
        // Test Testesen, "Kollektivet"
        assertTrue(UserAuth.canUserAccessHousehold(50, 10));

        // Nonexistent user, "Kollektivet"
        assertFalse(UserAuth.canUserAccessHousehold(999, 10));
    }

    @Ignore
    @Test
    public void canUserAccessShoppingList() {
        assertTrue(UserAuth.canUserAccessShoppingList(50, 1));
        assertFalse(UserAuth.canUserAccessShoppingList(50, 2));
    }

    @Ignore
    @Test
    public void isUserHouseholdAdmin() {
        assertTrue(UserAuth.isUserHouseholdAdmin(50, 10));
        assertFalse(UserAuth.isUserHouseholdAdmin(51, 10));
    }

    @Test
    public void canUserReadUser() {
        assertTrue(UserAuth.canUserReadUser(50, 51));
        assertFalse(UserAuth.canUserReadUser(50, 1));
    }
}