package swt6.schwarz.clients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import swt6.schwarz.weather.factories.SensorFactory;
import swt6.schwarz.weather.model.SensorType;
import swt6.schwarz.weather.station.WeatherStation;
import java.time.LocalDateTime;

public class TemperatureClient implements AutoCloseable {

    private static final Logger logger = LoggerFactory.getLogger(TemperatureClient.class);

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {};
    }

    public static void main(String[] args) {
        var tempSensor = SensorFactory.getSensor(SensorType.TEMPERATURE, 1000);
        var station = new WeatherStation(tempSensor);
        station.start();
        var startDateTime = LocalDateTime.now();

        while(true) {
            sleep(1000);
            var endDateTime = LocalDateTime.now();
            var currMeasurement = station.getCurrentMeasurement();
            var averageSoFar = station.getAverage(startDateTime, endDateTime);
            logger.info(currMeasurement.toString());
            logger.info("Average so far: " + Math.round(averageSoFar) + "°");
        }
    }

    @Override
    public void close() throws Exception {

    }
}
