package classes;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;

public class Sessions {

    private static final int timeoutSecs = 1800; // 30 min
    private static HashMap<String, Session> sessions = new HashMap<>();

    private Sessions() {
    }

    /**
     * Returns a session by its ID token. Returns null if the session doesn't exist
     * or if it has timed out and has been removed.
     * @param token the ID token for the session
     * @return the Session object, or null if it doesn't exist or has timed out.
     */
    public static Session getSession(String token) {
        Session session = sessions.get(token);
        if (session == null) {
            return null;
        }
        else {
            long now = System.currentTimeMillis() / 1000;
            if (now - session.getLastActivityTimestamp() > timeoutSecs) {
                sessions.remove(token);
                return null;
            }
            session.setLastActivityTimestamp(now);
            return session;
        }
    }

    /**
     * Generates a new sesssion with a random token associated with a user.
     * Last activity timestamp is set to now.
     * @param user the user for this session
     * @return the Session object
     */
    public static Session generateSession(User user) {
        SecureRandom random = new SecureRandom();
        byte randomBytes[] = new byte[128];
        random.nextBytes(randomBytes);
        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
        Session session = new Session(token, user, System.currentTimeMillis() / 1000);
        sessions.put(token, session);
        return session;
    }

    /**
     * Invalidates (deletes) a session.
     * @param token the token for the session to invalidate
     */
    public static void invalidateSession(String token) {
        sessions.remove(token);
    }
}
