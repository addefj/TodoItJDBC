package se.lexicon.model;

import java.time.LocalDate;

public class TodoItem {

    //fields
    private int id;
    private String title;
    private String description;
    private LocalDate deadLine;
    private boolean done;
    private int assigneeId;

    //constructors
    public TodoItem(String title, String description, LocalDate deadLine, boolean done, int assigneeId) {
        this.title = title;
        this.description = description;
        this.deadLine = deadLine;
        this.done = done;
        this.assigneeId = assigneeId;
    }

    public TodoItem(int id, String title, String description, LocalDate deadLine, boolean done, int assigneeId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.deadLine = deadLine;
        this.done = done;
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

    public int getAssigneeId() {
        return assigneeId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setDeadLine(LocalDate deadLine) {
        this.deadLine = deadLine;
    }

    public void setAssigneeId(int assigneeId) {
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
