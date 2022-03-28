package swt6.spring.worklog.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swt6.spring.worklog.dao.EmployeeRepository;
import swt6.spring.worklog.domain.Employee;

import java.util.List;
import java.util.Optional;

@Service("workLog")
@Transactional
public class WorklogServiceImpl2 implements WorkLogService{

    @Autowired
    private EmployeeRepository emplRepo;

    @Override
    public Employee syncEmployee(Employee employee) {
        return emplRepo.save(employee);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Employee> findEmployeeById(Long id) {
        return emplRepo.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Employee> findAllEmployees() {
        return emplRepo.findAll();
    }
}
