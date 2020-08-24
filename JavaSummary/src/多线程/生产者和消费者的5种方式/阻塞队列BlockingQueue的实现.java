package 多线程.生产者和消费者的5种方式;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 生产者和消费者，阻塞队列BlockingQueue的实现
 *
 */
class 阻塞队列BlockingQueue的实现 {
    private static Integer count = 0;
    //创建一个阻塞队列
    final BlockingQueue blockingQueue = new ArrayBlockingQueue<>(10);
    public static void main(String[] args) {
        阻塞队列BlockingQueue的实现 blockQueueTest = new 阻塞队列BlockingQueue的实现();
        new Thread(blockQueueTest.new Producer()).start();
        new Thread(blockQueueTest.new Consumer()).start();
        new Thread(blockQueueTest.new Producer()).start();
        new Thread(blockQueueTest.new Consumer()).start();
        new Thread(blockQueueTest.new Producer()).start();
        new Thread(blockQueueTest.new Consumer()).start();
        new Thread(blockQueueTest.new Producer()).start();
        new Thread(blockQueueTest.new Consumer()).start();
    }
    class Producer implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    blockingQueue.put(1);
                    count++;
                    System.out.println(Thread.currentThread().getName()
                            + "生产者生产，目前总共有" + count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
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
                    blockingQueue.take();
                    count--;
                    System.out.println(Thread.currentThread().getName()
                            + "消费者消费，目前总共有" + count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
