package 多线程.生产者和消费者的5种方式;

import java.util.concurrent.Semaphore;

/**
 * 生产者和消费者，信号量Semaphore的实现
 *
 */
public class 信号量Semaphore的实现 {
    private static Integer count = 0;
    //创建三个信号量
    final Semaphore notFull = new Semaphore(10);
    final Semaphore notEmpty = new Semaphore(0);
    final Semaphore mutex = new Semaphore(1);
    public static void main(String[] args) {
        信号量Semaphore的实现 test4 = new 信号量Semaphore的实现();
        new Thread(test4.new Producer()).start();
        new Thread(test4.new Consumer()).start();
        new Thread(test4.new Producer()).start();
        new Thread(test4.new Consumer()).start();
        new Thread(test4.new Producer()).start();
        new Thread(test4.new Consumer()).start();
        new Thread(test4.new Producer()).start();
        new Thread(test4.new Consumer()).start();
    }
    class Producer implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    notFull.acquire();
                    mutex.acquire();
                    count++;
                    System.out.println(Thread.currentThread().getName()
                        + "生产者生产，目前总共有" + count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    mutex.release();
                    notEmpty.release();
                }
            }
        }
    }
    class Consumer implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                try {
                    notEmpty.acquire();
                    mutex.acquire();
                    count--;
                    System.out.println(Thread.currentThread().getName()
                        + "消费者消费，目前总共有" + count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    mutex.release();
                    notFull.release();
                }
            }
        }
    }
}
