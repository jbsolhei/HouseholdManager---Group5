package classes;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SessionsTest {

    private User alice;
    private User bob;
    private Session aliceSession;
    private Session bobSession;

    @Before
    public void setup() {
        alice = new User();
        alice.setName("Alice");

        bob = new User();
        bob.setName("Bob");

        aliceSession = Sessions.generateSession(alice);
        bobSession = Sessions.generateSession(bob);
    }

    @Test
    public void getSession() throws Exception {
        Session session;

        // Existing session
        session = Sessions.getSession(aliceSession.getToken());
        assertEquals(aliceSession, session);

        // Nonexisting session
        session = Sessions.getSession("nope");
        assertNull(session);

        // Timed out session
        bobSession.setLastActivityTimestamp(0);
        session = Sessions.getSession(bobSession.getToken());
        assertNull(session);
    }

    @Test
    public void generateSession() throws Exception {
        Session newAliceSession = Sessions.generateSession(alice);
        String token = newAliceSession.getToken();
        assertEquals(newAliceSession, Sessions.getSession(token));
        assertNotEquals(aliceSession, Sessions.getSession(token));
    }

    @Test
    public void invalidateSession() throws Exception {
        String aliceSessionToken = aliceSession.getToken();
        Sessions.invalidateSession(aliceSessionToken);
        assertNull(Sessions.getSession(aliceSessionToken));
        assertEquals(bobSession, Sessions.getSession(bobSession.getToken()));
    }

}