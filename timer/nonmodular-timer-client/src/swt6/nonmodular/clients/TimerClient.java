package swt6.nonmodular.clients;

import swt6.nonmodular.beans.SimpleTimer;
import swt6.nonmodular.beans.TimerEvent;
import swt6.nonmodular.beans.TimerListener;

public class TimerClient {

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException exception) {}
    }

    public static void main(String[] args) {
        SimpleTimer timer = new SimpleTimer(10, 1000);
        timer.addTimerListener(event -> System.out.printf("timer expired: %d/%d%n", event.getTickCount(), event.getNoTicks()));
        timer.start();
        sleep(2000);
        timer.stop();
        sleep(2000);
        timer.start();
    }
}
