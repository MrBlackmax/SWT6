package swt6.orm.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import swt6.orm.domain.Employee;
import swt6.util.HibernateUtil;

import javax.persistence.criteria.CriteriaUpdate;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EmployeeManager {

  static String promptFor(BufferedReader in, String p) {
    System.out.print(p + "> ");
    System.out.flush();
    try {
      return in.readLine();
    }
    catch (Exception e) {
      return promptFor(in, p);
    }
  }

  public static void saveEmployee (Employee employee) {
    SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    var session = sessionFactory.getCurrentSession();
    Transaction tx = session.beginTransaction();

    session.save(employee);

    tx.commit();
    sessionFactory.close();
  }

  public static void saveEmployee2 (Employee employee) {
    var session = HibernateUtil.getCurrentSession();
    Transaction tx = session.beginTransaction();

    session.save(employee);

    tx.commit();
  }

  private static List<Employee> getAllEmployees() {
    var session = HibernateUtil.getCurrentSession();
    var tx = session.beginTransaction();
    //var employees = session.
    var empls = new ArrayList<Employee>();
    empls.add(new Employee("max", "schwarz", LocalDate.of(2000,07,13)));
    return  empls;
  }

  private static void updateEmployee(long id, String firstName, String lastName, LocalDate date) {
    var session = HibernateUtil.getCurrentSession();
    var tx = session.beginTransaction();
    var empl = session.find(Employee.class, id);

  }

  private static boolean deleteEmployeeById(long emplId) {
    var session = HibernateUtil.getCurrentSession();
    var tx = session.beginTransaction();

    var empl = session.find(Employee.class, emplId);
    if (empl != null) session.remove(empl);
    tx.commit();

    Query<?> delQry = session.createQuery("delete from Employee e where e.id=:id");
    delQry.setParameter("id", emplId);

    return empl != null;
  }

  public static void main(String[] args) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    String availCmds = "commands: quit, insert, list,update, delete";
    
    System.out.println("Hibernate Employee Admin");
    System.out.println(availCmds);
    String userCmd = promptFor(in, "");

    HibernateUtil.getSessionFactory();

    try {
      while (!userCmd.equals("quit")) {

        switch (userCmd) {
          
        case "insert":
          saveEmployee(new Employee(
                  promptFor(in, "firstName"),
                  promptFor(in, "lastName"),
                  LocalDate.parse(promptFor(in, "dob (dd.mm.yyyy)"), formatter)
                  ));
          break;
          case "update":
            updateEmployee(Long.parseLong(promptFor(in, "id")),
                    promptFor(in, "firstName"),
                    promptFor(in, "lastName"),
                    LocalDate.parse(promptFor(in, "dob (dd.mm.yyyy)"), formatter)
            );
            break;
          case "list":
            getAllEmployees().forEach(System.out::println);
            break;
          case "delete":
            var success = deleteEmployeeById(Long.parseLong(promptFor(in, "id")));
            System.out.println("Deletion was " + (success ? "" : "not") + " successfull");
            break;

        default:
          System.out.println("ERROR: invalid command");
          break;
        }

        System.out.println(availCmds);
        userCmd = promptFor(in, "");
      } // while

    } // try
    catch (Exception ex) {
      ex.printStackTrace();
      HibernateUtil.closeSessionFactory();
    } // catch
  }


}
