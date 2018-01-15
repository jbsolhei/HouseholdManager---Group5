package auth;

import org.glassfish.jersey.process.internal.RequestScoped;

@RequestScoped
@AuthenticatedUser
public class AuthenticatedUserData {

    private int userId;
    private String sessionToken;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
}


