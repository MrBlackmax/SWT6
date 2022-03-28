package swt6.schwarz.weather.factories;

import swt6.schwarz.temp.TemperatureSensorProvider;
import swt6.schwarz.weather.model.Sensor;
import swt6.schwarz.weather.model.SensorType;

public class SensorFactory implements AutoCloseable {

    public static Sensor getSensor(SensorType type, int interval) {

        switch (type) {
            case TEMPERATURE:
                return TemperatureSensorProvider.getTemperatureSensor(interval);
            case AIR_PRESSURE:
                return null; // for new implementations
            default:
                return null;
        }
    }

    @Override
    public void close() throws Exception {

    }
}
