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
    public String getCreateQuery() {
        return "INSERT INTO person (first_name, last_name) VALUES (?, ?)";
    }

    @Override
    public void setCreateStatement(PreparedStatement ps, Person person) throws SQLException {
        ps.setString(1, person.getFirstName());
        ps.setString(2, person.getLastName());
    }

    @Override
    public void handleGeneratedKeys(ResultSet rs, Person person) throws SQLException {
        int generatedId = rs.getInt(1);
        person.setId(generatedId);
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE person SET first_name = ?, last_name = ? WHERE person_id = ?";
    }

    @Override
    public void setUpdateStatement(PreparedStatement ps, Person person) throws SQLException {
        ps.setString(1, person.getFirstName());
        ps.setString(2, person.getLastName());
        ps.setInt(3, person.getId());
    }


    @Override
    public ArrayList<Person> findByName(String name) {
        ArrayList<Person> personList = new ArrayList<>();
        String sqlQuery = "SELECT * FROM person WHERE first_name LIKE ? OR last_name LIKE ?";
        try (PreparedStatement ps = connection.prepareStatement(sqlQuery)) {
            ps.setString(1, "%" + name + "%");
            ps.setString(2, "%" + name + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    personList.add(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("❌Error finding person: " + e.getMessage());
            e.printStackTrace();
        }

        return personList;
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
