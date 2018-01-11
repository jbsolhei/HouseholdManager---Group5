package classes;

import java.io.Serializable;

public class LoginAttemptResponse implements Serializable {
    private boolean success;
    private String sessionToken;

    public LoginAttemptResponse() {

    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
}
