package se.lexicon.dao;

import se.lexicon.model.Person;
import se.lexicon.model.TodoItem;

import java.util.List;
import java.util.Optional;

public interface TodoItemsDao {

    TodoItem create(TodoItem todoItem);

    List<TodoItem> findAll();

    Optional<TodoItem> findById(int id);

    List<TodoItem> findByDoneStatus(boolean done);

    List<TodoItem> findByAssignee(int id);

    List<TodoItem> findByAssignee(Person person);

    List<TodoItem> findByUnassignedTodoItems();

    TodoItem update(TodoItem todoItem);

    boolean deleteById(int id);
}
