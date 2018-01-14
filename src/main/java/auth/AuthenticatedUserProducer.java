package auth;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;

@RequestScoped
public class AuthenticatedUserProducer {

    /*@Produces
    @RequestScoped
    @AuthenticatedUser
    private Session userSession;

    public void handleAuthenticationEvent(@Observes @AuthenticatedUser Session userSession) {
        this.userSession = userSession;
    }*/
}
