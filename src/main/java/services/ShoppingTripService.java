package services;

import classes.ShoppingTrip;
import database.ShoppingTripDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
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
        LocalDate date = LocalDate.now();
        shoppingTrip.setShoppingDate(date);
        return ShoppingTripDAO.createShoppingTrip(shoppingTrip);
    }

    @GET
    @Path("/{id}/trip")
    @Produces(MediaType.APPLICATION_JSON)
    public ShoppingTrip getShoppingTrip(@PathParam("id") int shoppingTripid) {
        return ShoppingTripDAO.getShoppingTrip(shoppingTripid);
    }

    @DELETE
    @Path("/{id}")
    public void deleteShoppingTrip(@PathParam("id") int shoppingTripId){
        ShoppingTripDAO.deleteShoppingTrip(shoppingTripId);
    }
}
