package 单例模式;

//double checked locking模式
public class DCLSingleton {

    private volatile static DCLSingleton DCLSingleton;//将 instance 变量声明成 volatile 保证JVM 的即时编译器中不会指令重排序的优化

    private DCLSingleton() {};

    public static DCLSingleton getDCLSingleton() {
        if (DCLSingleton == null) {
            synchronized (DCLSingleton.class) {
//                同步块内还要再检验一次？因为可能会有多个线程一起进入同步块外的 if，
// 如果在同步块内不进行二次检验的话就会生成多个实例了。
                if (DCLSingleton == null) {
//                    instance = new 设计模式.单例模式.DCLSingleton()这句，这并非是一个原子操作，事实上在 JVM 中这句话大概做了下面 3 件事情:
//                    1.给 instance 分配内存
//                    2.调用 设计模式.单例模式.DCLSingleton 的构造函数来初始化成员变量
//                    3.将instance对象指向分配的内存空间（执行完这步 instance 就为非 null 了）。

                    DCLSingleton = new DCLSingleton();
                }
            }
        }
        return DCLSingleton;
    }
}

class DCLOptimizeSingleton {

    private static volatile DCLOptimizeSingleton instance = null;

    private DCLOptimizeSingleton() {}

    public static DCLOptimizeSingleton getInstance () {
        DCLOptimizeSingleton inst = instance;  // <<< 在这里创建临时变量
        if (inst == null) {
            synchronized (DCLOptimizeSingleton.class) {
                inst = instance;
                if (inst == null) {
                    inst = new DCLOptimizeSingleton();
                    instance = inst;
                }
            }
        }
        return inst;  // <<< 注意这里返回的是临时变量
    }
}
