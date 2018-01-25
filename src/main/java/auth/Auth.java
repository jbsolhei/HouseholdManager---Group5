package auth;

import javax.ws.rs.NameBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@NameBinding
/**
 * <p>Auth class.</p>
 *
 */
@Retention(RUNTIME)
@Target({METHOD, TYPE})
public @interface Auth {
    AuthType value() default AuthType.DEFAULT;
}
