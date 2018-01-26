package classes;

import org.junit.Test;

import static org.junit.Assert.*;

public class HashHandlerTest {

    @Test
    public void makeHashFromPassword() {
        String hashedPassword = HashHandler.makeHashFromPassword("test123");

        // It's hard to test hashing as it's supposed to output random-looking data
        assertNotNull(hashedPassword);
        assertNotEquals(hashedPassword, "");
    }

    @Test
    public void passwordMatchesHash() {
        String hashedPassword = HashHandler.makeHashFromPassword("test123");

        assertTrue(HashHandler.passwordMatchesHash("test123", hashedPassword));
        assertFalse(HashHandler.passwordMatchesHash("321feil", hashedPassword));
    }
}