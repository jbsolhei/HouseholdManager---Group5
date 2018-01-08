package classes;

import java.util.ArrayList;

public class Household {
    private String name;
    private User[] residents;

    public Household(String name, User[] residents){
        this.name = name;
        this.residents = residents;
    }

    public static void main(String[] args) {
        User bernt = new User("Bernt");
        User gunnar = new User("Gunnar");
        User leif = new User("Leif");

        bernt.addDebt(gunnar, 100);
        bernt.addDebt(gunnar, 100);
        for (Debt debt : bernt.getDebts()) {
            System.out.println(debt.getSum());
        }
    }

    public String getName() {
        return name;
    }

    public User[] getResidents() {
        return residents;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setResidents(User[] residents) {
        this.residents = residents;
    }
}
