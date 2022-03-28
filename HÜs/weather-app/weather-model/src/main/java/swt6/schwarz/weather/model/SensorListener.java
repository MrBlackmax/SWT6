package swt6.schwarz.weather.model;

import java.util.EventListener;

@FunctionalInterface
public interface SensorListener extends EventListener {
    public void valueMeasured(Measurement measurement);
}
