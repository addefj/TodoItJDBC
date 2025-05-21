package se.lexicon.dao;
import se.lexicon.model.Person;
import se.lexicon.model.TodoItem;
import java.util.List;

public interface TodoItemsDao extends BaseDao<TodoItem> {

    TodoItem create(TodoItem todoItem);

    List<TodoItem> findByDoneStatus(boolean done);

    List<TodoItem> findByAssignee(int id);

    List<TodoItem> findByAssignee(Person person);

    List<TodoItem> findByUnassignedTodoItems();

    TodoItem update(TodoItem todoItem);
}
