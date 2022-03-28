package swt6.schwarz.temp.impl;

import swt6.schwarz.weather.model.Measurement;
import swt6.schwarz.weather.model.Sensor;
import swt6.schwarz.weather.model.SensorListener;

import java.util.Timer;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class TemperatureSensor implements Sensor,AutoCloseable {
    public static double MIN_VALUE = -65.0;
    public static double MAX_VALUE = 125.0;

    private int measureInterval = 1000;
    private AtomicReference<Timer> sensorTimer = new AtomicReference<>(null);
    private CopyOnWriteArrayList<SensorListener> listeners = new CopyOnWriteArrayList<>();

    public TemperatureSensor() {
    }

    public TemperatureSensor(int measureInterval) {
        setMeasureInterval(measureInterval);
    }

    @Override
    public void start() {
        if (isRunning()) throw new IllegalStateException("Cannot start: Sensor is already running");
        var interval = this.getMeasureInterval();
        sensorTimer.set(new Timer());
        sensorTimer.get().scheduleAtFixedRate(new MeasurementGenerator(this::sendNewMeasurement),0, interval);
    }

    @Override
    public void stop() {
        if (sensorTimer.get() == null) throw new IllegalStateException("Cannot stop: Sensor is not running");
        sensorTimer.get().cancel();
    }

    public void sendNewMeasurement(Measurement measurement) {
        this.listeners.forEach(l -> l.valueMeasured(measurement));
    }

    private boolean isRunning() {
        return sensorTimer.get() != null;
    }

    public int getMeasureInterval() {
        return measureInterval;
    }

    public void setMeasureInterval(int measureInterval) {
        this.measureInterval = measureInterval;
    }

    public AtomicReference<Timer> getSensorTimer() {
        return sensorTimer;
    }

    public void setSensorTimer(AtomicReference<Timer> sensorTimer) {
        this.sensorTimer = sensorTimer;
    }

    public CopyOnWriteArrayList<SensorListener> getListeners() {
        return listeners;
    }

    public void setListeners(CopyOnWriteArrayList<SensorListener> listeners) {
        this.listeners = listeners;
    }

    @Override
    public void register(SensorListener listener) {
        this.listeners.add(listener);
    }

    @Override
    public void unregister(SensorListener listener) {
        this.listeners.remove(listener);
    }

    @Override
    public void close() throws Exception {
        stop();
        listeners.clear();
        listeners = null;
    }
}
