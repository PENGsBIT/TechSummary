package 多线程.辅助类CountDownLatch和CyclicBarrier和Semaphore;

//一组线程等待至某个状态之后再全部同时执行。它主要的方法就是一个：await()。
// await() 方法被调用一次，计数便会减少1，并阻塞住当前线程。当计数减至0时，阻塞解除，
// 所有在此 CyclicBarrier 上面阻塞的线程开始运行。在这之后，如果再次调用 await() 方法，
// 计数就又会变成 N-1，新一轮重新开始，这便是 Cyclic 的含义所在。

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierImpl {
    //用来挂起当前线程，直至所有线程都到达barrier状态再同时执行后续任务；

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(5, new Runnable() {
            //栅栏动作，在计数器为0的时候执行
            @Override
            public void run() {
                System.out.println("我们都准备好了.");
            }
        });

        ExecutorService es = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            es.execute(new Roommate(barrier));
        }
    }
}

class Roommate implements Runnable {
    private CyclicBarrier barrier;
    private static int Count = 1;
    private int id;

    public Roommate(CyclicBarrier barrier) {
        this.barrier = barrier;
        this.id = Count++;
    }

    @Override
    public void run() {
        System.out.println(id + " : 我到了");
        try {
            Thread.sleep(((long) (Math.random() * 10000)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            //通知barrier，已经完成动作，在等待
            barrier.await();
            System.out.println("Id " + id + " : 点菜吧!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

    }
}
