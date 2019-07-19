package 单例模式;

//在effective java（这本书真的很棒）中说道，最佳的单例实现模式就是枚举模式。利用枚举的特性，让JVM来帮我们保证线程安全和单一实例的问题。
//1默认枚举实例的创建是线程安全的
//2以往的单例实现了序列化接口,那么就再也不能保持单例的状态了.因为readObject()方法一直返回一个
//新的对象.使用radResolve()来避免此情况发生.枚举单例 对序列化有保证
//3采用反射来创建实例时.可通过AccessibleObject.setAccessible(),通过反射机制来调用私有
//构造器.那么枚举可以防止这种创建第二个实例的情况发生.
public enum EnumSingleton {
    INSTANCE;

    public EnumSingleton getInstance() {
        return INSTANCE;
    }
}

class run{
    public static void main(String[] args) {
        EnumSingleton.INSTANCE.getInstance();
    }
}
