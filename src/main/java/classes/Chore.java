package classes;

import java.util.Date;

public class Chore {
    private String description;
    private java.sql.Date date;
    private boolean done;
    private int houseId;
    private User user;
    private int choreId;
    private int time;

    public Chore(){}

    public String getDescription() {
        return description;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public java.sql.Date getDate() {
        return date;
    }

    public boolean isDone() {
        return done;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(java.sql.Date date) {
        this.date = date;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public int getHouseId() {
        return houseId;
    }

    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }

    public int getChoreId() {
        return choreId;
    }

    public void setChoreId(int choreId) {
        this.choreId = choreId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}