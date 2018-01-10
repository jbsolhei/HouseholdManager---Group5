package classes;

import java.util.HashMap;

public class Sessions {

    private static final int timeoutSecs = 1800; // 30 min
    private static HashMap<String, Session> sessions;

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
}
