package se.lexicon.model;

import java.time.LocalDate;

public class TodoItem {

    //fields
    private int id;
    private String title;
    private String description;
    private LocalDate deadLine;
    private boolean done;
    private Integer assigneeId;

    //constructor for creating todoItems
    public TodoItem(String title, String description, LocalDate deadLine, boolean done, Integer assigneeId) {
        setTitle(title);
        setDescription(description);
        setDeadLine(deadLine);
        setDone(done);
        setAssigneeId(assigneeId);
    }

    //constructor for creating unassigned todoItems
    public TodoItem(String title, String description, LocalDate deadLine, boolean done) {
        setTitle(title);
        setDescription(description);
        setDeadLine(deadLine);
        setDone(done);
        this.assigneeId = null;
    }

    //constructor for retrieving data
    public TodoItem(int id, String title, String description, LocalDate deadLine, boolean done, Integer assigneeId) {
        this.id = id;
        setTitle(title);
        setDescription(description);
        setDeadLine(deadLine);
        setDone(done);
        this.assigneeId = assigneeId;
    }

    //methods
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDeadLine() {
        return deadLine;
    }

    public boolean isDone() {
        return done;
    }

    public Integer getAssigneeId() {
        return assigneeId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) throw new IllegalArgumentException("title can't be null or empty");
        this.title = title;
    }

    public void setDescription(String description) {
        if (description == null || description.trim().isEmpty()) throw new IllegalArgumentException("description can't be null or empty");
        this.description = description;
    }

    public void setDeadLine(LocalDate deadLine) {
        if (deadLine == null || LocalDate.now().isAfter(deadLine)) throw new IllegalArgumentException("deadLine can't be null or before today's date");
        this.deadLine = deadLine;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setAssigneeId(Integer assigneeId) {
        if(assigneeId <= 0) throw new IllegalArgumentException("assigneeId can't be <= 0");
        this.assigneeId = assigneeId;
    }

    @Override
    public String toString() {
        return "\nTodoItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", deadLine=" + deadLine +
                ", done=" + done +
                ", assigneeId=" + assigneeId +
                '}';
    }
}
