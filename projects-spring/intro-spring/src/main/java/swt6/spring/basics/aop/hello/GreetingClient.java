package swt6.spring.basics.aop.hello;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import swt6.spring.basics.GreetingService;

public class GreetingClient {
    public static void main(String[] args) {
        try(AbstractApplicationContext factory = new ClassPathXmlApplicationContext("swt6/spring/basics/hello/greetingService.xml")) {
            GreetingService bean = (GreetingService) factory.getBean("greetingService");
            bean.sayHello();
            GreetingService bean2 = factory.getBean(GreetingService.class);
            bean2.sayHello();
            System.out.println(bean.equals(bean2));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
