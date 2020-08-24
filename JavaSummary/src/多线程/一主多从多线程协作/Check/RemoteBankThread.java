package 多线程.一主多从多线程协作.Check;/**
 * @program: javatest
 * @author: zpc
 * @create: 2019-08-18 21:13
 **/

import 多线程.一主多从多线程协作.Service.RemoteBankService;

/**
 * @Author: zpc
 * @Description:
 * @Create: 2019-08-18 21:13
 **/


public class RemoteBankThread extends BaseCheckThread{
    public RemoteBankThread(int uid) {
        super(uid);
    }

    @Override
    public Boolean call() throws Exception {
        return new RemoteBankService().checkCredit(uid);
    }
}
