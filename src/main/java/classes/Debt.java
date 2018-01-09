package classes;

public class Debt {
    private double amount;
    private User toUser;

    public Debt(double amount, User toUser){
        this.amount = amount;
        this.toUser = toUser;
    }

    public double getAmount() {
        return amount;
    }

    public User getToUser() {
        return toUser;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }
}
