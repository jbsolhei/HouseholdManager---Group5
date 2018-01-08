package services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.logging.Logger;

/**
 * @author team5
 */
@Path("user")
public class UserService {
    
    private static final Logger log = Logger.getLogger(UserService.class.getName());

    @GET
    @Produces("text/plain")
    public String getTest(){
        return log.toString();
    }
}
