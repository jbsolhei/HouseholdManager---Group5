package classes;

import java.util.ArrayList;

public class Household {
    private String name;
    private String address;
    private User[] residents;
    private User[] admins;
    private ShoppingList[] shoppingLists;
    private ArrayList<News> news;
    private Todo[] todo;

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

    public Todo[] getTodoList() {
        return todo;
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

    public void setTodoList(Todo[] todoList) {
        this.todo = todoList;
    }

    public int getHouseId() {
        return houseId;
    }

    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }
}
