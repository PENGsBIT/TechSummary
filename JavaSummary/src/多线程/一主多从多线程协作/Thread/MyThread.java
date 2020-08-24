package 多线程.一主多从多线程协作.Thread;/**
 * @program: javatest
 * @author: zpc
 * @create: 2019-08-18 20:56
 **/

/**
 * @Author: zpc
 * @Description: 线程类，查看执行的线程是哪个线程。
 * @Create: 2019-08-18 20:56
 **/


public class MyThread implements Runnable{
    private Integer number;

    public MyThread(int number){
        this.number = number;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
            System.out.println("NOW! ThreadPoolExecutor - " + getNumber());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Integer getNumber() {
        return number;
    }
}
