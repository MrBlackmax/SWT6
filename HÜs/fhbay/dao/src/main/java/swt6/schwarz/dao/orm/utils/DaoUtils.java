package swt6.schwarz.dao.orm.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DaoUtils {
    private static EntityManagerFactory emFactory;
    private static ThreadLocal<EntityManager> emThread = new ThreadLocal<>();

    public static synchronized EntityManagerFactory getEntityManagerFactory() {
        if (emFactory == null) {
            emFactory = Persistence.createEntityManagerFactory("FhBayUnit");
        }
        return emFactory;
    }

    public static synchronized EntityManager getEntityManager()  {
        if (emThread.get() == null) {
            emThread.set(getEntityManagerFactory().createEntityManager());
        }
        return emThread.get();
    }

    public static synchronized EntityManager getTransactedEntityManager() {
        var em = getEntityManager();
        var tx = em.getTransaction();
        tx.begin();
        System.out.println("DB: Transaction started");
        return em;
    }

    public static synchronized void commit() {
        var em = getEntityManager();
        var tx = em.getTransaction();

        if (tx.isActive()) {
            tx.commit();
            System.out.println("DB: Transaction committed");
        }
        closeEntityManager();
    }

    public static synchronized void rollback() {
        var em = getEntityManager();
        var tx = em.getTransaction();

        if (tx.isActive()) {
            tx.rollback();
            System.out.println("DB: Transaction rolled back");
        }
        closeEntityManager();
    }

    public  static synchronized void closeEntityManager() {
        if (emThread.get() != null) {
            emThread.get().close();
            emThread.set(null);
        }
    }

    public static synchronized  void closeEntityManagerFactory() {
        closeEntityManager();
        if (emFactory != null) {
            emFactory.close();
            emFactory = null;
        }
    }
}
