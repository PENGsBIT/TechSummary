//package 设计模式DesignPatterns.proxy;
//import net.sf.cglib.设计模式DesignPatterns.proxy.Enhancer;
//import net.sf.cglib.设计模式DesignPatterns.proxy.MethodInterceptor;
//import net.sf.cglib.设计模式DesignPatterns.proxy.MethodProxy;
//
///**
// * @program: TechSummary
// * @author: zpc
// * @create: 2019-07-10 22:10
// **/
////使用cglib需要引入cglib的jar包，如果你已经有spring-core的jar包，则无需引入，因为spring中包含了cglib。
//public class cglibProxy implements MethodInterceptor{
//    private Enhancer enhancer = new Enhancer();
//    @Override
//    /**
//     *
//     * @param o 是被代理对象
//     * @param method 调用方法的Method对象
//     * @param args 方法参数
//     * @param methodProxy
//     * @return cglib生成用来代替Method对象的一个对象，使用MethodProxy比调用JDK自身的Method直接执行方法效率会有提升
//     * @throws Throwable
//     */
//    public Object intercept(Object o, Method method, Object[] args,
//                            MethodProxy methodProxy) throws Throwable {
//        System.out.println("before " + methodProxy.getSuperName());
//        System.out.println(method.getName());
//        Object o1 = methodProxy.invokeSuper(o, args);
//        //Object o2 = method.invoke(o, args); 使用这种方式会发生死循环，因为方法会被拦截
//        System.out.println("after " + methodProxy.getSuperName());
//        return o1;
//    }
//
//    public  Object newProxyInstance(Class<?> c) {
//        //设置产生的代理对象的父类。
//        enhancer.setSuperclass(c);
//        //设置CallBack接口的实例
//        enhancer.setCallback(this);
//        //使用默认无参数的构造函数创建目标对象
//        return enhancer.create();
//    }
//}
