package swt6.schwarz.weather.model;

public interface Sensor {
    void start();
    void stop();
    void register(SensorListener listener);
    void unregister(SensorListener listener);
}
