package swt6.schwarz.fhbay.shell;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.shell.jline.PromptProvider;

@SpringBootApplication
@EnableScheduling
public class FhBayShellApplication {
    public static void main(String[] args) {
        SpringApplication.run(FhBayShellApplication.class, args);
    }

    @Bean("promptProvider")
    public PromptProvider getPrompt() {
        return () ->
                new AttributedString("fhbay> ", AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN));
    }
}
