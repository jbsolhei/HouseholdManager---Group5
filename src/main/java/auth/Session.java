package auth;


public class Session {

    private String token;
    private int userId;
    private long lastActivityTimestamp;

    public Session(String token, int userId, long lastActivityTimestamp) {
        this.token = token;
        this.userId = userId;
        this.lastActivityTimestamp = lastActivityTimestamp;
    }

    /**
     * Returns the session token string.
     * @return the token string
     */
    public String getToken() {
        return token;
    }

    /**
     * Returns the ID of the user associated with this session.
     * @return the user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Returns the timestamp for this session's last activity, that is, when this
     * session was last retrieved by Sessions.getSession().
     * @return the timestamp
     */
    public long getLastActivityTimestamp() {
        return lastActivityTimestamp;
    }

    /**
     * Sets this session's last activity timestamp.
     * @param lastActivityTimestamp the timestamp
     */
    public void setLastActivityTimestamp(long lastActivityTimestamp) {
        this.lastActivityTimestamp = lastActivityTimestamp;
    }
}
