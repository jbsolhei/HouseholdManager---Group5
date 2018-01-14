package auth;

import javax.annotation.Priority;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Auth
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    /*
    @Inject
    @AuthenticatedUser
    Event<Session> userAuthenticatedEvent;
    */

    //@Produces
    //@RequestScoped
    @Inject
    @AuthenticatedUser
    private AuthenticatedUserData authenticatedUser;

    @Override
    public void filter(ContainerRequestContext context) throws IOException {
        String authHeader = context.getHeaderString(HttpHeaders.AUTHORIZATION);

        String providedToken = retrieveTokenFromHeader(authHeader);
        Session session = Sessions.getSession(providedToken);

        if (session == null) {
            unauthenticated(context);
        }
        else {
            //userAuthenticatedEvent.fire(session);

            //authenticatedUser = new AuthenticatedUserData();
            authenticatedUser.setUserId(session.getUserId());
            authenticatedUser.setSessionToken(session.getToken());
        }
    }

    public static String retrieveTokenFromHeader(String authHeader) {
        if (authHeader == null || !authHeader.toLowerCase().trim().startsWith("bearer")) {
            return null;
        }

        return authHeader.substring(6).trim();
    }

    private void unauthenticated(ContainerRequestContext context) {
        context.abortWith(
            Response.status(Response.Status.UNAUTHORIZED)
                    .header(HttpHeaders.WWW_AUTHENTICATE, "Bearer realm=\"HouseholdManager\"")
                    .build()
        );
    }
}
