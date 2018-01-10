package classes;

public class Session {

    private String token;
    private User loggedInUser;
    private long lastActivityTimestamp;

    public Session(String token, User loggedInUser, long lastActivityTimestamp) {
        this.token = token;
        this.loggedInUser = loggedInUser;
        this.lastActivityTimestamp = lastActivityTimestamp;
    }

    public String getToken() {
        return token;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public long getLastActivityTimestamp() {
        return lastActivityTimestamp;
    }

    public void setLastActivityTimestamp(long lastActivityTimestamp) {
        this.lastActivityTimestamp = lastActivityTimestamp;
    }
}
