package classes;


public class ShoppingList {
    private int shoppingListId;
    private String name;
    private User[] users;
    private Item[] items;
    private boolean isArchived;
    private boolean isPrivate;

    public ShoppingList(){}

    public int getShoppingListId() {
        return shoppingListId;
    }

    public String getName() {
        return name;
    }

    public User[] getUsers() {
        return users;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setShoppingListId(int shoppingListId) {
        this.shoppingListId = shoppingListId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }
}
