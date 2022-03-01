package swt6.modular.clients;

import swt6.modular.beans.Timer;
import swt6.modular.beans.TimerFactory;
import swt6.modular.beans.TimerProvider;

import java.util.ServiceLoader;

public class TimerClient {

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException exception) {}
    }

    private static void testTimer() {
        Timer timer = TimerFactory.getTimer(500, 10);
        timer.addTimerListener(event -> System.out.printf("timer expired: %d/%d%n", event.getTickCount(), event.getNoTicks()));
        timer.start();
        sleep(2000);
        timer.stop();
        sleep(2000);
        timer.start();
    }

    private static Timer getBestTimer(int interval, int noTicks) {
        ServiceLoader<TimerProvider> serviceLoader = ServiceLoader.load(TimerProvider.class);
        double minResolution = Double.MAX_VALUE;
        TimerProvider minProvider = null;
        for(TimerProvider provider : serviceLoader) {
            if (provider.timerResolution() < minResolution) {
                minProvider = provider;
                minResolution = minProvider.timerResolution();
            }
        }
        return minProvider == null ? null : minProvider.getTimer(interval, noTicks);
    }

    private static void testTimerProvider() {
        Timer timer = getBestTimer(500, 10);
        if (timer == null) return;
        timer.addTimerListener(event -> System.out.printf("timer expired: %d/%d%n", event.getTickCount(), event.getNoTicks()));
        timer.start();
        sleep(2000);
        timer.stop();
        sleep(2000);
        timer.start();
    }

    private static void testReflection()  {
        Timer timer = getBestTimer(100,10);
        if (timer == null) {
            return;
        }
        try {
            System.out.printf("Fields of %s object %n", timer.getClass().getName());
            var fields = timer.getClass().getDeclaredFields();
            for (var field : fields) {
                field.setAccessible(true);
                Object value = field.get(timer);
                System.out.printf("%s -> %s%n", field.getName(), value);
            }
        } catch (IllegalAccessException exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("============== testTimer ==============");
        testTimer();
        System.out.println("============== testTimerProvider ==============");
        testTimerProvider();
        System.out.println("============== testReflection ==============");
        testReflection();
    }
}
