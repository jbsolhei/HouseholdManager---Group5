package classes;

import java.time.LocalDateTime;

/**
 * <p>News class.</p>
 *
 */
public class News {
    private int newsId;
    private int userId;
    private User user;
    private int houseId;
    private String message;
    private LocalDateTime time;

    /**
     * <p>Getter for the field <code>user</code>.</p>
     *
     * @return a {@link classes.User} object.
     */
    public User getUser() {
        return user;
    }

    /**
     * <p>Setter for the field <code>user</code>.</p>
     *
     * @param user a {@link classes.User} object.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * <p>Getter for the field <code>newsId</code>.</p>
     *
     * @return a int.
     */
    public int getNewsId() {
        return newsId;
    }

    /**
     * <p>Setter for the field <code>newsId</code>.</p>
     *
     * @param newsId a int.
     */
    public void setNewsId(int newsId) {
        this.newsId = newsId;
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
     * <p>Getter for the field <code>time</code>.</p>
     *
     * @return a {@link java.time.LocalDateTime} object.
     */
    public LocalDateTime getTime() {
        return time;
    }

    /**
     * <p>Setter for the field <code>time</code>.</p>
     *
     * @param time a {@link java.time.LocalDateTime} object.
     */
    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
