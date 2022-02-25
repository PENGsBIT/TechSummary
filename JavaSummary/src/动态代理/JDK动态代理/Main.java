package 动态代理.JDK动态代理;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) {
        // 指明一个类加载器，要操作class文件，怎么少得了类加载器呢
        ClassLoader classLoader = Main.class.getClassLoader();
        // 为代理对象指定要是实现哪些接口，这里我们要为UserServiceImpl这个目标对象创建动态代理，所以需要为代理对象指定实现UserService接口
        Class[] classes = new Class[]{UserService.class};
        // 初始化一个InvocationHandler，并初始化InvocationHandler中的目标对象
        InvocationHandler invocationHandler = new UserServiceInvocationHandler(new UserServiceImpl());
        // 创建动态代理
        UserService userService = (UserService) Proxy.newProxyInstance(classLoader, classes, invocationHandler);
        // 执行代理对象的方法，通过观察控制台的结果，判断我们是否对目标对象(UserServiceImpl)的方法进行了增强
        userService.insert();

    }
}
