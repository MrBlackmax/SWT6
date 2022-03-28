package swt6.schwarz.dao.orm.repositories;

public interface Repository<T> {
    void open();
    void close();
    T add(T entity);
    T update(T entity);
    void remove (T entity);
}
