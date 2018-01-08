package classes;

public class Debt {
    private double sum;
    private User toUser;

    public Debt(double sum, User toUser){
        this.sum = sum;
        this.toUser = toUser;
    }

    public double getSum() {
        return sum;
    }

    public User getToUser() {
        return toUser;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }
}
