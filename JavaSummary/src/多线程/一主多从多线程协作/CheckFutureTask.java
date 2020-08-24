package 多线程.一主多从多线程协作;

import 多线程.一主多从多线程协作.Check.BaseCheckThread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;

/**
 * @Author: zpc
 * @Description: CheckFutureTask
 * @Create: 2019-08-18 21:19
 **/


public class CheckFutureTask extends FutureTask<Boolean> {

    private volatile CountDownLatch countDownLatch;

    private final int number;

    public CheckFutureTask(BaseCheckThread checkThread, CountDownLatch countDownLatch, int number) {
        super(checkThread);
        this.countDownLatch = countDownLatch;
        this.number = number;
    }

    @Override
    protected void done() {
        try {
            if(!get()){
                afterFail();
            }
        } catch (Exception e) {
            afterFail();
        } finally {
            countDownLatch.countDown();
        }
    }

    /**
     * 在失败后调用
     */
    private void afterFail(){
        for(int i = 0 ; i < number - 1 ; i++){
            countDownLatch.countDown();
        }
    }
}
