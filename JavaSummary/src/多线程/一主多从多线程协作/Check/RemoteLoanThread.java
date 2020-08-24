/**
 * @program: TechSummary
 * @author: zpc
 * @create: 2019-08-18 21:13
 **/
package 多线程.一主多从多线程协作.Check;
import 多线程.一主多从多线程协作.Service.RemoteLoanService;

/**
 * @Author: zpc
 * @Description:
 * @Create: 2019-08-18 21:13
 **/


public class RemoteLoanThread extends BaseCheckThread {
    public RemoteLoanThread(int uid) {
        super(uid);
    }

    @Override
    public Boolean call() throws Exception {
        return new RemoteLoanService().checkAuth(uid);
    }
}
