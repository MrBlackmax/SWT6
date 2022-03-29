package swt6.schwarz.fhbay;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import swt6.schwarz.fhbay.logic.Logic;
import swt6.schwarz.fhbay.util.DbSeeder;

public class LogicFactory {
    public static Logic getLogic() {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("swt6/schwarz/fhbay/applicationContext-jpa.xml");
        var fhbayLogic = context.getBean("fhbay", Logic.class);
        new DbSeeder(fhbayLogic).seedDB();
        return fhbayLogic;
    }
}
