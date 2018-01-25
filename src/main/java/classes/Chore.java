package classes;

import java.time.LocalDateTime;

/**
 * <p>Chore class.</p>
 *
 */
public class Chore {
    private String title;
    private String description;
    private boolean done;
    private int houseId;
    private int userId;
    private User user;
    private int choreId;
    private LocalDateTime time;

    /**
     * <p>Constructor for Chore.</p>
     */
    public Chore(){}

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
     * <p>Getter for the field <code>title</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getTitle() {
        return title;
    }

    /**
     * <p>Setter for the field <code>title</code>.</p>
     *
     * @param title a {@link java.lang.String} object.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * <p>Getter for the field <code>description</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getDescription() {
        return description;
    }

    /**
     * <p>isDone.</p>
     *
     * @return a boolean.
     */
    public boolean isDone() {
        return done;
    }

    /**
     * <p>Setter for the field <code>description</code>.</p>
     *
     * @param description a {@link java.lang.String} object.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * <p>Setter for the field <code>done</code>.</p>
     *
     * @param done a boolean.
     */
    public void setDone(boolean done) {
        this.done = done;
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
     * <p>Getter for the field <code>choreId</code>.</p>
     *
     * @return a int.
     */
    public int getChoreId() {
        return choreId;
    }

    /**
     * <p>Setter for the field <code>choreId</code>.</p>
     *
     * @param choreId a int.
     */
    public void setChoreId(int choreId) {
        this.choreId = choreId;
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
     * @param timeString a {@link java.lang.String} object.
     */
    public void setTime(String timeString) {
        this.time = LocalDateTime.parse(timeString);
    }
}
