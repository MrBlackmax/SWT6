package swt6.orm.swt6.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaUtil {
    private static EntityManagerFactory emFactory;
    private static ThreadLocal<EntityManager> emThread = new ThreadLocal<>();

    public static synchronized EntityManagerFactory getEntityManagerFactory() {
        if (emFactory == null) {
            emFactory = Persistence.createEntityManagerFactory("WorkLogPU");
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
        return em;
    }

    public static synchronized void commit() {
        var em = getEntityManager();
        var tx = em.getTransaction();

        if (tx.isActive()) {
            tx.commit();
        }
        closeEntityManager();
    }

    public static synchronized void rollback() {
        var em = getEntityManager();
        var tx = em.getTransaction();

        if (tx.isActive()) {
            tx.rollback();
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
