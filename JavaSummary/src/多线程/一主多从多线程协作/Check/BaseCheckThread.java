package 多线程.一主多从多线程协作.Check;/**
 * @program: javatest
 * @author: zpc
 * @create: 2019-08-18 21:11
 **/

import java.util.concurrent.Callable;

/**
 * @Author: zpc
 * @Description: 检查服务是否成功
 * @Create: 2019-08-18 21:11
 **/


public abstract class BaseCheckThread implements Callable<Boolean> {

    protected final int uid;

    public BaseCheckThread(int uid){
        this.uid = uid;
    }

    public int getUid() {
        return uid;
    }
}
