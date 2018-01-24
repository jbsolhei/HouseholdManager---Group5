package classes;


public class Stats {
    private int[] tasks = new int[12];
    private String userName;

    public Stats() {

    }

    public int[] getTasks() {
        return tasks;
    }

    public void setTasks(int[] tasks) {
        this.tasks = tasks;
    }

    public void addTasksToMonth(int monthNumber, int numberOfTasks) {
        tasks[monthNumber - 1] += numberOfTasks;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
