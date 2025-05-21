package se.lexicon.dao;
import java.util.ArrayList;
import java.util.Optional;

public interface BaseDao<T> {
    ArrayList<T> findAll();
    Optional<T> findById(int id);
    boolean deleteById(int id);
}
