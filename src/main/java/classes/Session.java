package classes;

public class Session {

    private String tokenID;
    private User loggedInUser;
    private long lastActivityTimestamp;

    public Session(String tokenID, User loggedInUser, long lastActivityTimestamp) {
        this.tokenID = tokenID;
        this.loggedInUser = loggedInUser;
        this.lastActivityTimestamp = lastActivityTimestamp;
    }

    public String getTokenID() {
        return tokenID;
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
