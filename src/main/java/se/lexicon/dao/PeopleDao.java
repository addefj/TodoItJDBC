package se.lexicon.dao;

import se.lexicon.model.Person;

import java.util.List;
import java.util.Optional;

public interface PeopleDao {

    Person create(Person person);

    List<Person> findAll();

    Optional<Person> findById(int id);

    List<Person> findByName(String name);

    Person update(Person person);

    boolean delete(int id);

}
