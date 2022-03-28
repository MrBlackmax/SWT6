package swt6.spring.worklog.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import swt6.spring.worklog.dao.EmployeeDao;
import swt6.spring.worklog.dao.EmployeeRepository;
import swt6.spring.worklog.domain.Employee;
import swt6.util.DbScriptRunner;
import swt6.util.JpaUtil;

import static swt6.util.PrintUtil.printSeparator;
import static swt6.util.PrintUtil.printTitle;

public class DbTest {

    private static void createSchema(DataSource ds, String ddlScript) {
        try {
            DbScriptRunner scriptRunner = new DbScriptRunner(ds.getConnection());
            InputStream is = DbTest.class.getClassLoader().getResourceAsStream(ddlScript);
            if (is == null)
                throw new IllegalArgumentException(String.format("File %s not found in classpath.", ddlScript));
            scriptRunner.runScript(new InputStreamReader(is));
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return;
        }
    }

    private static void testJdbc() {

        try (AbstractApplicationContext factory =
                     new ClassPathXmlApplicationContext(
                             "swt6/spring/worklog/test/applicationContext-jdbc.xml")) {

            printTitle("create schema", 60, '-');
            createSchema(factory.getBean("dataSource", DataSource.class),
                    "swt6/spring/worklog/test/CreateWorklogDbSchema.sql");
            EmployeeDao emplDao = factory.getBean("employeeDaoJdbc", EmployeeDao.class);
            printTitle("insert employee", 60, '-');
            var employee = new Employee("Max", "Muster", LocalDate.of(2022, 03, 15));
            //emplDao.insert(employee);
            employee = emplDao.merge(employee);
            System.out.println("empl1 = " + ((employee == null) ? null : employee.toString()));
            printTitle("update employee", 60, '-');
            employee.setFirstName("Anna");
            employee = emplDao.merge(employee);
            printTitle("find all employees", 60, '-');
            var employees = emplDao.findAll();
            employees.forEach(System.out::println);
            printTitle("find employee by id", 60, '_');
            var empl1 = emplDao.findById(1L);
            System.out.println("empl=" + ((employee != null) ? employee.toString() : null));
            empl1 = emplDao.findById(100L);
            System.out.println("empl=" + ((employee != null) ? employee.toString() : null));
        }
    }

    @SuppressWarnings("unused")
    private static void testJpa() {
        try (AbstractApplicationContext factory = new ClassPathXmlApplicationContext(
                "swt6/spring/worklog/test/applicationContext-jpa1.xml")) {

            var emFactory = factory.getBean(EntityManagerFactory.class);
            var emplDao = factory.getBean("employeeDao", EmployeeDao.class);
            var testEmpl = new Employee("Test", "Test", LocalDate.of(1999, 9, 9));

            try {
                JpaUtil.beginTransaction(emFactory);
                emplDao.insert(testEmpl);
                testEmpl.setFirstName("Max");
                emplDao.merge(testEmpl);
            } finally {
                JpaUtil.commitTransaction(emFactory);
            }

            printTitle("find employee", 60, '-');
            final Long empl1Id = testEmpl.getId();
            JpaUtil.executeInTransaction(emFactory, () -> {
                Optional<Employee> empl = emplDao.findById(empl1Id);
                System.out.println("empl = " + (empl.isPresent() ? empl.get().toString() : "not found"));
                empl = emplDao.findById(100L);
                System.out.println("empl = " + (empl.isPresent() ? empl.get().toString() : "not found"));
            });

            printTitle("find all employees", 60, '-');
            JpaUtil.executeInTransaction(emFactory, () -> {
                var empls = emplDao.findAll();
                empls.forEach(System.out::println);
            });

            //JpaUtil.executeInTransaction(emFactory, () -> emplDao.insert(testEmpl));
        }
    }

    @SuppressWarnings("unused")
    private static void testSpringData() {
        try (AbstractApplicationContext factory = new ClassPathXmlApplicationContext(
                "swt6/spring/worklog/test/applicationContext-jpa1.xml")) {

            var emFactory = factory.getBean(EntityManagerFactory.class);
            JpaUtil.executeInTransaction(emFactory, () -> {
                var repository = JpaUtil.getJpaRepository(emFactory, EmployeeRepository.class);
                var testEmpl = new Employee("Hawara", "Oida", LocalDate.of(1879, 9, 9));
                testEmpl = repository.save(testEmpl);
                repository.flush();

                printTitle("update employee", 60, '-');
                testEmpl.setLastName("Test");
                testEmpl = repository.save(testEmpl);
            });

            printSeparator(60, '-');

            JpaUtil.executeInTransaction(emFactory, () -> {
                var repo = JpaUtil.getJpaRepository(emFactory, EmployeeRepository.class);
                printTitle("findById", 60, '-');
                Optional<Employee> empl1 = repo.findById(1L);
                System.out.println("empl = " + (empl1.isPresent() ? empl1.get().toString() : "not found"));
                printTitle("findByAll", 60, '-');
                var empls = repo.findAll();
                empls.forEach(System.out::println);
            });

            JpaUtil.executeInTransaction(emFactory, () -> {
                var repo = JpaUtil.getJpaRepository(emFactory, EmployeeRepository.class);
                printTitle("find by lastname", 60, '-');
                var empl = repo.findByLastName("Test");
                System.out.println("empl = " + (empl.isPresent() ? empl.get().toString() : "not found"));
                printTitle("find all by lastname", 60, '-');
                var empls = repo.findByLastNameContaining("es");
                empls.forEach(System.out::println);
            });
        }
    }

    public static void main(String[] args) {

        printSeparator(60);
        //printTitle("testJDBC", 60);
        //printSeparator(60);
        //testJdbc();

        // printSeparator(60);
        //printTitle("testJpa", 60);
        // printSeparator(60);
        // testJpa();

        printSeparator(60);
        printTitle("testSpringData", 60);
        printSeparator(60);
        testSpringData();
    }
}
