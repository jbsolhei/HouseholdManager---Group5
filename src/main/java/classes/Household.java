package classes;

import java.util.ArrayList;

public class Household {
    private String name;
    private String address;
    private User[] residents;
    private User[] admins;
    private ShoppingList[] shoppingLists;
    private ArrayList<News> news;
    private Chore[] chores;

    private int houseId;

    public Household(){}

    public ArrayList<News> getNews() {
        return news;
    }

    public void setNews(ArrayList<News> news) {
        this.news = news;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public User[] getResidents() {
        return residents;
    }

    public ShoppingList[] getShoppingLists() {
        return shoppingLists;
    }

    public Chore[] getChores() {
        return chores;
    }

    public User[] getAdmins() {
        return admins;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setResidents(User[] residents) {
        this.residents = residents;
    }

    public void setAdmins(User[] admins) {
        this.admins = admins;
    }

    public void setShoppingLists(ShoppingList[] shoppingLists) {
        this.shoppingLists = shoppingLists;
    }

    public void setChores(Chore[] choreList) {
        this.chores = choreList;
    }

    public int getHouseId() {
        return houseId;
    }

    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }
}
