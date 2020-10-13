package 多线程.辅助类CountDownLatch和CyclicBarrier和Semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

//Semaphore可以控同时访问的线程个数，通过 acquire() 获取一个许可，如果没有就等待，
//而 release() 释放一个许可。它还有 tryAcquire 和 acquireUninterruptibly 方法，可以根据自己的需要选择
public class SemaphoreImpl  {

    private Semaphore semaphore;// fS号量
    private int user;// 第几个用户

    class MyTask implements Runnable {
        Semaphore semaphore;
        int user;
        @Override
        public void run() {
            try {
                semaphore.acquire();
                System.out.println( "user:"+user+",开始排队" );
                System.out.println("semaphore剩余的容量"+semaphore.availablePermits());
                Thread.sleep((long)Math.random() * 50000);
                System.out.println("user:"+user + "排队结束，准备离开 ");
                Thread.sleep((long)Math.random() * 1000);
                System.out.println(user + "已经离开，下一个进场");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        public MyTask(Semaphore semaphore, int user) {
            this.semaphore = semaphore;
            this.user = user;
        }
    }
    private void execute(){

        final Semaphore s = new Semaphore(2);

        ExecutorService threadPool = Executors.newCachedThreadPool();

        for (int i = 0; i < 20; i++) {
            threadPool.execute(new MyTask(s, (i +1))); }
        threadPool.shutdown();
    }

    public static void main (String [ ] args) {
        SemaphoreImpl semaphoreImpl = new SemaphoreImpl();
        semaphoreImpl.execute();
    }

}


