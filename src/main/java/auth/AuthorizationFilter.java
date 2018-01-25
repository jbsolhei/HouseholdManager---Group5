package auth;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * <p>AuthorizationFilter class.</p>
 *
 */
@Auth
@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;

    /** {@inheritDoc} */
    @Override
    public void filter(ContainerRequestContext context) throws IOException {
        Method matchedEndpointMethod = resourceInfo.getResourceMethod();

        if (matchedEndpointMethod == null || matchedEndpointMethod.getAnnotation(Auth.class) == null) {
            context.abortWith(Response.serverError()
                    .entity("No matching endpoint method or missing @Auth when authorizing!")
                    .build());
        }
        else {
            AuthType authType = matchedEndpointMethod.getAnnotation(Auth.class).value();

            if (authType != AuthType.DEFAULT) {

                int userId = (Integer) context.getProperty("session.userId");
                MultivaluedMap<String, String> pathParams = context.getUriInfo().getPathParameters();

                int pathId;
                try {
                    pathId = Integer.parseInt(pathParams.getFirst("id"));
                }
                catch (NumberFormatException e) {
                    context.abortWith(Response.serverError()
                            .entity("Malformed path param ID: " + pathParams.getFirst("id"))
                            .build());
                    return;
                }

                switch (authType) {
                    case USER_READ:
                        if (!UserAuth.canUserReadUser(userId, pathId)) {
                            unauthorized(context);
                        }
                        return;

                    case USER_MODIFY:
                        if (userId != pathId) {
                            unauthorized(context);
                        }
                        return;

                    case CHORE:
                        if (!UserAuth.canUserAccessChore(userId, pathId)) {
                            unauthorized(context);
                        }
                        return;

                    case HOUSEHOLD:
                        if (!UserAuth.canUserAccessHousehold(userId, pathId)) {
                            unauthorized(context);
                        }
                        return;

                    case SHOPPING_LIST:
                        if (!UserAuth.canUserAccessShoppingList(userId, pathId)) {
                            unauthorized(context);
                        }
                        return;

                    case HOUSEHOLD_ADMIN:
                        if (!UserAuth.isUserHouseholdAdmin(userId, pathId)) {
                            unauthorized(context);
                        }
                        return;

                    case NOTIFICATION_DELETE:
                        if (!UserAuth.canUserDeleteNotification(userId, pathId)) {
                            unauthorized(context);
                        }
                        return;

                    default:
                        return;
                }
            }
        }
    }

    private void unauthorized(ContainerRequestContext context) {
        context.abortWith(Response.status(Response.Status.FORBIDDEN).build());
    }
}
