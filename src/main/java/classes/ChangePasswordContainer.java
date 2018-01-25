package classes;

/**
 * Created by Camilla Velvin on 23.01.2018.
 */
public class ChangePasswordContainer {
    private String oldPassword;
    private String newPassword;
    private int userId;

    /**
     * <p>Constructor for ChangePasswordContainer.</p>
     */
    public ChangePasswordContainer() {
    }

    /**
     * <p>Getter for the field <code>oldPassword</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getOldPassword() {
        return oldPassword;
    }

    /**
     * <p>Setter for the field <code>oldPassword</code>.</p>
     *
     * @param oldPassword a {@link java.lang.String} object.
     */
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    /**
     * <p>Getter for the field <code>newPassword</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * <p>Setter for the field <code>newPassword</code>.</p>
     *
     * @param newPassword a {@link java.lang.String} object.
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * <p>Getter for the field <code>userId</code>.</p>
     *
     * @return a int.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * <p>Setter for the field <code>userId</code>.</p>
     *
     * @param userId a int.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
}
