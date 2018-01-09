package classes;

import java.util.HashMap;

public class Sessions {

    private static final int timeoutSecs = 60;
    private static HashMap<String, Session> sessions;

    private Sessions() {

    }

    public static Session getSession(String tokenID) {
        Session session = sessions.get(tokenID);
        if (session == null) {
            return null;
        }
        else {
            long now = System.currentTimeMillis() / 1000;

            session.setLastActivityTimestamp(1);
        }
        return null;
    }
}
