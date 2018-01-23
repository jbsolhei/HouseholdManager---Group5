package services;

import auth.Auth;
import auth.AuthType;
import classes.ShoppingTrip;
import database.ShoppingTripDAO;
import org.apache.commons.lang3.StringEscapeUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.util.List;

@Path("shoppingtrip")
public class ShoppingTripService {

    @GET
    @Auth(AuthType.HOUSEHOLD)
    @Path("/{id}")
    @Produces (MediaType.APPLICATION_JSON)
    public List<ShoppingTrip> getShoppingTrips(@PathParam("id") int houseId) {
        return ShoppingTripDAO.getShoppingTrips(houseId);
    }

    @POST
    @Auth
    @Consumes (MediaType.APPLICATION_JSON)
    public boolean createShoppingTrip(ShoppingTrip shoppingTrip) {
        shoppingTrip.setName(StringEscapeUtils.escapeHtml4(shoppingTrip.getName()));
        shoppingTrip.setComment(StringEscapeUtils.escapeHtml4(shoppingTrip.getComment()));
        LocalDate date = LocalDate.now();
        shoppingTrip.setShoppingDate(date);
        return ShoppingTripDAO.createShoppingTrip(shoppingTrip);
    }

    @GET
    @Auth
    @Path("/{id}/trip")
    @Produces(MediaType.APPLICATION_JSON)
    public ShoppingTrip getShoppingTrip(@PathParam("id") int shoppingTripid) {
        return ShoppingTripDAO.getShoppingTrip(shoppingTripid);
    }

    @DELETE
    @Auth
    @Path("/{id}")
    public void deleteShoppingTrip(@PathParam("id") int shoppingTripId){
        ShoppingTripDAO.deleteShoppingTrip(shoppingTripId);
    }
}
