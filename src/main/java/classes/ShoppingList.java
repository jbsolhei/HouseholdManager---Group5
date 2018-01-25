package classes;


/**
 * <p>ShoppingList class.</p>
 *
 */
public class ShoppingList {
    private int shoppingListId;
    private String name;
    private User[] users;
    private Item[] items;
    private boolean isArchived;
    private boolean isPrivate;

    /**
     * <p>Constructor for ShoppingList.</p>
     */
    public ShoppingList(){}

    /**
     * <p>Getter for the field <code>shoppingListId</code>.</p>
     *
     * @return a int.
     */
    public int getShoppingListId() {
        return shoppingListId;
    }

    /**
     * <p>Getter for the field <code>name</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>Getter for the field <code>users</code>.</p>
     *
     * @return an array of {@link classes.User} objects.
     */
    public User[] getUsers() {
        return users;
    }

    /**
     * <p>isPrivate.</p>
     *
     * @return a boolean.
     */
    public boolean isPrivate() {
        return isPrivate;
    }

    /**
     * <p>Setter for the field <code>shoppingListId</code>.</p>
     *
     * @param shoppingListId a int.
     */
    public void setShoppingListId(int shoppingListId) {
        this.shoppingListId = shoppingListId;
    }

    /**
     * <p>Setter for the field <code>name</code>.</p>
     *
     * @param name a {@link java.lang.String} object.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>Setter for the field <code>users</code>.</p>
     *
     * @param users an array of {@link classes.User} objects.
     */
    public void setUsers(User[] users) {
        this.users = users;
    }

    /**
     * <p>setPrivate.</p>
     *
     * @param aPrivate a boolean.
     */
    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    /**
     * <p>Getter for the field <code>items</code>.</p>
     *
     * @return an array of {@link classes.Item} objects.
     */
    public Item[] getItems() {
        return items;
    }

    /**
     * <p>Setter for the field <code>items</code>.</p>
     *
     * @param items an array of {@link classes.Item} objects.
     */
    public void setItems(Item[] items) {
        this.items = items;
    }

    /**
     * <p>isArchived.</p>
     *
     * @return a boolean.
     */
    public boolean isArchived() {
        return isArchived;
    }

    /**
     * <p>setArchived.</p>
     *
     * @param archived a boolean.
     */
    public void setArchived(boolean archived) {
        isArchived = archived;
    }
}
