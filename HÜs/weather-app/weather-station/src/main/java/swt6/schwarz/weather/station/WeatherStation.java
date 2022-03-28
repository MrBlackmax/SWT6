package swt6.schwarz.weather.station;

import swt6.schwarz.weather.factories.SensorFactory;
import swt6.schwarz.weather.model.Measurement;
import swt6.schwarz.weather.model.Sensor;
import swt6.schwarz.weather.model.SensorType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WeatherStation implements AutoCloseable {
    private Sensor sensor;
    private List<Measurement> measurements;

    public WeatherStation(Sensor sensor) {
        measurements = new ArrayList<>();
        this.sensor = sensor;
    }

    public void start() {
        sensor.register(measurement -> measurements.add(measurement));
        sensor.start();
    }

    public void stop() {
        sensor.stop();
    }

    public double getAverage(LocalDateTime start, LocalDateTime end) {
        if (measurements.size() == 0) return  0.0;
        return measurements.stream().filter(m -> m.getTimeStamp().isAfter(start) && m.getTimeStamp().isBefore(end)).mapToDouble(m -> m.getValue()).average().orElse(0.0);
    }

    public Measurement getCurrentMeasurement() {
        if (measurements.size() == 0) return null;
        return measurements.get(measurements.size() - 1);
    }

    @Override
    public void close() throws Exception {
        stop();
        sensor = null;
        measurements.clear();
        measurements = null;
    }
}
