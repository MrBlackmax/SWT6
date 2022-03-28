package swt6.schwarz.temp.impl;

import swt6.schwarz.weather.model.Measurement;
@FunctionalInterface
public interface RandomMeasurementCallback {
    void call(Measurement measurement);
}
