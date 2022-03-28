package swt6.schwarz.dao.orm.utils;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.ArrayList;

public class EntityHandler {

    private EntityManager em;

    public EntityHandler(EntityManager em) {
        this.em = em;
    }

    public <T> T saveEntity(T entity) {
        try {
            entity = em.merge(entity);
        } catch (Exception ex) {
            DaoUtils.rollback();
            throw ex;
        }
        return entity;
    }

    public  <T> T updateEntity(Serializable id, T entity, Class<T> entityType) {
        T storedEntity = null;
        try {

            if (id == null) {
                throw new RuntimeException("Entity doe not exist");
            }

            storedEntity = em.find(entityType, id);
            if (storedEntity == null) {
                throw new RuntimeException("Entity does not exist");
            }

            storedEntity = em.merge(entity);
        } catch (Exception ex) {
            DaoUtils.rollback();
            throw ex;
        }
        return storedEntity;
    }

    public <T> void deleteEntity(T entity) {
        try {
            entity = em.merge(entity);
            em.remove(entity);
        } catch (Exception ex) {
            DaoUtils.rollback();
            throw ex;
        }
    }
}
