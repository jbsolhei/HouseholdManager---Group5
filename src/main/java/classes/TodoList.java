package classes;

public class TodoList {
    private String name;
    private User[] participants;
    private Todo[] todos;

    public TodoList(){}

    public String getName() {
        return name;
    }

    public Todo[] getTodos() {
        return todos;
    }

    public User[] getParticipants() {
        return participants;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParticipants(User[] participants) {
        this.participants = participants;
    }

    public void setTodos(Todo[] todos) {
        this.todos = todos;
    }
}

