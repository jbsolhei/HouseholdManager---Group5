package classes;

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

    @Override
    public void filter(ContainerRequestContext context) throws IOException {
        String authHeader = context.getHeaderString(HttpHeaders.AUTHORIZATION);

        String providedToken = retrieveTokenFromHeader(authHeader);
        Session session = Sessions.getSession(providedToken);

        if (session == null) {
            unauthenticated(context);
        }

        // TODO Add authorization/privilege checks
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
