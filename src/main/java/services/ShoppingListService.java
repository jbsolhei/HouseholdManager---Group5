package services;

import auth.Auth;
import auth.AuthType;
import classes.Item;
import classes.ShoppingList;
import classes.User;
import database.ShoppingListDAO;
import database.UserDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author team5
 */

@Path("household/{id}/shopping_lists")
public class ShoppingListService {

    /**
     * Produces all shopping lists from a given household
     * If the user is an admin the method will return all available shopping lists associated with the household
     * If the user is not admin the method only returns shopping lists associated with the household and the user
     *
     * @param id the house ID
     * @param userId the user ID
     * @return an array of shopping list
     */
    @GET
    @Auth(AuthType.HOUSEHOLD)
    @Path("/user/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ShoppingList[] getShoppingLists(@PathParam("id") String id, @PathParam("userId") String userId) {
        int house_id = Integer.parseInt(id);
        int user_id = Integer.parseInt(userId);
        if (UserDAO.isAdmin(house_id, user_id)) return ShoppingListDAO.getShoppingListsAdmin(house_id);
        return ShoppingListDAO.getShoppingListsUser(house_id, user_id);
    }

    /**
     * Gets a specific shopping list given its shopping list ID
     *
     * @param shoppingListId the shopping list ID
     * @return a ShoppingList object
     */
    @GET
    @Auth
    @Path("/{shopping_list_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ShoppingList getShoppingList(@PathParam("shopping_list_id") String shoppingListId) {
        return ShoppingListDAO.getShoppingList(Integer.parseInt(shoppingListId));
    }

    /**
     * Produces all users associated with a shopping list
     *
     * @param id the house ID
     * @param shoppingListId the shopping list ID
     * @return an array of users
     */
    @GET
    @Auth(AuthType.HOUSEHOLD)
    @Path("/{shopping_list_id}/users")
    @Produces(MediaType.APPLICATION_JSON)
    public User[] getShoppingListUsers(@PathParam("id") String id, @PathParam("shopping_list_id") String shoppingListId) {
        return ShoppingListDAO.getUsersInShoppingList(Integer.parseInt(shoppingListId));
    }

    @GET
    @Auth(AuthType.HOUSEHOLD)
    @Path("/{shopping_list_id}/items")
    @Produces(MediaType.APPLICATION_JSON)
    public Item[] getItems(@PathParam("id") String id, @PathParam("shopping_list_id") String shopping_list_id){
        return ShoppingListDAO.getItems(Integer.parseInt(shopping_list_id));
    }

    @POST
    @Auth(AuthType.HOUSEHOLD)
    @Consumes(MediaType.TEXT_PLAIN)
    public int createShoppingList(@PathParam("id") int houseId, String shoppingListName){
        return ShoppingListDAO.createShoppingList(shoppingListName, houseId);
    }

    /**
     * Deletes a shopping list given its shopping list ID
     *
     * @param houseId the hose ID
     * @param shopping_list_id the shopping list ID
     */
    @DELETE
    @Auth(AuthType.HOUSEHOLD)
    @Path("/{shopping_list_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteShoppingList(@PathParam("id") int houseId, @PathParam("shopping_list_id") int shopping_list_id){
        ShoppingListDAO.deleteShoppingList(houseId, shopping_list_id);
    }

    @POST
    @Auth(AuthType.HOUSEHOLD)
    @Path("/{shopping_list_id}/items")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addItems(@PathParam("shopping_list_id") int shopping_list_id, Item item){
        ShoppingListDAO.addItem(item, shopping_list_id);
    }

    @DELETE
    @Auth(AuthType.HOUSEHOLD)
    @Path("/{shopping_list_id}/items/{itemId}")
    public void deleteItem(@PathParam("shopping_list_id") int shopping_list_id, @PathParam("itemId") int itemId){
        ShoppingListDAO.deleteItem(shopping_list_id, itemId);
    }

    /**
     * Udates users associated with a shopping list
     *
     * @param houseId the house ID
     * @param shopping_list_id the shopping list ID
     * @param userIds an array of user IDs
     */
    @POST
    @Auth(AuthType.HOUSEHOLD)
    @Path("/{shopping_list_id}/users")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateUsers(@PathParam("id") int houseId, @PathParam("shopping_list_id") int shopping_list_id, String[] userIds) {
        for (String u : userIds) {
            System.out.println(u);
        }
        ShoppingListDAO.updateUsers(userIds, shopping_list_id);
    }

    /**
     * Updates 'checkedBy' of an Item given its itemId and userId
     * Sets checkedBy = null if user id = 0
     *
     * @param itemId the item ID
     * @param userId the user ID
     * @return true if the database was updated, false if an error occurred
     */
    @POST
    @Auth(AuthType.HOUSEHOLD)
    @Path("/items/{itemId}/user/")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean updateCheckedBy(@PathParam("itemId") int itemId , int userId) {
        int rs = ShoppingListDAO.updateCheckedBy(userId, itemId);
        System.out.println(userId + " " + itemId);
        return rs >= 0;
    }

    /**
     * Updates a shopping lists 'archived' column in the database
     *
     * @param shoppingListId the shopping list ID
     * @param archived the wanted value of the column
     */
    @PUT
    @Auth(AuthType.HOUSEHOLD)
    @Path("/{shopping_list_id}")
    @Consumes(MediaType.TEXT_PLAIN)
    public void updateArchived(@PathParam("shopping_list_id") int shoppingListId, String archived) {
        ShoppingListDAO.updateArchived(shoppingListId,Boolean.parseBoolean(archived));
    }

    /**
     * Deletes a row in User_Shopping_list
     *
     * @param shoppingListId the shopping list ID
     * @param userId the user ID
     * @return false if an error occurred, 1 if no errors occurred
     */
    @DELETE
    @Auth(AuthType.HOUSEHOLD)
    @Path("/{shopping_list_id}/user")
    public boolean deleteUserInShoppingList(@PathParam("shopping_list_id") int shoppingListId, int userId) {
        return (ShoppingListDAO.updateUserInShoppingList(shoppingListId, userId, true) != -1);
    }

    /**
     * Inserts a row in User_Shopping_list
     *
     * @param shoppingListId the shopping list ID
     * @param userId the user ID
     * @return false if an error occurred, 1 if no errors occurred
     */
    @POST
    @Auth(AuthType.HOUSEHOLD)
    @Path("/{shopping_list_id}/user")
    public boolean insertUserInShoppingList(@PathParam("shopping_list_id") int shoppingListId, int userId) {
        return (ShoppingListDAO.updateUserInShoppingList(shoppingListId, userId, false) != -1);
    }
}
