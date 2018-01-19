package classes;

import java.util.ArrayList;

public class User {
    private int userId;
    private String name;
    private String email;
    private String telephone;
    private String password;
    private ArrayList<Debt> debts;
    private ArrayList<Debt> income;
    private ArrayList<Household> associatedHouseholds;

    public User(){}

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    //Chore: addDebt og simplifyDebtForSingleUser er shit
    public void addDebt(User toUser, double sum){
        if(debts.size()==0)debts.add(new Debt(sum, toUser));
        for (Debt debt :debts) {
            if(debt.getToUser().equals(toUser)){
                debt.setAmount(sum);
                return;
            }
        }
        debts.add(new Debt(sum, toUser));
    }

    public void addIncome(User toUser, double sum){
        if(income.size()==0)income.add(new Debt(sum, toUser));
        for (Debt income :income) {
            if(income.getToUser().getUserId() == toUser.getUserId()){
                income.setAmount(sum);
                return;
            }
        }
        income.add(new Debt(sum, toUser));
    }


    public void simplifyDebtForSingleUser(){
        for (Debt debt : debts) {
            for(int i = 0; i<debt.getToUser().getDebts().size(); i++){
                while(debt.getAmount()>0.01){
                    if(debt.getAmount()>debt.getToUser().getDebts().get(i).getAmount()){
                        this.addDebt(debt.getToUser().getDebts().get(i).getToUser(),debt.getToUser().getDebts().get(i).getAmount());
                        debt.setAmount(debt.getAmount()-debt.getToUser().getDebts().get(i).getAmount());
                        debt.getToUser().getDebts().get(i).setAmount(0);
                    }else if(debt.getAmount()<debt.getToUser().getDebts().get(i).getAmount()){
                        //TODO: Usikker på om denne elseif-en trengs
                    }
                }
            }
        }
    }


    public ArrayList<Debt> getDebts() {
        return debts;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getPassword() {
        return password;
    }

    public void setDebts(ArrayList<Debt> debts) {
        this.debts = debts;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Household> getAssociatedHouseholds() {
        return associatedHouseholds;
    }

    public void setAssociatedHouseholds(ArrayList<Household> associatedHouseholds) {
        this.associatedHouseholds = associatedHouseholds;
    }
}