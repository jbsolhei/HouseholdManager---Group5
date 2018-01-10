package classes;

import java.util.ArrayList;

public class User {
    private String name;
    private String email;
    private String phone;
    private String password;
    private ArrayList<Debt> debts;

    public User(){}


    public ArrayList<Debt> getDebts() {
        return debts;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {return password; }

    public void setDebts(ArrayList<Debt> debts) {
        this.debts = debts;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}