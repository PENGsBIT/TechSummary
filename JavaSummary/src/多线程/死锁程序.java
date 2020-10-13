package 多线程;/**
 * @program: javatest
 * @author: zpc
 * @create: 2019-09-06 22:28
 **/

/**
 * @Author: zpc
 * @Description: 死锁程序DEMO
 * @Create: 2019-09-06 22:28
 **/


public class 死锁程序 {
    private static final Object LOCK_A = new Object();
    private static final Object LOCK_B = new Object();

    private static class TaskA extends Thread {
        @Override
        public void run() {
            synchronized (LOCK_A) {
                System.out.println(Thread.currentThread() + "I hold the LOCK_A");
                synchronized (LOCK_B) {
                    System.out.println(Thread.currentThread() + "I get the LOCK_B");
                }
            }
        }
    }

    private static class TaskB extends Thread {
        @Override
        public void run() {
            synchronized (LOCK_B) {
                System.out.println(Thread.currentThread() + "I hold the LOCK_B");
                synchronized (LOCK_A) {
                    System.out.println(Thread.currentThread() + "I get the LOCK_A");
                }
            }
        }
    }


    public static void main(String[] args) {
        new TaskA().start();
        new TaskB().start();
    }

}

