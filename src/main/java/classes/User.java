package classes;

import java.util.ArrayList;

public class User {
    private String name;
    private ArrayList<Debt> debts;

    public User(String name, ArrayList<Debt> debts){
        this.name = name;
        this.debts = debts;
    }

    public void addDebt(User toUser, double sum){
        for (Debt debt :debts) {
            if(debt.getToUser().equals(toUser)){
                debt.setSum(debt.getSum()+sum);
            }
        }
        debts.add(new Debt(sum, toUser));
    }

    public void simplifyDebtForSingleUser(){
        for (Debt debt : debts) {
            for(int i = 0; i<debt.getToUser().getDebts().size(); i++){
                while(debt.getSum()>0.01){
                    if(debt.getSum()>debt.getToUser().getDebts().get(i).getSum()){
                        this.addDebt(debt.getToUser().getDebts().get(i).getToUser(),debt.getToUser().getDebts().get(i).getSum());
                        debt.setSum(debt.getSum()-debt.getToUser().getDebts().get(i).getSum());
                        debt.getToUser().getDebts().get(i).setSum(0);
                    }else if(debt.getSum()<debt.getToUser().getDebts().get(i).getSum()){
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

    public void setDebts(ArrayList<Debt> debts) {
        this.debts = debts;
    }

    public void setName(String name) {
        this.name = name;
    }
}