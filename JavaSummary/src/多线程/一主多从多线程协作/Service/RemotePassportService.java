package 多线程.一主多从多线程协作.Service;/**
 * @program: javatest
 * @author: zpc
 * @create: 2019-08-18 21:08
 **/

import java.util.Random;

/**
 * @Author: zpc
 * @Description: 服务端会验证用户的身份服务
 * @Create: 2019-08-18 21:08
 **/


public class RemotePassportService {
    public boolean checkAuth(int uid){
        boolean flag;

        System.out.println("黑名单 - 验证开始");
        try {
            Thread.sleep(3000);
            flag = new Random().nextBoolean();
        } catch (InterruptedException e) {
            System.out.println("黑名单 - 验证终止");
            return false;
        }

        if(flag){
            System.out.println("黑名单 - 验证成功");
            return true;
        }
        else {
            System.out.println("黑名单 - 验证失败");
            return false;
        }
    }
}
