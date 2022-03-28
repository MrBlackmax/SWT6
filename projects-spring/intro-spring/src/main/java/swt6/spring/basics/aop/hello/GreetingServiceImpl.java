package swt6.spring.basics.aop.hello;

import swt6.spring.basics.GreetingService;

public class GreetingServiceImpl implements GreetingService {

    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void sayHello() {
        System.out.println(message);
    }
}
