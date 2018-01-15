package auth;

import javax.annotation.Priority;
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
    /*
    @Inject
    @AuthenticatedUser
    private AuthenticatedUserData authenticatedUser;
    */

    @Override
    public void filter(ContainerRequestContext context) throws IOException {
        String authHeader = context.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.toLowerCase().trim().startsWith("bearer")) {
            unauthenticated(context);
        }
        else {
            String providedToken = authHeader.substring(6).trim();
            Session session = Sessions.getSession(providedToken);

            if (session == null) {
                unauthenticated(context);
            }
            else {
                /*
                authenticatedUser = new AuthenticatedUserData();
                authenticatedUser.setUserId(session.getUserId());
                authenticatedUser.setSessionToken(session.getToken());
                System.out.println("AuthenticationFilter wrote to authenticatedUser object! Id: " + authenticatedUser.getUserId());
                */

                context.setProperty("session.token", session.getToken());
                context.setProperty("session.userId", session.getUserId());
            }
        }
    }

    private void unauthenticated(ContainerRequestContext context) {
        context.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .header(HttpHeaders.WWW_AUTHENTICATE, "Bearer realm=\"HouseholdManager\"")
                        .build()
        );
    }
}