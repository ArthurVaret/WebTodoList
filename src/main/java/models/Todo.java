package models;

public class Todo {
    private final int id;
    private final String description;

    public Todo(int id, String desc) {
        this.id = id;
        this.description = desc;
    }

    public Todo(String desc) {
        this(0,desc);
    }

    public int getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }

}
