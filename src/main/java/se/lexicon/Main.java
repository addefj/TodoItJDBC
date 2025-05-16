package se.lexicon;

import se.lexicon.dao.impl.PersonDaoImpl;
import se.lexicon.dao.impl.TodoItemsDaoImpl;
import se.lexicon.db.MySQLConnection;
import se.lexicon.model.Person;
import se.lexicon.model.TodoItem;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        try {
            Connection mySQLConnection = MySQLConnection.getConnection();
            PersonDaoImpl personDaoImpl = new PersonDaoImpl(mySQLConnection);
            TodoItemsDaoImpl todoItemsDaoImpl = new TodoItemsDaoImpl(mySQLConnection);

            //testing to create a person to db

            //Person person = new Person("Lovisa","Svensson");
            //Person createdPerson = personDaoImpl.create(new Person("John", "Svensson"));
            //Person createdPerson = personDaoImpl.create(person);
            //System.out.println("Created person: " + createdPerson);
            //System.out.println("Person created successfully!");




            //testing findAll()
            //System.out.println(personDaoImpl.findAll());

            //testing findById()
            /*
            Optional<Person> foundPerson = personDaoImpl.findById(3);
            if(foundPerson.isPresent()){
                System.out.println(foundPerson.get());
            } else {
                System.out.println("Person not found");
            }
             */

            //testing findByName()
            //System.out.println(personDaoImpl.findByName("John Svensson"));

            //testing update() person
            //personDaoImpl.update(new Person(5, "Ebba", "Nilsson"));


            //testing deleteById()
            //boolean successfulDelete = personDaoImpl.delete(3);
            //if(successfulDelete) System.out.println("Person deleted successfully!");


            //testing creating todoItem

            //TodoItem item = new TodoItem("garden", "cutting grass", LocalDate.of(2025, 5, 19), false);
            //System.out.println("Created todoItem: " + todoItemsDaoImpl.create(item));


            //testing findAll()
            //System.out.println(todoItemsDaoImpl.findAll());

            //testing findById()
            /*
            Optional<TodoItem> foundTodoItem = todoItemsDaoImpl.findById(1);
            if(foundTodoItem.isPresent()){
                System.out.println(foundTodoItem.get());
            } else {
                System.out.println("TodoItem not found");
            }
             */

            //testing findByDoneStatus()
            //System.out.println(todoItemsDaoImpl.findByDoneStatus(true));

            //testing findByAssignee() Id
            //System.out.println(todoItemsDaoImpl.findByAssignee(2));

            //testing findByAssignee() Object
            //Person p = new Person(2, "Lovisa","Svensson");
            //System.out.println(todoItemsDaoImpl.findByAssignee(p));

            //testing findByUnassignedTodoItems
            //System.out.println(todoItemsDaoImpl.findByUnassignedTodoItems());

            //testing update() todoItem
            //todoItemsDaoImpl.update(new TodoItem(9, "garden", "cutting grass, trim hedges", LocalDate.of(2025, 5, 19), false, null));

            //testing deleteById()
            boolean successfulDelete = todoItemsDaoImpl.deleteById(8);
            if(successfulDelete) System.out.println("todoItem deleted successfully!");


        } catch (SQLException e) {
            System.out.println("MySQL DB connection failed");
        }
    }
}