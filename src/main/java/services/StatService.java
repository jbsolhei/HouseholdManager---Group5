package services;

import auth.Auth;
import auth.AuthType;
import classes.Stats;
import database.StatDAO;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

/**
 * <p>StatService class.</p>
 *
 */
@Path("household")
public class StatService {

    /**
     * <p>getTaskStats.</p>
     *
     * @param id a int.
     * @return a {@link java.util.ArrayList} object.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Auth(AuthType.HOUSEHOLD)
    @Path("/{id}/stats/tasks")
    public ArrayList<Stats> getTaskStats(@PathParam("id") int id) {
        int numberOfMonthBackInTime = 12;
        return StatDAO.getTaskStats(id, numberOfMonthBackInTime);
    }

    /**
     * <p>getExpenseStats.</p>
     *
     * @param id a int.
     * @return an array of double.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Auth(AuthType.HOUSEHOLD)
    @Path("/{id}/stats/expenses")
    public double[] getExpenseStats(@PathParam("id") int id) {
        int numberOfMonthBackInTime = 12;
        return StatDAO.getExpenseStats(id, numberOfMonthBackInTime);
    }
}
