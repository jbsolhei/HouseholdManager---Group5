package classes;

import java.util.ArrayList;

/**
 * <p>Household class.</p>
 *
 */
public class Household {
    private String name;
    private String address;
    private User[] residents;
    private User[] admins;
    private ShoppingList[] shoppingLists;
    private ArrayList<News> news;
    private Chore[] chores;

    private int houseId;

    /**
     * <p>Constructor for Household.</p>
     */
    public Household(){}

    /**
     * <p>Getter for the field <code>news</code>.</p>
     *
     * @return a {@link java.util.ArrayList} object.
     */
    public ArrayList<News> getNews() {
        return news;
    }

    /**
     * <p>Setter for the field <code>news</code>.</p>
     *
     * @param news a {@link java.util.ArrayList} object.
     */
    public void setNews(ArrayList<News> news) {
        this.news = news;
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
     * <p>Getter for the field <code>address</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getAddress() {
        return address;
    }

    /**
     * <p>Getter for the field <code>residents</code>.</p>
     *
     * @return an array of {@link classes.User} objects.
     */
    public User[] getResidents() {
        return residents;
    }

    /**
     * <p>Getter for the field <code>shoppingLists</code>.</p>
     *
     * @return an array of {@link classes.ShoppingList} objects.
     */
    public ShoppingList[] getShoppingLists() {
        return shoppingLists;
    }

    /**
     * <p>Getter for the field <code>chores</code>.</p>
     *
     * @return an array of {@link classes.Chore} objects.
     */
    public Chore[] getChores() {
        return chores;
    }

    /**
     * <p>Getter for the field <code>admins</code>.</p>
     *
     * @return an array of {@link classes.User} objects.
     */
    public User[] getAdmins() {
        return admins;
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
     * <p>Setter for the field <code>address</code>.</p>
     *
     * @param address a {@link java.lang.String} object.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * <p>Setter for the field <code>residents</code>.</p>
     *
     * @param residents an array of {@link classes.User} objects.
     */
    public void setResidents(User[] residents) {
        this.residents = residents;
    }

    /**
     * <p>Setter for the field <code>admins</code>.</p>
     *
     * @param admins an array of {@link classes.User} objects.
     */
    public void setAdmins(User[] admins) {
        this.admins = admins;
    }

    /**
     * <p>Setter for the field <code>shoppingLists</code>.</p>
     *
     * @param shoppingLists an array of {@link classes.ShoppingList} objects.
     */
    public void setShoppingLists(ShoppingList[] shoppingLists) {
        this.shoppingLists = shoppingLists;
    }

    /**
     * <p>Setter for the field <code>chores</code>.</p>
     *
     * @param choreList an array of {@link classes.Chore} objects.
     */
    public void setChores(Chore[] choreList) {
        this.chores = choreList;
    }

    /**
     * <p>Getter for the field <code>houseId</code>.</p>
     *
     * @return a int.
     */
    public int getHouseId() {
        return houseId;
    }

    /**
     * <p>Setter for the field <code>houseId</code>.</p>
     *
     * @param houseId a int.
     */
    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }
}
