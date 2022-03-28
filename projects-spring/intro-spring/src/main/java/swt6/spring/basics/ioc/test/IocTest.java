package swt6.spring.basics.ioc.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import swt6.spring.basics.ioc.logic.WorkLogService;
import swt6.spring.basics.ioc.logic.factorybased.WorkLogServiceImpl;
import swt6.spring.basics.ioc.logic.javaConfig.WorkLogConfig;
import swt6.util.PrintUtil;

public class IocTest {

  private static void testSimple() {
    WorkLogServiceImpl workLog = new WorkLogServiceImpl();
    workLog.findAllEmployees();
    workLog.findEmployeeById(Long.parseLong("3"));
  }

  private static void testXmlConfig() {
    try(AbstractApplicationContext factory = new ClassPathXmlApplicationContext("swt6/spring/basics/ioc/test/applicationContext-xml-config.xml")) {
      System.out.println("###> worklog-injection");
      WorkLogService serviceTwo  = factory.getBean("workLog-constructor-injection", WorkLogService.class);
      serviceTwo.findAllEmployees();
      serviceTwo.findEmployeeById(3L);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private static void testAnnotationConfig() {
    try(AbstractApplicationContext factory = new ClassPathXmlApplicationContext("swt6/spring/basics/ioc/test/applicationContext-annotation.xml")) {
      System.out.println("###> worklog-injection");
      WorkLogService serviceTwo  = factory.getBean("workLog", WorkLogService.class);
      serviceTwo.findAllEmployees();
      serviceTwo.findEmployeeById(3L);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private static void testJavaConfig() {
    try(AbstractApplicationContext factory = new AnnotationConfigApplicationContext(WorkLogConfig.class)) {
      System.out.println("###> worklog-injection");
      WorkLogService serviceTwo  = factory.getBean("workLog", WorkLogService.class);
      serviceTwo.findAllEmployees();
      serviceTwo.findEmployeeById(3L);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static void main(String[] args) {
    PrintUtil.printTitle("testSimple", 60);
    testSimple();
    PrintUtil.printTitle("testXmlConfig", 60);
    testXmlConfig();
    PrintUtil.printTitle("testAnnotationConfig", 60);
    testAnnotationConfig();
    PrintUtil.printTitle("testJavaConfig", 60);
    testJavaConfig();
    PrintUtil.printSeparator(60);
  }
}
