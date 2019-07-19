package 单例模式;/**
 * @program: javatest
 * @author: zpc
 * @create: 2019-07-19 22:50
 **/

/**
 * @Author: zpc
 * @Description: 单例
 * @Create: 2019-07-19 22:50
 **/


public class 懒汉 {
}

class Sinleton{
    private static Sinleton sinleton;
    Sinleton(){
    }

    public static synchronized Sinleton getInstance() {
        if (sinleton == null) {
            return new Sinleton();
        }
        return sinleton;
    }
}
