package se.lexicon.dao.impl;

import se.lexicon.dao.TodoItemsDao;
import se.lexicon.model.Person;
import se.lexicon.model.TodoItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class TodoItemsDaoImpl implements TodoItemsDao {

    //fields
    private Connection connection;

    //constructor
    public TodoItemsDaoImpl(Connection connection) {
        this.connection = connection;
    }

    //methods
    @Override
    public TodoItem create(TodoItem todoItem) {
        String sqlQuery = "INSERT INTO todo_item (title, description, deadline, done, assignee_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, todoItem.getTitle());
            preparedStatement.setString(2, todoItem.getDescription());
            preparedStatement.setDate(3, java.sql.Date.valueOf(todoItem.getDeadLine()));
            preparedStatement.setBoolean(4, todoItem.isDone());
            if (todoItem.getAssigneeId() == null) {
                preparedStatement.setNull(5, java.sql.Types.INTEGER);
            } else {
                preparedStatement.setInt(5, todoItem.getAssigneeId());
            }

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    int generatedTodoItemId = resultSet.getInt(1);
                    todoItem.setId(generatedTodoItemId);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌Error creating todoItem: " + e.getMessage());
            e.printStackTrace();
        }
        return todoItem;
    }

    @Override
    public ArrayList<TodoItem> findAll() {
        ArrayList<TodoItem> TodoItemList = new ArrayList<>();
        String sqlQuery = "SELECT * FROM todo_item";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                TodoItemList.add(new TodoItem(
                        resultSet.getInt("todo_id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getDate("deadline").toLocalDate(),
                        resultSet.getBoolean("done"),
                        resultSet.getInt("assignee_id")
                ));
            }

        } catch (SQLException e) {
            System.err.println("❌Error finding all todoItems: " + e.getMessage());
            e.printStackTrace();
        }

        return TodoItemList;
    }

    @Override
    public Optional<TodoItem> findById(int id) {
        String sqlQuery = "SELECT * FROM todo_item WHERE todo_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {
                    TodoItem todoItem = new TodoItem(
                            resultSet.getInt("todo_id"),
                            resultSet.getString("title"),
                            resultSet.getString("description"),
                            resultSet.getDate("deadline").toLocalDate(),
                            resultSet.getBoolean("done"),
                            resultSet.getInt("assignee_id")
                    );
                    return Optional.of(todoItem);
                }
            }

        } catch (SQLException e) {
            System.err.println("❌Error finding todoItem: " + e.getMessage());
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public ArrayList<TodoItem> findByDoneStatus(boolean done) {
        ArrayList<TodoItem> TodoItemList = new ArrayList<>();
        String sqlQuery = "SELECT * FROM todo_item WHERE done = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setBoolean(1, done);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    TodoItemList.add(new TodoItem(
                            resultSet.getInt("todo_id"),
                            resultSet.getString("title"),
                            resultSet.getString("description"),
                            resultSet.getDate("deadline").toLocalDate(),
                            resultSet.getBoolean("done"),
                            resultSet.getInt("assignee_id")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("❌Error finding todoItems by done status: " + e.getMessage());
            e.printStackTrace();
        }

        return TodoItemList;

    }

    @Override
    public ArrayList<TodoItem> findByAssignee(int id) {
        ArrayList<TodoItem> TodoItemList = new ArrayList<>();
        String sqlQuery = "SELECT * FROM todo_item WHERE assignee_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    TodoItemList.add(new TodoItem(
                            resultSet.getInt("todo_id"),
                            resultSet.getString("title"),
                            resultSet.getString("description"),
                            resultSet.getDate("deadline").toLocalDate(),
                            resultSet.getBoolean("done"),
                            resultSet.getInt("assignee_id")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("❌Error finding todoItems by assignee id: " + e.getMessage());
            e.printStackTrace();
        }

        return TodoItemList;
    }


    @Override
    public ArrayList<TodoItem> findByAssignee(Person person) {

        ArrayList<TodoItem> TodoItemList = new ArrayList<>();
        String sqlQuery = "SELECT * FROM todo_item ti JOIN person p ON ti.assignee_id = p.person_id WHERE person_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1, person.getId());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    TodoItemList.add(new TodoItem(
                            resultSet.getInt("todo_id"),
                            resultSet.getString("title"),
                            resultSet.getString("description"),
                            resultSet.getDate("deadline").toLocalDate(),
                            resultSet.getBoolean("done"),
                            resultSet.getInt("assignee_id")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("❌Error finding todoItems by assignee: " + e.getMessage());
            e.printStackTrace();
        }

        return TodoItemList;
    }


    @Override
    public ArrayList<TodoItem> findByUnassignedTodoItems() {
        ArrayList<TodoItem> TodoItemList = new ArrayList<>();
        String sqlQuery = "SELECT * FROM todo_item WHERE assignee_id IS NULL";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    TodoItemList.add(new TodoItem(
                            resultSet.getInt("todo_id"),
                            resultSet.getString("title"),
                            resultSet.getString("description"),
                            resultSet.getDate("deadline").toLocalDate(),
                            resultSet.getBoolean("done"),
                            resultSet.getObject("assignee_id", Integer.class)
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("❌Error finding unassigned todoItems: " + e.getMessage());
            e.printStackTrace();
        }

        return TodoItemList;
    }


    @Override
    public TodoItem update(TodoItem todoItem) {
        String sqlQuery = "UPDATE todo_item SET title = ?, description = ?, deadline = ?, done = ?, assignee_id = ? WHERE todo_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setString(1, todoItem.getTitle());
            preparedStatement.setString(2, todoItem.getDescription());
            preparedStatement.setDate(3, java.sql.Date.valueOf(todoItem.getDeadLine()));
            preparedStatement.setBoolean(4, todoItem.isDone());
            if (todoItem.getAssigneeId() == null) {
                preparedStatement.setNull(5, java.sql.Types.INTEGER);
            } else {
                preparedStatement.setInt(5, todoItem.getAssigneeId());
            }
            preparedStatement.setInt(6, todoItem.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ Error updating todoItem: " + e.getMessage());
        }
        return todoItem;
    }


    @Override
    public boolean deleteById(int id) {
        String sqlQuery = "DELETE FROM todo_item WHERE todo_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setInt(1, id);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error deleting todoItem: " + e.getMessage());
        }
        return false;
    }
}
