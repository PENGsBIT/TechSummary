/**
 * @program: TechSummary
 * @author: zpc
 * @create: 2019-08-18 20:50
 **/
package 多线程.一主多从多线程协作;
import 多线程.一主多从多线程协作.Check.RemoteBankThread;
import 多线程.一主多从多线程协作.Check.RemoteLoanThread;
import 多线程.一主多从多线程协作.Check.RemotePassportThread;
import 多线程.一主多从多线程协作.Thread.MyThreadFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author: zpc
 * @Description: CountDownLatch-锁存器的使用
 * 完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待。
 * 主线程启动了多个子线程，主线程需要在子线程都结束后再做一些处理，也就是说，主线程必须知道所有子线程都结束的时候。
 * 实际例子：客户请求下单服务（OrderService），服务端会验证用户的身份（RemotePassportService），用户的银行信用（RemoteBankService），用户的贷款记录（RemoteLoanService）。为提高并发效率，
 * 要求三项服务验证工作同时进行，如其中任意一项验证失败，则立即返回失败，否则等待所有验证结束，成功返回。
 * @Create: 2019-08-18 20:50
 **/


public class 锁存器CountDownLatch加FureTask的使用 {
    private static ThreadPoolExecutor executor;

    public static void main(String[] args) {
        final int poolSize = 3;
        final int maxPoolSize = poolSize * 4;

        executor = new ThreadPoolExecutor(poolSize,maxPoolSize,
                1,TimeUnit.MINUTES, new LinkedBlockingQueue<>(),new MyThreadFactory());

        boolean result = checkAllStatus();
        System.out.println(result ? "验证成功" : "验证失败");

        executor.shutdownNow();
    }

    private static boolean checkAllStatus(){
        int taskNumber = 3;
        int uid = 1;
        //由于要实现异步转同步，那么肯定要用CountDownLatch，并且要设计两种状态：
        //三个线程都成功，返回成功
        //其中一个失败，终止其他两个线程，返回失败
        //那么设置countdown次数为3次即可：
        //成功就正常的，每个线程countdown一次
        //失败就countdown三次，强行结束

        CountDownLatch countDownLatch = new CountDownLatch(taskNumber);
        List<FutureTask<Boolean>> tasks = new ArrayList<>();
        tasks.add(new CheckFutureTask(new RemoteBankThread(uid),countDownLatch,taskNumber));
        tasks.add(new CheckFutureTask(new RemoteLoanThread(uid),countDownLatch,taskNumber));
        tasks.add(new CheckFutureTask(new RemotePassportThread(uid),countDownLatch,taskNumber));

        for(FutureTask<Boolean> task : tasks){
            executor.execute(task);
        }
        try {
            countDownLatch.await();

            for(FutureTask<Boolean> task : tasks){
                task.cancel(true);
            }

            for(FutureTask<Boolean> task : tasks){
                if(!task.get()){
                    return false;
                }
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
