package 多线程.一主多从多线程协作.Check;/**
 * @program: javatest
 * @author: zpc
 * @create: 2019-08-18 21:13
 **/

import 多线程.一主多从多线程协作.Service.RemotePassportService;

/**
 * @Author: zpc
 * @Description:
 * @Create: 2019-08-18 21:13
 **/


public class RemotePassportThread extends BaseCheckThread {
    public RemotePassportThread(int uid) {
        super(uid);
    }

    @Override
    public Boolean call() throws Exception {
        return new RemotePassportService().checkAuth(uid);
    }
}
