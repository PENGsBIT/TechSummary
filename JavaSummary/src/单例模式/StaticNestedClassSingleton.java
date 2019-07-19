package 单例模式;

//    静态内部类 static nested class
//    这种方法也是《Effective Java》上所推荐的。
class StaticNestedClassSingleton {
//    外部类加载时并不需要立即加载内部类，内部类不被加载则不去初始化INSTANCE，故而不占内存。
// 即当SingleTon第一次被加载时，并不需要去加载SingleTonHoler，只有当getInstance()方法第一次被调用时，
// 才会去初始化INSTANCE,第一次调用getInstance()方法会导致虚拟机加载SingleTonHoler类，
// 这种方法不仅能确保线程安全，也能保证单例的唯一性，同时也延迟了单例的实例化。

    public StaticNestedClassSingleton() {
    }

    public static synchronized StaticNestedClassSingleton getInstance() {
        return SinletonHolder.instance;
    }

    private static class SinletonHolder {
        private static final StaticNestedClassSingleton instance=new StaticNestedClassSingleton();
    }


}
