package classes;

/**
 * <p>Debt class.</p>
 *
 */
public class Debt {
    private double amount;
    private User toUser;

    /**
     * <p>Constructor for Debt.</p>
     *
     * @param amount a double.
     * @param toUser a {@link classes.User} object.
     */
    public Debt(double amount, User toUser){
        this.amount = amount;
        this.toUser = toUser;
    }

    /**
     * <p>Getter for the field <code>amount</code>.</p>
     *
     * @return a double.
     */
    public double getAmount() {

        return Math.round(amount * 100)/100;
    }

    /**
     * <p>Getter for the field <code>toUser</code>.</p>
     *
     * @return a {@link classes.User} object.
     */
    public User getToUser() {
        return toUser;
    }

    /**
     * <p>Setter for the field <code>amount</code>.</p>
     *
     * @param amount a double.
     */
    public void setAmount(double amount) {
    }

    /**
     * <p>Setter for the field <code>toUser</code>.</p>
     *
     * @param toUser a {@link classes.User} object.
     */
    public void setToUser(User toUser) {
        this.toUser = toUser;
    }
}
