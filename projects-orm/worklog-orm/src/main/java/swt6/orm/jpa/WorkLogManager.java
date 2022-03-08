package swt6.orm.jpa;

import jdk.swing.interop.SwingInterOpUtils;
import net.bytebuddy.asm.Advice;
import org.hibernate.annotations.common.util.impl.Log;
import org.hibernate.jpa.boot.spi.EntityManagerFactoryBuilder;
import swt6.orm.domain.*;
import swt6.orm.swt6.util.JpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.swing.*;
import java.io.Serializable;
import java.security.spec.ECField;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WorkLogManager {

    private static void insertEmployee(Employee empl) {
        var factory = Persistence.createEntityManagerFactory("WorkLogPU");
        EntityManager manager = null;
        EntityTransaction transaction = null;

        try {
            manager = factory.createEntityManager();
            transaction = manager.getTransaction();
            transaction.begin();
            manager.persist(empl);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null && transaction.isActive()) transaction.rollback();
        } finally {
            if (manager != null) manager.close();
            factory.close();
        }
    }

    private static void insertEmployeeUtil(Employee empl) {
        try {
            var manager = JpaUtil.getEntityManager();
            manager.persist(empl);
            JpaUtil.commit();
        } catch (Exception ex) {
            JpaUtil.rollback();
        }
    }

    private static <T> void insertEntity(T entity) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();

            em.persist(entity);

            JpaUtil.commit();
        } catch (Exception ex) {
            JpaUtil.rollback();
            throw ex;
        }
    }

    public static <T> T saveEntity(T entity) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();

            entity = em.merge(entity);

            JpaUtil.commit();
        } catch (Exception ex) {
            JpaUtil.rollback();
            throw ex;
        }
        return entity;
    }

    private static void listEmployees() {
        var formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd HH:mm");

        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            List<Employee> employeeList = em.createQuery("SELECT e from Employee e", Employee.class).getResultList();
            employeeList.forEach(e -> {
                System.out.println(e);
                if (e.getLogBookEntries().size() > 0) {
                    System.out.println("logbookEntries:");
                    e.getLogBookEntries().forEach(lbe -> System.out.println(lbe));
                }

                if (e.getProjects().size() > 0) {
                    System.out.println("projects: ");
                    e.getProjects().forEach(pro -> System.out.println(pro));
                }
            });

            JpaUtil.commit();
        } catch (Exception ex) {
            JpaUtil.rollback();
            throw ex;
        }
    }

    private static <T> T updateEntity(Serializable id, T entity, Class<T> entityType) {
        T storedEntity = null;
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();

            if (id == null) {
                throw new RuntimeException("Entity doe not exist");
            }

            storedEntity = em.find(entityType, id);
            if (storedEntity == null) {
                throw new RuntimeException("Entity does not exist");
            }

            storedEntity = em.merge(entity);
            JpaUtil.commit();
        } catch (Exception ex) {
            JpaUtil.rollback();
            throw ex;
        }
        return storedEntity;
    }

    private static Employee addLogBookEntries(Employee empl, LogbookEntry... entries) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();

            empl = em.merge(empl);

            for (LogbookEntry entry : entries) {
                //empl.addLogBookEntry(entry);
                entry.attachEmployee(empl);
            }

            //empl = em.merge(empl);

            JpaUtil.commit();
        } catch (Exception ex) {
            JpaUtil.rollback();
            throw ex;
        }
        return empl;
    }

    private static void deleteEmployee(Employee empl) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            empl = em.merge(empl);
            var entries = new ArrayList<>(empl.getLogBookEntries());
            for (var entry : entries) {
                empl.removeLogBookEntry(entry);
            }
            em.remove(empl);
            JpaUtil.commit();
        } catch (Exception ex) {
            JpaUtil.rollback();
            throw ex;
        }
    }

    private static void testFetchingStrategies() {

        // prepare: fetch valid ids for employee and logbookentry
        Long entryId = null;
        Long emplId = null;

        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();

            Optional<LogbookEntry> entry =
                    em.createQuery("select le from LogbookEntry le", LogbookEntry.class)
                            .setMaxResults(1)
                            .getResultList().stream().findAny();
            if (!entry.isPresent()) return;
            entryId = entry.get().getId();

            Optional<Employee> empl =
                    em.createQuery("select e from Employee e", Employee.class)
                            .setMaxResults(1)
                            .getResultList().stream().findAny();
            if (!empl.isPresent()) return;
            emplId = empl.get().getId();

            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }

        System.out.println("############################################");

        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            System.out.println("###>Fetching LogbbokEntry...");
            var entry = em.find(LogbookEntry.class, entryId);
            System.out.println("###> Fetched LogbookEntry");
            var empl1 = entry.getEmployee();
            System.out.println("###> Fetched associated Employee");
            System.out.println(empl1);
            System.out.println("###> Accessed associated Employee");
            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }

        System.out.println("############################################");

        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();

            System.out.println("###>Fetching Employee...");
            var empl2 = em.find(Employee.class, emplId);
            System.out.println("###>Fetched Employee...");
            var entries = empl2.getLogBookEntries();
            System.out.println("###> fetched associated Employee");
            entries.forEach(System.out::println);
            System.out.println("###> Accessed associated Employee");
            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }

        System.out.println("############################################");
    }


    private static void listEntriesOfEmployee(Employee empl) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            System.out.println("logbook entries of employee " + empl.getLastName() + " (" + empl.getId() + ")");
            //var query = em.createQuery("SELECT le FROM LogbookEntry  le WHERE le.employee.id = " + empl.getId(), LogbookEntry.class);
            //var query = em.createQuery("from LogbookEntry where employee.id = ?1",LogbookEntry.class);
            //query.setParameter(1, empl.getId());
            //var query = em.createQuery("from LogbookEntry  where employee.id = :emplId");
            //query.setParameter("emplId", empl.getId());
            var query = em.createQuery("from LogbookEntry  where employee = :empl");
            query.setParameter("empl", empl);
            query.getResultList().forEach(System.out::println);
            JpaUtil.commit();
        } catch (Exception ex) {
            JpaUtil.rollback();
            throw ex;
        }
    }

    private static void listEmployeesResidingIn(String zipCode) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();

            var query = em.createQuery("select e from Employee e where e.address.zipCode=:zip");
            query.setParameter("zip", zipCode);
            query.getResultList().forEach(System.out::println);
            JpaUtil.commit();
        } catch (Exception ex) {
            JpaUtil.rollback();
            throw ex;
        }
    }

    private static void loadEmployeesWithEntries() {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            var query = em.createQuery("select distinct e from Employee e join e.logBookEntries le where le.activity = :activity", Employee.class);
            query.setParameter("activity", "Analysis");
            query.getResultList().forEach(System.out::println);
            query = em.createQuery("select distinct e from Employee  e join fetch e.logBookEntries", Employee.class);
            var empls = query.getResultList();
            JpaUtil.commit();
            empls.forEach(e -> e.getLogBookEntries().forEach(System.out::println));
        } catch (Exception ex) {
            JpaUtil.rollback();
            throw ex;
        }
    }

    private static void listEntriesOfEmployeesCQ(Employee empl) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            var cb = em.getCriteriaBuilder();
            var query = cb.createQuery(LogbookEntry.class);
            var root = query.from(LogbookEntry.class);
            var param = cb.parameter(Employee.class);

            //query.where(cb.equal(root.get("employee"), param)).select(root);
            query.where(cb.equal(root.get(LogbookEntry_.employee), param)).select(root);
            var entryQuery = em.createQuery(query);
            entryQuery.setParameter(param, empl);
            entryQuery.getResultList().forEach(System.out::println);
            JpaUtil.commit();
        } catch (Exception ex) {
            JpaUtil.rollback();
            throw ex;
        }
    }

    private static Employee addPhones(Employee empl, String... phones) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            empl = em.merge(empl);
            for (var phone : phones) {
                empl.addPhone(phone);
            }

            JpaUtil.commit();
        } catch (Exception ex) {
            JpaUtil.rollback();
            throw ex;
        }
        return empl;
    }

    private static Employee assignToProject(Employee empl, Project project) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            empl = em.merge(empl);
            empl.assignToproject(project);
            JpaUtil.commit();
        } catch (Exception ex) {
            JpaUtil.rollback();
            throw ex;
        }
        return empl;
    }

    public static void main(String[] args) {
        try {

            JpaUtil.getEntityManagerFactory();
            var emplOnePerm = new PermanentEmployee("Max", "Schwarz", LocalDate.of(2000, 7, 13));
            emplOnePerm.setSalary(2000);
            var emplTwoTemp = new TemporaryEmployee("Sabrina", "Schwarz", LocalDate.of(1990, 5, 12));
            emplTwoTemp.setRenter("Dei Mama");
            emplTwoTemp.setHourlyRate(0.5);
            emplTwoTemp.setStartDate(LocalDate.of(2020, 1, 1));
            emplTwoTemp.setEndDate(LocalDate.of(2022, 12, 31));
            Employee emplOne = emplOnePerm;
            Employee emplTwo = emplTwoTemp;
            var entryOne = new LogbookEntry(("Analysis"), LocalDateTime.of(2022, 2, 1, 13, 0), LocalDateTime.of(2022, 2, 1, 15, 0));
            var entryTwo = new LogbookEntry(("Design"), LocalDateTime.of(2022, 2, 1, 16, 0), LocalDateTime.of(2022, 2, 1, 18, 0));
            var entryThree = new LogbookEntry(("Implementation"), LocalDateTime.of(2022, 2, 1, 19, 0), LocalDateTime.of(2022, 2, 1, 23, 0));
            emplOne.setAddress(new Address("4040", "Linz", "Hauptstraße 1"));
            emplTwo.setAddress(new Address("4040", "Linz", "Hauptstraße 2"));

            System.out.println("------------insertEmployees------------");
            emplOne = saveEntity(emplOne);
            emplTwo = saveEntity(emplTwo);
            listEmployees();

            System.out.println("------------updateEmployee------------");
            emplOne.setLastName("Testnachname");
            emplOne = updateEntity(emplOne.getId(), emplOne, Employee.class);
            listEmployees();

            System.out.println("------------addLogbookEntries------------");
            emplOne = addLogBookEntries(emplOne, entryOne, entryTwo);
            emplTwo = addLogBookEntries(emplTwo, entryThree);
            listEmployees();

            //System.out.println("------------removeEmployee------------");
            //deleteEmployee(emplTwo);
            //listEmployees();

            System.out.println("------------testFetchingStrategies------------");
            testFetchingStrategies();

            System.out.println("------------listEntriesofEmployee------------");
            listEntriesOfEmployee(emplOne);

            System.out.println("------------listEmployeesResigning in------------");
            listEmployeesResidingIn("4040");

            System.out.println("------------loadEmployeesWithEntries------------");
            loadEmployeesWithEntries();

            System.out.println("------------addPhones------------");
            addPhones(emplTwo, "+436644323567", "0727374756");

            System.out.println("------------assignToProject------------");
            emplOne = assignToProject(emplOne, new Project("SWT6"));
            listEmployees();
            JpaUtil.closeEntityManagerFactory();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
}
