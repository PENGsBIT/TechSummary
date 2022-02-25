package 动态代理.JDK动态代理; /**
 * @Program: test
 * @Author: zhoupengcheng03
 * @Package: PACKAGE_NAME
 * @ClassName: UserServiceInvocationHandler
 * @Description:
 * @Create: 2022-02-25 11:06
 **/
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UserServiceInvocationHandler implements InvocationHandler {

    // 持有目标对象
    private Object target;

    public UserServiceInvocationHandler(Object target){
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("invocation handler");
        // 通过反射调用目标对象的方法
        return method.invoke(target,args);
    }
}

