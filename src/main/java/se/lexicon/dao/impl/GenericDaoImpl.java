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
    public abstract String getCreateQuery();
    public abstract void setCreateStatement(PreparedStatement ps, T entity) throws SQLException;
    public abstract void handleGeneratedKeys(ResultSet rs, T entity) throws SQLException;
    public abstract String getUpdateQuery();
    public abstract void setUpdateStatement(PreparedStatement ps, T entity) throws SQLException;
    public abstract T mapResultSetToEntity(ResultSet rs) throws SQLException;

    //generic methods
    @Override
    public T create(T entity) {
        try (PreparedStatement ps = connection.prepareStatement(getCreateQuery(), PreparedStatement.RETURN_GENERATED_KEYS)) {
            setCreateStatement(ps, entity);
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    handleGeneratedKeys(rs, entity);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌Error in generic create: " + e.getMessage());
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public ArrayList<T> findAll() {
        ArrayList<T> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(getFindAllQuery())) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToEntity(rs));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error in generic findAll: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public Optional<T> findById(int id){
        try (PreparedStatement ps = connection.prepareStatement(getFindByIdQuery())) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("❌Error in generic findById: " + e.getMessage());
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public T update(T entity) {
        try (PreparedStatement ps = connection.prepareStatement(getUpdateQuery())) {
            setUpdateStatement(ps, entity);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ Error in generic update: " + e.getMessage());
        }
        return entity;
    }

    @Override
    public boolean deleteById(int id) {
        try (PreparedStatement ps = connection.prepareStatement(getDeleteByIdQuery())) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error in generic deleteById: " + e.getMessage());
        }
        return false;
    }
}