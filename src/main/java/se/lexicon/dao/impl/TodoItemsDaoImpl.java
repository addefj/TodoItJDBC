package se.lexicon.dao.impl;
import se.lexicon.dao.TodoItemsDao;
import se.lexicon.model.Person;
import se.lexicon.model.TodoItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TodoItemsDaoImpl extends GenericDaoImpl<TodoItem> implements TodoItemsDao {

    //fields
    private Connection connection;

    //constructor
    public TodoItemsDaoImpl(Connection connection) {
        super(connection);
    }

    //methods
    @Override
    public String getFindAllQuery() {
        return "SELECT * FROM todo_item ti LEFT JOIN person p ON ti.assignee_id = p.person_id";
    }

    @Override
    public String getFindByIdQuery() {
        return "SELECT * FROM todo_item ti LEFT JOIN person p ON ti.assignee_id = p.person_id WHERE todo_id = ?";
    }

    @Override
    public String getDeleteByIdQuery() {
        return "DELETE FROM todo_item WHERE todo_id = ?";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO todo_item (title, description, deadline, done, assignee_id) VALUES (?, ?, ?, ?, ?)";
    }

    @Override
    public void setCreateStatement(PreparedStatement ps, TodoItem todoItem) throws SQLException {
        ps.setString(1, todoItem.getTitle());
        ps.setString(2, todoItem.getDescription());
        ps.setDate(3, java.sql.Date.valueOf(todoItem.getDeadLine()));
        ps.setBoolean(4, todoItem.isDone());
        if (todoItem.getAssignee() == null) {
            ps.setNull(5, java.sql.Types.INTEGER);
        } else {
            ps.setInt(5, todoItem.getAssignee().getId());
        }
    }

    @Override
    public void handleGeneratedKeys(ResultSet rs, TodoItem todoItem) throws SQLException {
        int generatedId = rs.getInt(1);
        todoItem.setId(generatedId);
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE todo_item SET title = ?, description = ?, deadline = ?, done = ?, assignee_id = ? WHERE todo_id = ?";
    }

    @Override
    public void setUpdateStatement(PreparedStatement ps, TodoItem todoItem) throws SQLException {
        ps.setString(1, todoItem.getTitle());
        ps.setString(2, todoItem.getDescription());
        ps.setDate(3, java.sql.Date.valueOf(todoItem.getDeadLine()));
        ps.setBoolean(4, todoItem.isDone());
        if (todoItem.getAssignee() == null) {
            ps.setNull(5, java.sql.Types.INTEGER);
        } else {
            ps.setInt(5, todoItem.getAssignee().getId());
        }
        ps.setInt(6, todoItem.getId());
    }

    @Override
    public ArrayList<TodoItem> findByDoneStatus(boolean done) {
        ArrayList<TodoItem> todoItemList = new ArrayList<>();
        String sqlQuery = "SELECT * FROM todo_item ti LEFT JOIN person p ON ti.assignee_id = p.person_id WHERE done = ?";
        try (PreparedStatement ps = connection.prepareStatement(sqlQuery)) {
            ps.setBoolean(1, done);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    todoItemList.add(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("❌Error finding todoItems by done status: " + e.getMessage());
            e.printStackTrace();
        }
        return todoItemList;
    }

    @Override
    public ArrayList<TodoItem> findByAssignee(int id) {
        ArrayList<TodoItem> todoItemList = new ArrayList<>();
        String sqlQuery = "SELECT * FROM todo_item ti LEFT JOIN person p ON ti.assignee_id = p.person_id WHERE person_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sqlQuery)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    todoItemList.add(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("❌Error finding todoItems by assignee id: " + e.getMessage());
            e.printStackTrace();
        }
        return todoItemList;
    }

    @Override
    public ArrayList<TodoItem> findByAssignee(Person person) {
        ArrayList<TodoItem> todoItemList = new ArrayList<>();
        String sqlQuery = "SELECT * FROM todo_item ti JOIN person p ON ti.assignee_id = p.person_id WHERE person_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sqlQuery)) {
            ps.setInt(1, person.getId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    todoItemList.add(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("❌Error finding todoItems by assignee: " + e.getMessage());
            e.printStackTrace();
        }

        return todoItemList;
    }


    @Override
    public ArrayList<TodoItem> findByUnassignedTodoItems() {
        ArrayList<TodoItem> todoItemList = new ArrayList<>();
        String sqlQuery = "SELECT * FROM todo_item WHERE assignee_id IS NULL";
        try (PreparedStatement ps = connection.prepareStatement(sqlQuery)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Person assignee = null;
                    todoItemList.add(new TodoItem(
                            rs.getInt("todo_id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getDate("deadline").toLocalDate(),
                            rs.getBoolean("done"),
                            assignee
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("❌Error finding unassigned todoItems: " + e.getMessage());
            e.printStackTrace();
        }

        return todoItemList;
    }

    @Override
    public TodoItem mapResultSetToEntity(ResultSet rs) throws SQLException {
        Person assignee = null;
        int personId = rs.getInt("person_id");
        if (!rs.wasNull()) {
            assignee = new Person(
                    personId,
                    rs.getString("first_name"),
                    rs.getString("last_name")
            );
        }
        return new TodoItem(
                rs.getInt("todo_id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getDate("deadline").toLocalDate(),
                rs.getBoolean("done"),
                assignee
        );
    }
}
