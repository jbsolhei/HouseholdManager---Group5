package classes;

import java.util.ArrayList;

public class Household {
    private String name;
    private String address;
    private User[] residents;
    private User[] admins;
    private ShoppingList shoppingList;
    private TodoList todoList;

    private int houseId;

    public Household(){}

    public String getName() {
        return name;
    }

    public String getAdress() {
        return address;
    }

    public User[] getResidents() {
        return residents;
    }

    public ShoppingList getShoppingList() {
        return shoppingList;
    }

    public TodoList getTodoList() {
        return todoList;
    }

    public User[] getAdmins() {
        return admins;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAdress(String address) {
        this.address = address;
    }

    public void setResidents(User[] residents) {
        this.residents = residents;
    }

    public void setAdmins(User[] admins) {
        this.admins = admins;
    }

    public void setShoppingList(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
    }

    public void setTodoList(TodoList todoList) {
        this.todoList = todoList;
    }

    public int getHouseId() {
        return houseId;
    }

    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }
}
