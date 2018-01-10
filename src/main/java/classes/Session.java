package classes;

public class Session {

    private String token;
    private int loggedInUserId;
    private long lastActivityTimestamp;

    public Session(String token, int loggedInUserId, long lastActivityTimestamp) {
        this.token = token;
        this.loggedInUserId = loggedInUserId;
        this.lastActivityTimestamp = lastActivityTimestamp;
    }

    public String getToken() {
        return token;
    }

    public int getLoggedInUserId() {
        return loggedInUserId;
    }

    public long getLastActivityTimestamp() {
        return lastActivityTimestamp;
    }

    public void setLastActivityTimestamp(long lastActivityTimestamp) {
        this.lastActivityTimestamp = lastActivityTimestamp;
    }
}
