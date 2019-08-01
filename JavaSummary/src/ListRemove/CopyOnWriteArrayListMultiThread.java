package ListRemove;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListMultiThread {
    //CopyOnWriteArrayList也是一个线程安全的ArrayList，其实现原理在于，每次add,remove等所有的操作都是重新创建一个新的数组，再把引用指向新的数组
    static List<String> list = new CopyOnWriteArrayList<>();
    //防止一个线程修改了list的modCount导致另外一个线程迭代时modCount与该迭代器的expectedModCount不相等。
    public static void main(String[] args) {
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        System.out.println("when process start list is: "+list);
        new Thread(() -> {
            Iterator<String> iterator = list.iterator();

            while (iterator.hasNext()) {
                System.out.println(Thread.currentThread().getName()
                    + ":" + iterator.next());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            System.out.println("when thread"+ Thread.currentThread().getName()+"check list modCount in copyonwrite is correct");
            System.out.println("when thread"+ Thread.currentThread().getName()+"end list is"+list);
        }).start();

        new Thread(()-> {
            Iterator<String> iterator = list.iterator();
            while (iterator.hasNext()) {
                String element = iterator.next();
                System.out.println(Thread.currentThread().getName()
                    + ":" + element);
                if (element.equals("c")) {
                    list.remove(element);
                }
            }
            System.out.println("when thread"+ Thread.currentThread().getName()+"end list is"+list);
        }).start();


    }
}
