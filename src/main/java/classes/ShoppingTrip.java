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

    public ShoppingTrip() {
    }

    public int getShoppingTripId() {
        return shoppingTripId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getShoppingDate() {
        return shoppingDate;
    }

    public void setShoppingDate(LocalDate shoppingDate) {
        this.shoppingDate = shoppingDate;
    }

    public void setShoppingTripId(int shoppingTripId) {
        this.shoppingTripId = shoppingTripId;
    }

    public double getExpence() {
        return expence;
    }

    public void setExpence(double expence) {
        this.expence = expence;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<User> getContributors() {
        return contributors;
    }

    public void setContributors(List<User> contributors) {
        this.contributors = contributors;
    }

    public int getHouseId() {
        return houseId;
    }

    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }

    public int getShopping_listId() {
        return shopping_listId;
    }

    public void setShopping_listId(int shopping_listId) {
        this.shopping_listId = shopping_listId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getShopping_listName() {
        return shopping_listName;
    }

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
