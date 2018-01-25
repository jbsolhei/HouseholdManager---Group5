package classes;


/**
 * <p>Stats class.</p>
 *
 */
public class Stats {
    private int[] tasks = new int[12];
    private String userName;

    /**
     * <p>Constructor for Stats.</p>
     */
    public Stats() {

    }

    /**
     * <p>Getter for the field <code>tasks</code>.</p>
     *
     * @return an array of int.
     */
    public int[] getTasks() {
        return tasks;
    }

    /**
     * <p>Setter for the field <code>tasks</code>.</p>
     *
     * @param tasks an array of int.
     */
    public void setTasks(int[] tasks) {
        this.tasks = tasks;
    }

    /**
     * <p>addTasksToMonth.</p>
     *
     * @param monthNumber a int.
     * @param numberOfTasks a int.
     */
    public void addTasksToMonth(int monthNumber, int numberOfTasks) {
        tasks[monthNumber - 1] += numberOfTasks;
    }

    /**
     * <p>Getter for the field <code>userName</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * <p>Setter for the field <code>userName</code>.</p>
     *
     * @param userName a {@link java.lang.String} object.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
