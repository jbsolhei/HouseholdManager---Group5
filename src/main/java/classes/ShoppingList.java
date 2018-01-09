package classes;


public class ShoppingList {
    private String name;
    private User[] participants;
    private boolean isPrivate;
    private Item[] items;

    public ShoppingList(){}

    public String getName() {
        return name;
    }

    public User[] getParticipants() {
        return participants;
    }

    public Item[] getItems() {
        return items;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParticipants(User[] participants) {
        this.participants = participants;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }
}
