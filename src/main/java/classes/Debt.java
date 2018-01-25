package classes;

public class Debt {
    private double amount;
    private User toUser;

    public Debt(double amount, User toUser){
        this.amount = amount;
        this.toUser = toUser;
    }

    public double getAmount() {

        return Math.round(amount * 100)/100;
    }

    public User getToUser() {
        return toUser;
    }

    public void setAmount(double amount) {
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }
}
