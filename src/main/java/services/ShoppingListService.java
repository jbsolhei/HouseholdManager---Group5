package services;

import classes.Item;
import classes.ShoppingList;
import classes.User;
import database.ShoppingListDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author team5
 */

@Path("household")
public class ShoppingListService {

    @GET
    @Path("/{id}/shopping_lists")
    @Produces(MediaType.APPLICATION_JSON)
    public ShoppingList[] getShoppingLists(@PathParam("id") String id) {
        return ShoppingListDAO.getShoppingLists(Integer.parseInt(id));
    }

    @GET
    @Path("/{id}/shopping_lists/{shopping_list_id}/items")
    @Produces(MediaType.APPLICATION_JSON)
    public Item[] getItems(@PathParam("id") String id, @PathParam("shopping_list_id") String shopping_list_id){
        return ShoppingListDAO.getItems(Integer.parseInt(shopping_list_id));
    }

    @POST
    @Path("/{id}/shopping_lists/")
    @Produces(MediaType.APPLICATION_JSON)
    public void createShoppingList(@PathParam("id") int houseId, ShoppingList shoppingList){
        ShoppingListDAO.createShoppingList(shoppingList, houseId);
    }
}
