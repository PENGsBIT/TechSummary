package 多线程;

import java.util.concurrent.*;

public class MultiThread_Test {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyThread mt = new MyThread();
        mt.start();
        //#############################################
        MyRunnable mr=new MyRunnable();
       new Thread(mr).start();
       //##############################################
        ExecutorService es = Executors.newSingleThreadExecutor();

        // 自动在一个新的线程上启动 多线程.MyCallable，执行 call 方法
        Future<?> f = es.submit(new MyCallable());

        // 当前 main 线程阻塞，直至 future 得到值
        System.out.println(f.get());

        es.shutdown();
    }
}

class MyThread extends Thread {
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }
}

//   MyRunnable mr=new MyRunnable();
//       new Thread(mr).start();
class MyRunnable implements Runnable {
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }
}

class DemoCallable implements Callable<String>{
    //实现Callable接口通过FutureTask包装，可以获取到线程的处理结果，
    // future.get()方法获取返回值，如果线程还没执行完，则会阻塞。
    @Override
    public String call() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    public static void main(String[] args) throws Exception {
        //FutureTask实现了RunnableFuture，RunnableFuture则实现了Runnable和Future两个接口。
        // 因此构造Thread时，FutureTask还是被转型为Runnable使用。因此其本质还是实现Runnable接口。
        DemoCallable c = new DemoCallable();
        FutureTask<String> future = new FutureTask<>(c);
        Thread t = new Thread(future);
        t.start();
        String result = future.get(); //同步获取返回结果
        System.out.println(result);
    }
}

class tpool{
    public void  run(){
        ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(3,3,10,
            TimeUnit.SECONDS,new SynchronousQueue<>(),new ThreadPoolExecutor.DiscardOldestPolicy());
        try{
            threadPoolExecutor.execute(()-> System.out.println(Thread.currentThread().getName()));
        }finally {
            threadPoolExecutor.shutdown();
        }

    }


}


class MyCallable implements Callable<String> {
    public String call() {
        //System.out.println(Thread.currentThread().getName());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return Thread.currentThread().getName();
    }
}
