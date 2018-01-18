package services;

import auth.Auth;
import auth.AuthType;
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
    @Auth(AuthType.HOUSEHOLD)
    @Path("/{id}/shopping_lists")
    @Produces(MediaType.APPLICATION_JSON)
    public ShoppingList[] getShoppingLists(@PathParam("id") String id) {
        return ShoppingListDAO.getShoppingLists(Integer.parseInt(id));
    }

    @GET
    @Auth(AuthType.HOUSEHOLD)
    @Path("/{id}/shopping_lists/{shopping_list_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User[] getShoppingListUsers(@PathParam("id") String id, @PathParam("shopping_list_id") String shoppingListId) {
        return ShoppingListDAO.getShoppingListUsers(Integer.parseInt(shoppingListId));
    }

    @GET
    @Auth(AuthType.HOUSEHOLD)
    @Path("/{id}/shopping_lists/{shopping_list_id}/items")
    @Produces(MediaType.APPLICATION_JSON)
    public Item[] getItems(@PathParam("id") String id, @PathParam("shopping_list_id") String shopping_list_id){
        return ShoppingListDAO.getItems(Integer.parseInt(shopping_list_id));
    }

    @POST
    @Auth(AuthType.HOUSEHOLD)
    @Path("/{id}/shopping_lists/")
    @Consumes(MediaType.TEXT_PLAIN)
    public int createShoppingList(@PathParam("id") int houseId, String shoppingListName){
        return  ShoppingListDAO.createShoppingList(shoppingListName, houseId);
    }

    @DELETE
    @Auth(AuthType.HOUSEHOLD)
    @Path("/{id}/shopping_lists/{shopping_list_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteShoppingList(@PathParam("id") int houseId, @PathParam("shopping_list_id") int shopping_list_id){
        ShoppingListDAO.deleteShoppingList(houseId, shopping_list_id);
    }

    @POST
    @Auth(AuthType.HOUSEHOLD)
    @Path("/{id}/shopping_lists/{shopping_list_id}/items")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addItems(@PathParam("shopping_list_id") int shopping_list_id, Item item){
        ShoppingListDAO.addItem(item, shopping_list_id);
    }

    @DELETE
    @Auth(AuthType.HOUSEHOLD)
    @Path("/{id}/shopping_lists/{shopping_list_id}/items/{itemId}")
    public void deleteItem(@PathParam("shopping_list_id") int shopping_list_id, @PathParam("itemId") int itemId){
        ShoppingListDAO.deleteItem(shopping_list_id, itemId);
    }

    @POST
    @Auth(AuthType.HOUSEHOLD)
    @Path("/{id}/shopping_list/{shopping_list_id}/users")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateUsers(@PathParam("id") int houseId, @PathParam("shopping_list_id") int shopping_list_id, String[] userIds) {
        for (String u : userIds) {
            System.out.println(u);
        }
        ShoppingListDAO.updateUsers(userIds, shopping_list_id);
    }

    @POST
    @Auth(AuthType.HOUSEHOLD)
    @Path("{id}/shopping_lists/items/{itemId}/user/")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean updateCheckedBy(@PathParam("itemId") int itemId , int userId) {
        int rs = ShoppingListDAO.updateCheckedBy(userId, itemId);
        System.out.println(userId + " " + itemId);
        return rs >= 0;
    }
}
