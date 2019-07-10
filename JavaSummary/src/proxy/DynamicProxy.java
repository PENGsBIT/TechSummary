package proxy;

import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.InvokeHandler;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.ResponseHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @program: TechSummary
 * @author: zpc
 * @create: 2019-07-10 21:47
 **/

public class DynamicProxy {
    private Target target;

    public DynamicProxy(Target target) {
        this.target = target;
    }

    // 为目标对象生成代理对象
    public Object getProxyInstance() {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("method:" + method.getName());
                        System.out.println("args:" + args[0].getClass().getName());
                        System.out.println("Before invoke method...");
                        Object object = method.invoke(target, args);
                        System.out.println("After invoke method...");
                        return object;
                    }
                });
    }
}
