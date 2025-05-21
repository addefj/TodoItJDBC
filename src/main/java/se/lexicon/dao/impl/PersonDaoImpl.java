package se.lexicon.dao.impl;
import se.lexicon.dao.PersonDao;
import se.lexicon.model.Person;
import java.sql.*;
import java.util.ArrayList;

public class PersonDaoImpl extends GenericDaoImpl<Person> implements PersonDao  {

    //fields
    private Connection connection;

    //constructor
    public PersonDaoImpl(Connection connection) {
        super(connection);
    }

    //methods
    @Override
    public Person create(Person person) {
        String sqlQuery = "INSERT INTO person (first_name, last_name) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, person.getFirstName());
            preparedStatement.setString(2, person.getLastName());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    int generatedPersonId = resultSet.getInt(1);
                    person.setId(generatedPersonId);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌Error creating person: " + e.getMessage());
            e.printStackTrace();
        }
        return person;
    }


    @Override
    public String getFindAllQuery() {
        return "SELECT * FROM person";
    }

    @Override
    public String getFindByIdQuery() {
        return "SELECT * FROM person WHERE person_id = ?";
    }

    @Override
    public String getDeleteByIdQuery() {
        return "DELETE FROM person WHERE person_id = ?";
    }

    @Override
    public ArrayList<Person> findByName(String name) {
        ArrayList<Person> personList = new ArrayList<>();
        String sqlQuery = "SELECT * FROM person WHERE first_name LIKE ? OR last_name LIKE ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, "%" + name + "%");
            preparedStatement.setString(2, "%" + name + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    personList.add(mapResultSetToEntity(resultSet));
                }
            }
        } catch (SQLException e) {
            System.err.println("❌Error finding person: " + e.getMessage());
            e.printStackTrace();
        }

        return personList;
    }

    @Override
    public Person update(Person person) {
        String sqlQuery = "UPDATE person SET first_name = ?, last_name = ? WHERE person_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, person.getFirstName());
            preparedStatement.setString(2, person.getLastName());
            preparedStatement.setInt(3, person.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ Error updating person: " + e.getMessage());
        }
        return person;
    }

    @Override
    public Person mapResultSetToEntity(ResultSet rs) throws SQLException {
        return new Person(
                rs.getInt("person_id"),
                rs.getString("first_name"),
                rs.getString("last_name")
        );
    }
}
