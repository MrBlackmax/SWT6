package swt6.spring.basics.ioc.logic.annotationconfig;

import jdk.jfr.Name;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import swt6.spring.basics.ioc.domain.Employee;
import swt6.spring.basics.ioc.logic.WorkLogService;
import swt6.spring.basics.ioc.util.Log;
import swt6.spring.basics.ioc.util.Logger;
import swt6.spring.basics.ioc.util.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.util.*;

@Service
public class WorkLogServiceImpl implements WorkLogService {
    private Map<Long, Employee> employees = new HashMap<Long, Employee>();

    //CDI
    //@Name("consoleLogger")

    //@Autowired
    //@Qualifier("consoleLogger")
    @Inject
    //@Log(Log.Type.FILE)
    private Logger logger;

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    private void initLogger() {
        Properties props = new Properties();

        try {
            ClassLoader cl = this.getClass().getClassLoader();
            props.load(cl.getResourceAsStream(
                    "swt6/spring/basics/ioc/logic/factorybased/worklog.properties"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        var loggerType = props.getProperty("loggerType");
        logger = LoggerFactory.getLogger(loggerType);
    }

    private void init() {
        employees.put(1L, new Employee(1L, "Bill", "Gates"));
        employees.put(2L, new Employee(2L, "James", "Goslin"));
        employees.put(3L, new Employee(3L, "Bjarne", "Stroustrup"));
    }

    public WorkLogServiceImpl() {
        initLogger();
        init();
    }

    public WorkLogServiceImpl(Logger logger) {
        init();
        this.logger = logger;
    }

    public Employee findEmployeeById(Long id) {
        var employee = employees.get(id);
        logger.log(String.format("findEmployeeById(%d) --> %s", id, (employee != null) ? employee.toString() : "null"));
        return employee;
    }

    public List<Employee> findAllEmployees() {
        logger.log("findAllEmployees()");
        return new ArrayList<Employee>(employees.values());
    }
}
