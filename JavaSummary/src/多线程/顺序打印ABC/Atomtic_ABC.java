package 多线程.顺序打印ABC;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;


public class Atomtic_ABC {

    private AtomicInteger ai = new AtomicInteger(0);
    private static final int MAX_SYC_VALUE = 3 * 10;

    private class RunnableA implements Runnable {
        public void run() {
            while (ai.get() < MAX_SYC_VALUE-1) {
                if (ai.get() % 3 == 0) {
                    System.out.print("A");
                    ai.getAndIncrement();
                }
            }

        }
    }

    private class RunnableB implements Runnable {
        public void run() {
            while (ai.get() < MAX_SYC_VALUE) {
                if (ai.get() % 3 == 1) {
                    System.out.print("B");
                    ai.getAndIncrement();
                }
            }

        }
    }

    private class RunnableC implements Runnable {
        public void run() {
            while (ai.get() < MAX_SYC_VALUE) {
                if (ai.get() % 3 == 2) {
                    System.out.println("C");
                    ai.getAndIncrement();
                }
            }

        }
    }


    public static void main(String[] args) {
        Atomtic_ABC atomic_ABC = new Atomtic_ABC();
        ExecutorService service = Executors.newFixedThreadPool(3);

        service.execute(atomic_ABC.new RunnableA());
        service.execute(atomic_ABC.new RunnableB());
        service.execute(atomic_ABC.new RunnableC());

        service.shutdown();
    }
}
