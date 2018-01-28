package auth;

import javax.ws.rs.NameBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <p>Annotation used on REST-endpoints which requires authentication/authorization.</p>
 * <p>An optional value-parameter specifies what authorization type should be used.
 * The default is {@link AuthType#DEFAULT}.</p>
 *
 * @see AuthType
 */
@NameBinding
@Retention(RUNTIME)
@Target({METHOD, TYPE})
public @interface Auth {
    AuthType value() default AuthType.DEFAULT;
}
