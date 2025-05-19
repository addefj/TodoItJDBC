package se.lexicon;

import se.lexicon.dao.PersonDao;
import se.lexicon.dao.TodoItemsDao;
import se.lexicon.dao.impl.PersonDaoImpl;
import se.lexicon.dao.impl.TodoItemsDaoImpl;
import se.lexicon.db.MySQLConnection;
import se.lexicon.model.Person;
import se.lexicon.model.TodoItem;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {

        try {
            Connection mySQLConnection = MySQLConnection.getConnection();
            PersonDao personDao = new PersonDaoImpl(mySQLConnection);
            TodoItemsDao todoItemsDao = new TodoItemsDaoImpl(mySQLConnection);

            //testing to create a person to db
            //Person person = new Person("Lovisa","Svensson");
            //Person createdPerson = personDao.create(new Person("John", "Svensson"));
            //Person createdPerson = personDao.create(person);
            //System.out.println("Created person: " + createdPerson);
            //System.out.println("Person created successfully!");




            //testing findAll()
            //System.out.println(personDaoImpl.findAll());

            //testing findById()
            /*
            Optional<Person> foundPerson = personDao.findById(3);
            if(foundPerson.isPresent()){
                System.out.println(foundPerson.get());
            } else {
                System.out.println("Person not found");
            }
             */

            //testing findByName()
            //System.out.println(personDao.findByName("John Svensson"));

            //testing update() person
            //personDao.update(new Person(5, "Ebba", "Nilsson"));


            //testing deleteById()
            //boolean successfulDelete = personDao.delete(3);
            //if(successfulDelete) System.out.println("Person deleted successfully!");


            //testing creating todoItem

            //TodoItem item = new TodoItem("garden", "cutting grass", LocalDate.of(2025, 5, 19), false);
            //System.out.println("Created todoItem: " + todoItemsDao.create(item));


            //testing findAll()
            //System.out.println(todoItemsDao.findAll());

            //testing findById()
            /*
            Optional<TodoItem> foundTodoItem = todoItemsDao.findById(1);
            if(foundTodoItem.isPresent()){
                System.out.println(foundTodoItem.get());
            } else {
                System.out.println("TodoItem not found");
            }
             */

            //testing findByDoneStatus()
            //System.out.println(todoItemsDao.findByDoneStatus(true));

            //testing findByAssignee() Id
            //System.out.println(todoItemsDao.findByAssignee(2));

            //testing findByAssignee() Object
//            Person p = new Person("Martin","Lind");
//            personDao.create(p);
//            System.out.println(p);
//            System.out.println("------------------");
//            todoItemsDao.create(new TodoItem("Task 1", "Description 1", LocalDate.now().plusDays(3), false, p.getId()));
//            todoItemsDao.create(new TodoItem("Task 2", "Description 2", LocalDate.now().plusDays(7), false, p.getId()));
//            System.out.println(todoItemsDao.findByAssignee(p));
//            System.out.println("------------------");

            // 1. Get existing person by ID (for example, person with ID = 1)
//            Optional<Person> optionalPerson = personDao.findById(2);
//
//            if (optionalPerson.isPresent()) {
//                Person existingPerson = optionalPerson.get();
//
//                // 2. Find all todo items assigned to this person
//                List<TodoItem> items = todoItemsDao.findByAssignee(existingPerson);
//
//                // 3. Print the results
//                System.out.println("TodoItems assigned to: " + existingPerson.getFirstName() + " " + existingPerson.getLastName());
//                for (TodoItem item : items) {
//                    System.out.println(item);
//                }
//
//            } else {
//                System.out.println("❌ Person with ID 1 not found in database.");
//            }

            //testing findByUnassignedTodoItems
            //System.out.println(todoItemsDao.findByUnassignedTodoItems());

            //testing update() todoItem
            //todoItemsDao.update(new TodoItem(9, "garden", "cutting grass, trim hedges", LocalDate.of(2025, 5, 19), false, null));

            //testing deleteById()
            //boolean successfulDelete = todoItemsDao.deleteById(8);
            //if(successfulDelete) System.out.println("todoItem deleted successfully!");


        } catch (SQLException e) {
            System.out.println("MySQL DB connection failed");
        }
    }
}