package se.lexicon.dao;
import se.lexicon.model.Person;
import java.util.List;

public interface PersonDao extends BaseDao<Person> {

    Person create(Person person);

    List<Person> findByName(String name);

    Person update(Person person);
}
