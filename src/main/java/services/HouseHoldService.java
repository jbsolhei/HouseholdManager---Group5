package services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * @author team5
 */
@Path("household")
public class HouseHoldService {

    @GET
    @Produces("text/plain")
    public String getTest(){
        return "Household service says hello!";
    }
}
