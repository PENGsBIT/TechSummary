package 多线程;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

//wait()和notify()方式
//在enqueue和dequeue方法内部，只有队列的大小等于上限（limit）或者下限（0）时，才调用notifyAll方法。如果队列的大小既不等于上限，也不等于下限，
// 任何线程调用enqueue或者dequeue方法时，都不会阻塞，都能够正常的往队列中添加或者移除元素。
public class BlockingQueue {

    private List queue = new LinkedList();
    private int limit = 10;

    public BlockingQueue(int limit) {
        this.limit = limit;
    }

    public synchronized void enqueue(Object item) throws InterruptedException {
        while (this.queue.size() == this.limit) {
            //进入阻塞状态（释放锁）
            wait();
        }
        this.queue.add(item);
        System.out.println("新加入的元素为:" + item);
        //通知dequeue线程可以取数据了
//        if (this.queue.size() == 0) {
//            notifyAll();
//        }
        notifyAll();
    }

    public synchronized Object dequeue() throws InterruptedException{
        while (this.queue.size() == 0) {
            wait();
        }
//        if (this.queue.size() == this.limit) {
//            notifyAll();
//        }
        notifyAll();
        return this.queue.remove(0);
    }

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue blockingQueue=new BlockingQueue(3);
        blockingQueue.enqueue(1);
        blockingQueue.enqueue(2);
        blockingQueue.enqueue(3);
        Thread t1=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    blockingQueue.enqueue(4);
                    blockingQueue.enqueue(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t1");
        t1.start();

        Thread t2=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("移除的元素为"+blockingQueue.dequeue());
                    System.out.println("移除的元素为"+blockingQueue.dequeue());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t2");
        try {
            TimeUnit.SECONDS.sleep(2);//睡眠2s才启动线程2
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();
    }

}

