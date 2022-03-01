package swt6.modular.beans;

public interface TimerProvider {
    double timerResolution();
    Timer getTimer(int interval, int noTicks);
}


