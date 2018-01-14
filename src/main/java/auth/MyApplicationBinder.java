package auth;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class MyApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(AuthenticatedUserData.class).to(AuthenticatedUserData.class);
    }
}
