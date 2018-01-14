package auth;

import javax.annotation.Priority;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Auth
@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationFilter implements ContainerRequestFilter {

    @Inject
    @AuthenticatedUser
    private AuthenticatedUserData authenticatedUser;

    @Override
    public void filter(ContainerRequestContext context) throws IOException {
        System.out.println("AuthorizationFilter reached! Authenticated user: " + authenticatedUser.getUserId());

        // MultivaluedMap<String, String> pathParams = context.getUriInfo().getPathParameters();
        // TODO Add authorization/privilege checks
    }
}
