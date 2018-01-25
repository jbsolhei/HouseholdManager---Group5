package services;

import auth.Auth;
import auth.AuthType;
import classes.ShoppingTrip;
import database.FinanceDAO;
import database.ShoppingTripDAO;
//import org.apache.commons.lang3.StringEscapeUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>ShoppingTripService class.</p>
 *
 */
@Path("shoppingtrip")
public class ShoppingTripService {

    /**
     * <p>getShoppingTrips.</p>
     *
     * @param houseId a int.
     * @return a {@link java.util.List} object.
     */
    @GET
    @Auth(AuthType.HOUSEHOLD)
    @Path("/{id}")
    @Produces (MediaType.APPLICATION_JSON)
    public List<ShoppingTrip> getShoppingTrips(@PathParam("id") int houseId) {
        return ShoppingTripDAO.getShoppingTrips(houseId);
    }

    /**
     * <p>createShoppingTrip.</p>
     *
     * @param shoppingTrip a {@link classes.ShoppingTrip} object.
     * @return a boolean.
     */
    @POST
    @Auth
    @Consumes (MediaType.APPLICATION_JSON)
    public boolean createShoppingTrip(ShoppingTrip shoppingTrip) {
        /*shoppingTrip.setName(StringEscapeUtils.escapeHtml4(shoppingTrip.getName()));
        shoppingTrip.setComment(StringEscapeUtils.escapeHtml4(shoppingTrip.getComment()));*/
        LocalDate date = LocalDate.now();
        shoppingTrip.setShoppingDate(date);
        FinanceDAO.updateFinance(shoppingTrip.getUserId(), shoppingTrip.getExpence(), shoppingTrip.getContributors());
        return ShoppingTripDAO.createShoppingTrip(shoppingTrip);
    }

    /**
     * <p>getShoppingTrip.</p>
     *
     * @param shoppingTripid a int.
     * @return a {@link classes.ShoppingTrip} object.
     */
    @GET
    @Auth
    @Path("/{id}/trip")
    @Produces(MediaType.APPLICATION_JSON)
    public ShoppingTrip getShoppingTrip(@PathParam("id") int shoppingTripid) {
        return ShoppingTripDAO.getShoppingTrip(shoppingTripid);
    }

    /**
     * <p>deleteShoppingTrip.</p>
     *
     * @param shoppingTripId a int.
     */
    @DELETE
    @Auth
    @Path("/{id}")
    public void deleteShoppingTrip(@PathParam("id") int shoppingTripId){
        ShoppingTripDAO.deleteShoppingTrip(shoppingTripId);
    }
}
