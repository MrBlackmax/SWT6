package swt6.schwarz.fhbay.shell;

import org.springframework.stereotype.Component;
import swt6.schwarz.fhbay.LogicFactory;
import swt6.schwarz.fhbay.logic.Logic;

@Component
public class LogicConnection {

    private static Logic fhBayLogic;

    public static Logic getLogic() {
        if (fhBayLogic == null) {
            fhBayLogic = LogicFactory.getLogic();
        }
        return fhBayLogic;
    }
}
