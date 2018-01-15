package services;

import classes.ShoppingTrip;
import database.ShoppingTripDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("shoppingtrip")
public class ShoppingTripService {

    @GET
    @Path("/{id}")
    @Produces (MediaType.APPLICATION_JSON)
    public List<ShoppingTrip> getShoppingTrips(@PathParam("id") int houseId) {
        return ShoppingTripDAO.getShoppingTrips(houseId);
    }

    @POST
    @Consumes (MediaType.APPLICATION_JSON)
    public boolean createShoppingTrip(ShoppingTrip shoppingTrip) {
        return ShoppingTripDAO.createShoppingTrip(shoppingTrip);
    }

    @GET
    @Path("/{id}/trip")
    @Produces(MediaType.APPLICATION_JSON)
    public ShoppingTrip getShoppingTrip(@PathParam("id") int shoppingTripid) {
        return ShoppingTripDAO.getShoppingTrip(shoppingTripid);
    }
}
