package classes;

/**
 * Created by Camilla Velvin on 23.01.2018.
 */
public class ChangePasswordContainer {
    private String oldPassword;
    private String newPassword;
    private int userId;

    public ChangePasswordContainer() {
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
