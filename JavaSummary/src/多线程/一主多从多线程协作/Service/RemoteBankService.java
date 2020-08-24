package 多线程.一主多从多线程协作.Service;/**
 * @program: javatest
 * @author: zpc
 * @create: 2019-08-18 21:07
 **/

import java.util.Random;

/**
 * @Author: zpc
 * @Description: 用户的银行信用服务
 * @Create: 2019-08-18 21:07
 **/


public class RemoteBankService {
    public boolean checkCredit(int uid){
        boolean flag;

        System.out.println("银行信用 - 验证开始");
        try {
            Thread.sleep(5000);
            flag = new Random().nextBoolean();
        } catch (InterruptedException e) {
            System.out.println("银行信用 - 验证终止");
            return false;
        }

        if(flag){
            System.out.println("银行信用 - 验证成功");
            return true;
        }
        else {
            System.out.println("银行信用 - 验证失败");
            return false;
        }
    }
}
