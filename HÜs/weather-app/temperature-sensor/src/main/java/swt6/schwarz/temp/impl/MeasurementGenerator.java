package swt6.schwarz.temp.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import swt6.schwarz.weather.model.Measurement;
import swt6.schwarz.weather.model.MeasureUnit;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.TimerTask;

public class MeasurementGenerator extends TimerTask implements AutoCloseable {
    private static final Logger logger = LoggerFactory.getLogger(TemperatureSensor.class);

    private RandomMeasurementCallback callback;

    public MeasurementGenerator(RandomMeasurementCallback callback) {
        this.callback = callback;
    }

    @Override
    public void run() {
        logger.trace("MeasurementGenerator: Measurement started");
        var randomValue = TemperatureSensor.MIN_VALUE + (TemperatureSensor.MAX_VALUE-TemperatureSensor.MIN_VALUE) * new Random().nextDouble();
        var roundedValue = Math.round(randomValue * 100.0) / 100.0;
        var newMeasurement =  new Measurement(roundedValue, MeasureUnit.CELSIUS, LocalDateTime.now());
        logger.trace("MeasurementGenerator: Measurement finished");
        callback.call(newMeasurement);
    }

    @Override
    public void close() throws Exception {
        callback = null;
    }
}
