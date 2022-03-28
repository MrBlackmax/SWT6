package swt6.spring.basics.ioc.logic.javaConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import swt6.spring.basics.ioc.util.ConsoleLogger;
import swt6.spring.basics.ioc.util.FileLogger;
import swt6.spring.basics.ioc.util.Logger;

@Configuration
@ComponentScan()
public class WorkLogConfig {

    @Bean
    public Logger consoleLogger() {
        return new ConsoleLogger();
    }

    @Bean
    public Logger fileLogger() {
        return new FileLogger();
    }

    @Bean
    public WorkLogServiceImpl workLog() {
        return new WorkLogServiceImpl(consoleLogger());
    }
}
