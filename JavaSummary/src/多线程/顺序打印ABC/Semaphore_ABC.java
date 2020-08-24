package 多线程.顺序打印ABC;/**
 * @program: javatest
 * @author: zpc
 * @create: 2019-08-18 21:05
 **/

import 多线程.一主多从多线程协作.Thread.MyThreadFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: zpc
 * @Description: 使用Semaphore解决ABC问题
 * @Create: 2019-08-18 21:05
 **/


public class Semaphore_ABC {
    public static void main(String[] args) throws InterruptedException {
        final int poolSize = 3;
        final int maxPoolSize = poolSize * 4;

        ThreadPoolExecutor executor = new ThreadPoolExecutor(poolSize,maxPoolSize,
                1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(),new MyThreadFactory());

        AtomicInteger count = new AtomicInteger(1);

        Semaphore A_B = new Semaphore(1);
        Semaphore B_C = new Semaphore(1);
        Semaphore C_A = new Semaphore(1);

        A_B.acquire();
        B_C.acquire();

        executor.execute(() -> {
            try {
                while (true){
                    C_A.acquire();
                    System.out.print(count + ":");
                    System.out.print("A");
                    A_B.release();
                }
            } catch (InterruptedException ignored) {
            }
        });

        executor.execute(() -> {
            try {
                while (true){
                    A_B.acquire();
                    System.out.print("B");
                    B_C.release();
                }
            } catch (InterruptedException ignored) {
            }
        });

        executor.execute(() -> {
            try {
                while (true){
                    B_C.acquire();
                    System.out.println("C");
                    if(count.getAndIncrement() >= 10){
                        executor.shutdownNow();
                    }
                    C_A.release();
                }
            } catch (InterruptedException ignored) {
            }
        });
    }
}
