package 设计模式DesignPatterns.proxy;

//import org.junit.Test;

/**
 * @program: TechSummary
 * @author: zpc
 * @create: 2019-07-10 21:42
 **/

public class ProxyTest {
    public static void main(String[] args) {

        Target target = new TargetImpl();
        staticProxy p = new staticProxy(target);
        String result =  p.execute();
        System.out.println(result);
    }
//    @Test
    public void testDynamicProxy (){
        Target target = new TargetImpl();
        //输出目标对象信息
        System.out.println(target.getClass());
        TargetImpl proxy =  (TargetImpl) new DynamicProxy(target).getProxyInstance();
        //输出代理对象信息
        System.out.println(proxy.getClass());
        //执行代理方法
        proxy.execute();
    }
//    @Test
//    public void testCglibProxy(){
//        //目标对象
//        UserDao target = new UserDao();
//        System.out.println(target.getClass());
//        //代理对象
//        UserDao 设计模式DesignPatterns.proxy = (UserDao) new ProxyFactory(target).getProxyInstance();
//        System.out.println(设计模式DesignPatterns.proxy.getClass());
//        //执行代理对象方法
//        设计模式DesignPatterns.proxy.save();
//    }
}
