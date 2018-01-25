package classes;

/**
 * <p>Item class.</p>
 *
 */
public class Item {
    private int itemId;
    String name;
    User checkedBy;

    /**
     * <p>Constructor for Item.</p>
     */
    public Item() {}

    /**
     * <p>Getter for the field <code>itemId</code>.</p>
     *
     * @return a int.
     */
    public int getItemId() {
        return itemId;
    }

    /**
     * <p>Setter for the field <code>itemId</code>.</p>
     *
     * @param itemId a int.
     */
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    /**
     * <p>Getter for the field <code>name</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>Setter for the field <code>name</code>.</p>
     *
     * @param name a {@link java.lang.String} object.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>Getter for the field <code>checkedBy</code>.</p>
     *
     * @return a {@link classes.User} object.
     */
    public User getCheckedBy() {
        return checkedBy;
    }

    /**
     * <p>Setter for the field <code>checkedBy</code>.</p>
     *
     * @param checkedBy a {@link classes.User} object.
     */
    public void setCheckedBy(User checkedBy) {
        this.checkedBy = checkedBy;
    }
}
