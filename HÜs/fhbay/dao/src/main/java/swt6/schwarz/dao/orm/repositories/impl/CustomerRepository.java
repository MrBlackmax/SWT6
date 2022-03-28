package swt6.schwarz.dao.orm.repositories.impl;

import swt6.schwarz.dao.orm.domain.Article;
import swt6.schwarz.dao.orm.domain.Customer;
import swt6.schwarz.dao.orm.repositories.BaseRepository;
import swt6.schwarz.dao.orm.utils.DaoUtils;

import java.util.List;

public class CustomerRepository extends BaseRepository<Customer> {

    @Override
    public Customer update(Customer entity ) {
        return getEntityHandler().updateEntity(entity.getId(), entity, Customer.class);
    }

    public Customer findById(long customerId) {
        try {
            var query = getEm().createQuery("select c from Customer c where c.id = :id", Customer.class);
            query.setParameter("id", customerId);
            return query.getResultList().stream().findFirst().orElse(null);
        } catch (Exception ex) {
            DaoUtils.rollback();
            throw ex;
        }
    }
}
