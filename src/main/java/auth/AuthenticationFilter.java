package auth;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * <p>AuthenticationFilter class.</p>
 *
 */
@Auth
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    /** {@inheritDoc} */
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
