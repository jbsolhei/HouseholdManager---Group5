package classes;

/**
 * <p>Notification class.</p>
 *
 */
public class Notification {

    private String message;
    private int notificationId;
    private int userId;
    private int houseId;
    private String houseName;
    private String dateTime;

    /**
     * <p>Getter for the field <code>message</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getMessage() {
        return message;
    }

    /**
     * <p>Setter for the field <code>message</code>.</p>
     *
     * @param message a {@link java.lang.String} object.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * <p>Getter for the field <code>notificationId</code>.</p>
     *
     * @return a int.
     */
    public int getNotificationId() {
        return notificationId;
    }

    /**
     * <p>Setter for the field <code>notificationId</code>.</p>
     *
     * @param notificationId a int.
     */
    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
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

    /**
     * <p>Getter for the field <code>houseId</code>.</p>
     *
     * @return a int.
     */
    public int getHouseId() {
        return houseId;
    }

    /**
     * <p>Setter for the field <code>houseId</code>.</p>
     *
     * @param houseId a int.
     */
    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }

    /**
     * <p>Getter for the field <code>dateTime</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getDateTime() {
        return dateTime;
    }

    /**
     * <p>Setter for the field <code>dateTime</code>.</p>
     *
     * @param dateTime a {@link java.lang.String} object.
     */
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * <p>Getter for the field <code>houseName</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getHouseName() {
        return houseName;
    }

    /**
     * <p>Setter for the field <code>houseName</code>.</p>
     *
     * @param houseName a {@link java.lang.String} object.
     */
    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }
}
