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
import java.util.Optional;

public class Main {
    public static void main(String[] args) {

        try {
            Connection mySQLConnection = MySQLConnection.getConnection();
            PersonDao personDao = new PersonDaoImpl(mySQLConnection);
            TodoItemsDao todoItemsDao = new TodoItemsDaoImpl(mySQLConnection);


            //testing create()
            Person createdPerson = personDao.create(new Person("Kalle", "Kula"));
            System.out.println("Created person: " + createdPerson);
            System.out.println("Person created successfully!");

            TodoItem item = new TodoItem("cleaning", "kitchen, bathroom", LocalDate.now().plusDays(3), true, personDao.findById(2).get());
            System.out.println("Created todoItem: " + todoItemsDao.create(item));


            //testing findAll()
            personDao.findAll().forEach(System.out::println);
            System.out.println("---------------");
            todoItemsDao.findAll().forEach(System.out::println);
            System.out.println("---------------");

            //testing findById()
            Optional<Person> foundPerson = personDao.findById(1);
            foundPerson.ifPresent(System.out::println);
            System.out.println("---------------");
            Optional<TodoItem> foundTodoItem = todoItemsDao.findById(1);
            foundTodoItem.ifPresent(System.out::println);
            System.out.println("---------------");

            //testing deleteById()
//            boolean successfulDelete = personDao.deleteById(3);
//            if(successfulDelete) System.out.println("Person deleted successfully!");
//            successfulDelete = todoItemsDao.deleteById(3);
//            if(successfulDelete) System.out.println("TodoItem deleted successfully!");

            //testing update()
            //personDao.update(new Person(5, "Ebba", "Larsson"));
            //todoItemsDao.update(new TodoItem(5,"Task 3", "Description 3", LocalDate.now().plusDays(2), false, new Person(5, "Ebba", "Larsson")));

            //testing findByName()
          //  System.out.println(personDao.findByName("Svensson"));


            //testing findByDoneStatus()
            //System.out.println(todoItemsDao.findByDoneStatus(done));

            //testing findByAssignee() Id
            //System.out.println(todoItemsDao.findByAssignee(2));

            //testing findByAssignee() Object
//            Person p = new Person("Martin","Lind");
//            personDao.create(p);
//            System.out.println(p);
//            System.out.println("------------------");
//            todoItemsDao.create(new TodoItem("Task 1", "Description 1", LocalDate.now().plusDays(3), false, p));
//            todoItemsDao.create(new TodoItem("Task 2", "Description 2", LocalDate.now().plusDays(7), false, p));
//            System.out.println(todoItemsDao.findByAssignee(p));
//            System.out.println("------------------");


            //testing findByUnassignedTodoItems
            //System.out.println(todoItemsDao.findByUnassignedTodoItems());


        } catch (SQLException e) {
            System.out.println("MySQL DB connection failed");
        }
    }
}