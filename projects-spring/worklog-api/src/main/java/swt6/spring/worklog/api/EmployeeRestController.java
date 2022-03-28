package swt6.spring.worklog.api;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.exceptions.EmployeeNotFoundException;
import swt6.spring.worklog.logic.WorkLogService;
import swt6.spring.worklog.api.EmployeeDto;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(value = "/worklog")
public class EmployeeRestController {

    @Autowired
    private WorkLogService workLog;

    @Autowired
    private ModelMapper modelMapper;

    private Logger logger = LoggerFactory.getLogger(EmployeeRestController.class);

    public EmployeeRestController() {
        logger.info("EmployeeRestController created");
    }

    @GetMapping(value = "hello", produces = MediaType.TEXT_PLAIN_VALUE)
    public String hello() {
        logger.info("EmployeeRestController.hello()");
        return "Hello from EmployeeRestController";
    }

    @GetMapping("/employees")
    public List<Employee> getEmployees() {
        var employees = this.workLog.findAllEmployees();
        Type listDtoType = new TypeToken<Collection<EmployeeDto>>().getType();
        return modelMapper.map(employees, listDtoType);
    }

    @GetMapping("/employees/{id}")
    public EmployeeDto getEmployeeById(@PathVariable("id") Long id) {
        var employee = this.workLog.findEmployeeById(id);
        if (!employee.isPresent()) throw new EmployeeNotFoundException(id);
        return modelMapper.map(employee.get(), EmployeeDto.class);
    }

}
