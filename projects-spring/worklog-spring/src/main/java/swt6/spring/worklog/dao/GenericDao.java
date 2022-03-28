package swt6.spring.worklog.dao;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GenericDao <T>{
    Optional<T> findById (Long id);
    List<T> findAll();
    T merge(T entity);
    void insert(T entity);
}
