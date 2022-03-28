package swt6.schwarz.dao.orm.repositories;

import swt6.schwarz.dao.orm.utils.DaoUtils;
import swt6.schwarz.dao.orm.utils.EntityHandler;

import javax.persistence.EntityManager;

public abstract class BaseRepository <T> implements Repository<T>{
    EntityHandler entityHandler;
    EntityManager em;

    public BaseRepository() {
    }

    public EntityHandler getEntityHandler() {
        return entityHandler;
    }

    public void setEntityHandler(EntityHandler entityHandler) {
        this.entityHandler = entityHandler;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public T add(T entity) {
        return entityHandler.saveEntity(entity);
    }

    @Override
    public void remove(T entity) {
        entityHandler.deleteEntity(entity);
    }

    @Override
    public void open() {
        em = DaoUtils.getTransactedEntityManager();
        entityHandler = new EntityHandler(em);
    }

    @Override
    public void close() {
        DaoUtils.commit();
    }
}
