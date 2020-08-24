package 多线程.一主多从多线程协作.Thread;/**
 * @program: javatest
 * @author: zpc
 * @create: 2019-08-18 20:58
 **/

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: zpc
 * @Description: 线程工厂类，负责给线程取名。
 * @Create: 2019-08-18 20:58
 **/


public class MyThreadFactory  implements ThreadFactory {
    private AtomicInteger atomicInteger = new AtomicInteger(0);
    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r,"myThread - " + atomicInteger.incrementAndGet());
    }
}
