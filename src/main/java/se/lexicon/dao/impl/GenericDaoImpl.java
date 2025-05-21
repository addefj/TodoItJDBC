package se.lexicon.dao.impl;
import se.lexicon.dao.BaseDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public abstract class GenericDaoImpl<T> implements BaseDao<T> {

    //fields
    protected final Connection connection;

    //constructor
    protected GenericDaoImpl(Connection connection) {
        this.connection = connection;
    }

    //abstract methods
    public abstract String getFindAllQuery();
    public abstract String getFindByIdQuery();
    public abstract String getDeleteByIdQuery();
    public abstract T mapResultSetToEntity(ResultSet rs) throws SQLException;

    //generic methods
    @Override
    public ArrayList<T> findAll() {
        ArrayList<T> list = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(getFindAllQuery())) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(mapResultSetToEntity(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error in generic findAll: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public Optional<T> findById(int id){
        try (PreparedStatement preparedStatement = connection.prepareStatement(getFindByIdQuery())) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToEntity(resultSet));
                }
            }
        } catch (SQLException e) {
            System.err.println("❌Error in generic findById: " + e.getMessage());
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public boolean deleteById(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(getDeleteByIdQuery())) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error in generic deleteById: " + e.getMessage());
        }
        return false;
    }
}