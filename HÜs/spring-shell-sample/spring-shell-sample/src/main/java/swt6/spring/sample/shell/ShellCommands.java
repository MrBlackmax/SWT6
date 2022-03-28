package swt6.spring.sample.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import swt6.spring.sample.beans.Calculator;

@ShellComponent
public class ShellCommands {

  @Autowired
  private Calculator calculator;

  @ShellMethod("Add two numbers")
  public String addNumbers(@ShellOption int op1, @ShellOption int op2) {
    return String.valueOf(calculator.add(op1, op2));
  }
}
