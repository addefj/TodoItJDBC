package se.lexicon.dao.impl;

import se.lexicon.dao.PersonDao;
import se.lexicon.model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class PersonDaoImpl implements PersonDao {

    //fields
    private Connection connection;

    //constructor
    public PersonDaoImpl(Connection connection) {
        this.connection = connection;
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
    public ArrayList<Person> findAll() {
        ArrayList<Person> personList = new ArrayList<>();
        String sqlQuery = "SELECT * FROM person";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                personList.add(createPersonFromDB(resultSet));
            }

        } catch (SQLException e) {
            System.err.println("❌Error finding all persons: " + e.getMessage());
            e.printStackTrace();
        }

        return personList;
    }

    @Override
    public Optional<Person> findById(int id) {
        String sqlQuery = "SELECT * FROM person WHERE person_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {
                    return Optional.of(createPersonFromDB(resultSet));
                }
            }

        } catch (SQLException e) {
            System.err.println("❌Error finding person: " + e.getMessage());
            e.printStackTrace();
        }

        return Optional.empty();
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
                    personList.add(createPersonFromDB(resultSet));
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
    public boolean delete(int id) {
        String sqlQuery = "DELETE FROM person WHERE person_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setInt(1, id);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error deleting person: " + e.getMessage());
        }
        return false;
    }

    public Person createPersonFromDB(ResultSet resultSet) throws SQLException {
        return new Person(
                resultSet.getInt("person_id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name")
        );
    }

}
