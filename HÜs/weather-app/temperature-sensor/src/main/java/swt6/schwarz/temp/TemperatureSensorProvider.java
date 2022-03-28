package swt6.schwarz.temp;

import swt6.schwarz.temp.impl.TemperatureSensor;
import swt6.schwarz.weather.model.Sensor;

public class TemperatureSensorProvider {
    public static Sensor getTemperatureSensor(int interval) {
        return new TemperatureSensor(interval);
    }
}
