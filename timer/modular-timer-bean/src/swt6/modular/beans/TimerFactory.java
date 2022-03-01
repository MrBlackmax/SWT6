package swt6.modular.beans;


import swt6.modular.beans.impl.SimpleTimer;

public class TimerFactory {
    public static Timer getTimer(int interval, int noTicks) {
        return new SimpleTimer(noTicks, interval);
    }
}
