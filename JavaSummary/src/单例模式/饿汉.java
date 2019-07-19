package 单例模式;/**
 * @program: javatest
 * @author: zpc
 * @create: 2019-07-19 22:49
 **/

/**
 * @Author: zpc
 * @Description: 单例
 * @Create: 2019-07-19 22:49
 **/


public class 饿汉 {

}

class Singleton {
    //当类第一次被加载到内存它就实例化了，所以这种实例的创建方式是线程安全的。
   private static final Singleton instance = new Singleton();

   private Singleton() {}

   public static Singleton getInstance() {
       return instance;
   }

}
