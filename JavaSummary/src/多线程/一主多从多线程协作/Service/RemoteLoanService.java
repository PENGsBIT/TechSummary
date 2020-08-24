package 多线程.一主多从多线程协作.Service;/**
 * @program: javatest
 * @author: zpc
 * @create: 2019-08-18 20:52
 **/

import java.util.Random;

/**
 * @Author: zpc
 * @Description: 用户的贷款记录服务
 * @Create: 2019-08-18 20:52
 **/


public class RemoteLoanService {
    public boolean checkAuth(int uid){
        boolean flag;

        System.out.println("不良贷款 - 验证开始");
        try {
            Thread.sleep(1000);
//             这里让时间最短的直接失败，方便查看测试结果
             flag = new Random().nextBoolean();
//            flag = false;
        } catch (InterruptedException e) {
            System.out.println("不良贷款 - 验证终止");
            return false;
        }

        if(flag){
            System.out.println("不良贷款 - 验证成功");
            return true;
        }
        else {
            System.out.println("不良贷款 - 验证失败");
            return false;
        }
    }
}
