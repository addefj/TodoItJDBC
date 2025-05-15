package se.lexicon;

import se.lexicon.dao.PeopleDao;
import se.lexicon.dao.impl.PeopleDaoImpl;
import se.lexicon.db.MySQLConnection;
import se.lexicon.model.Person;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {

        try {
            Connection mySQLConnection = MySQLConnection.getConnection();
            PeopleDao peopleDao = new PeopleDaoImpl(mySQLConnection);

            //testing to create a person to db
/*
            Person createdPerson = peopleDao.create(new Person("John", "Svensson"));
            System.out.println("Created person: " + createdPerson);
            System.out.println("Person created successfully!");


 */

            //testing findAll()
            //System.out.println(peopleDao.findAll());

            //testing findById()
            /*
            Optional<Person> foundPerson = peopleDao.findById(3);
            if(foundPerson.isPresent()){
                System.out.println(foundPerson.get());
            } else {
                System.out.println("Person not found");
            }
             */

            //testing findByName()
            //System.out.println(peopleDao.findByName("John Svensson"));

            //testing update()
            //peopleDao.update(new Person(5, "Ebba", "Nilsson"));


            //testing delete()
            //boolean successfulDelete = peopleDao.delete(3);
            //if(successfulDelete) System.out.println("Person deleted successfully!");





        } catch (SQLException e) {
            System.out.println("MySQL DB connection failed");
        }
    }
}