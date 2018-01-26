package auth;

import database.DAOTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserAuthTest {

    @Before
    public void setUp() throws Exception {
        /*
         * Set up in-memory H2 test database.
         * This test file is not part of the DAO test suite so we'll
         * have to set up and tear down the database here manually
         */
        DAOTest.setUp();
    }

    @After
    public void tearDown() throws Exception {
        // Tear down H2 test database
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
    public void canUserAccessChore() {
        // Test Testesen, "Vask badet"
        assertTrue(UserAuth.canUserAccessChore(50, 100));

        // Trym, "Vask badet"
        assertFalse(UserAuth.canUserAccessChore(1, 100));
    }

    @Test
    public void canUserAccessHousehold() {
        // Test Testesen, "Kollektivet"
        assertTrue(UserAuth.canUserAccessHousehold(50, 10));

        // Nonexistent user, "Kollektivet"
        assertFalse(UserAuth.canUserAccessHousehold(999, 10));
    }

    @Test
    public void canUserAccessShoppingList() {
        assertTrue(UserAuth.canUserAccessShoppingList(50, 10));
        assertFalse(UserAuth.canUserAccessShoppingList(50, 11));
    }

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

    @Test
    public void canUserDeleteNotification() {
        assertTrue(UserAuth.canUserDeleteNotification(50, 1));
        assertFalse(UserAuth.canUserDeleteNotification(50, 2));
        assertTrue(UserAuth.canUserDeleteNotification(51, 2));
        assertFalse(UserAuth.canUserDeleteNotification(51, 1));
    }
}