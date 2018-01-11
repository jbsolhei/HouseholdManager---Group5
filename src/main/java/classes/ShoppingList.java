package classes;


public class ShoppingList {
    private int shoppingListId;
    private String name;
    private User[] participants;
    private boolean isPrivate;

    public ShoppingList(){}

    public int getShoppingListId() {
        return shoppingListId;
    }

    public String getName() {
        return name;
    }

    public User[] getParticipants() {
        return participants;
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

    public void setParticipants(User[] participants) {
        this.participants = participants;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }
}
