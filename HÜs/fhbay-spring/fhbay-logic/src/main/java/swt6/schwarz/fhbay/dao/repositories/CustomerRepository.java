package swt6.schwarz.fhbay.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swt6.schwarz.fhbay.dao.domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
