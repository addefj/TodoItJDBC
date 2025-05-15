package se.lexicon.dao.impl;

import se.lexicon.dao.TodoItemsDao;
import se.lexicon.model.Person;
import se.lexicon.model.TodoItem;
import java.util.ArrayList;
import java.util.List;

public class TodoItemsDaoImpl implements TodoItemsDao {

    //fields
    List<TodoItem> todoItemList = new ArrayList<>();

    //methods
    @Override
    public TodoItem create(TodoItem todoItem) {
        return null;
    }

    @Override
    public ArrayList<TodoItem> findAll() {
        return null;
    }

    @Override
    public TodoItem findById(int id) {
        return null;
    }

    @Override
    public ArrayList<TodoItem> findByDoneStatus(boolean done) {
        return null;
    }

    @Override
    public ArrayList<TodoItem> findByAssignee(int id) {
        return null;
    }

    @Override
    public ArrayList<TodoItem> findByAssignee(Person person) {
        return null;
    }

    @Override
    public ArrayList<TodoItem> findByUnassignedTodoItems() {
        return null;
    }

    @Override
    public TodoItem update(TodoItem todoItem) {
        return null;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }
}
