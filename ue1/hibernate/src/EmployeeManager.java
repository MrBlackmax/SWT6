package swt6.orm.hibernate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.format.DateTimeFormatter;

public class EmployeeManager {

  static String promptFor(BufferedReader in, String p) {
    System.out.print(p + "> ");
    System.out.flush();
    try {
      return in.readLine();
    }
    catch (Exception e) {
      return promptFor(in, p);
    }
  }

  public static void main(String[] args) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    String availCmds = "commands: quit, insert";
    
    System.out.println("Hibernate Employee Admin");
    System.out.println(availCmds);
    String userCmd = promptFor(in, "");

    try {

      while (!userCmd.equals("quit")) {

        switch (userCmd) {
          
        case "insert":
          break;

        default:
          System.out.println("ERROR: invalid command");
          break;
        }

        System.out.println(availCmds);
        userCmd = promptFor(in, "");
      } // while

    } // try
    catch (Exception ex) {
      ex.printStackTrace();
    } // catch
  }
}
