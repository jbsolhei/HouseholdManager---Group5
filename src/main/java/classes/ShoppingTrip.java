package classes;

import java.time.*;
import java.util.List;

import static jdk.nashorn.internal.objects.NativeMath.round;

/**
 * Created by Camilla Velvin on 12.01.2018.
 */
public class ShoppingTrip {
    private int shoppingTripId;
    private String name;
    private double expence;
    private String comment;
    private int userId;
    private String userName;
    private LocalDate shoppingDate;
    private List<User> contributors;
    private int houseId;
    private int shopping_listId;
    private String shopping_listName;
    private double expencePerPerson;

    /**
     * <p>Constructor for ShoppingTrip.</p>
     */
    public ShoppingTrip() {
    }

    /**
     * <p>Getter for the field <code>shoppingTripId</code>.</p>
     *
     * @return a int.
     */
    public int getShoppingTripId() {
        return shoppingTripId;
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
     * <p>Setter for the field <code>name</code>.</p>
     *
     * @param name a {@link java.lang.String} object.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>Getter for the field <code>shoppingDate</code>.</p>
     *
     * @return a {@link java.time.LocalDate} object.
     */
    public LocalDate getShoppingDate() {
        return shoppingDate;
    }

    /**
     * <p>Setter for the field <code>shoppingDate</code>.</p>
     *
     * @param shoppingDate a {@link java.time.LocalDate} object.
     */
    public void setShoppingDate(LocalDate shoppingDate) {
        this.shoppingDate = shoppingDate;
    }

    /**
     * <p>Setter for the field <code>shoppingTripId</code>.</p>
     *
     * @param shoppingTripId a int.
     */
    public void setShoppingTripId(int shoppingTripId) {
        this.shoppingTripId = shoppingTripId;
    }

    /**
     * <p>Getter for the field <code>expence</code>.</p>
     *
     * @return a double.
     */
    public double getExpence() {
        return expence;
    }

    /**
     * <p>Setter for the field <code>expence</code>.</p>
     *
     * @param expence a double.
     */
    public void setExpence(double expence) {
        this.expence = expence;
    }

    /**
     * <p>Getter for the field <code>comment</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getComment() {
        return comment;
    }

    /**
     * <p>Setter for the field <code>comment</code>.</p>
     *
     * @param comment a {@link java.lang.String} object.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * <p>Getter for the field <code>userId</code>.</p>
     *
     * @return a int.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * <p>Setter for the field <code>userId</code>.</p>
     *
     * @param userId a int.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * <p>Getter for the field <code>contributors</code>.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<User> getContributors() {
        return contributors;
    }

    /**
     * <p>Setter for the field <code>contributors</code>.</p>
     *
     * @param contributors a {@link java.util.List} object.
     */
    public void setContributors(List<User> contributors) {
        this.contributors = contributors;
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

    /**
     * <p>Getter for the field <code>shopping_listId</code>.</p>
     *
     * @return a int.
     */
    public int getShopping_listId() {
        return shopping_listId;
    }

    /**
     * <p>Setter for the field <code>shopping_listId</code>.</p>
     *
     * @param shopping_listId a int.
     */
    public void setShopping_listId(int shopping_listId) {
        this.shopping_listId = shopping_listId;
    }

    /**
     * <p>Getter for the field <code>userName</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * <p>Setter for the field <code>userName</code>.</p>
     *
     * @param userName a {@link java.lang.String} object.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * <p>Getter for the field <code>shopping_listName</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getShopping_listName() {
        return shopping_listName;
    }

    /**
     * <p>Setter for the field <code>shopping_listName</code>.</p>
     *
     * @param shopping_listName a {@link java.lang.String} object.
     */
    public void setShopping_listName(String shopping_listName) {
        this.shopping_listName = shopping_listName;
    }

    public void setExpencePerPerson() {
        this.expencePerPerson = Math.round(expence / contributors.size());
    }

    public double getExpencePerPerson() {
        return expencePerPerson;
    }
}
