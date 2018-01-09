package classes;

import java.util.Date;

public class Todo {
    private String name;
    private Date date;
    private boolean done;

    public Todo(){}

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public boolean isDone() {
        return done;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}