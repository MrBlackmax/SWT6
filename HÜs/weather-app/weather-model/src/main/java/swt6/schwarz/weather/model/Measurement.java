package swt6.schwarz.weather.model;

import java.time.LocalDateTime;

public class Measurement implements AutoCloseable{
    private double value;
    private MeasureUnit unit;
    private LocalDateTime timeStamp;

    public Measurement() {
    }

    public Measurement(double value, MeasureUnit unit, LocalDateTime timeStamp) {
        this.value = value;
        this.unit = unit;
        this.timeStamp = timeStamp;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public MeasureUnit getUnit() {
        return unit;
    }

    public void setUnit(MeasureUnit unit) {
        this.unit = unit;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "value=" + value +
                ", unit=" + unit +
                ", timeStamp=" + timeStamp +
                '}';
    }

    @Override
    public void close() throws Exception {
        value = 0.0;
        unit = null;
        timeStamp = null;
    }
}
