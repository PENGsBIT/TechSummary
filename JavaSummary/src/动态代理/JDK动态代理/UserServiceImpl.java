package 动态代理.JDK动态代理;

/**
 * @Program: test
 * @Author: zhoupengcheng03
 * @Package: PACKAGE_NAME
 * @ClassName: UserServiceImpl
 * @Description:
 * @Create: 2022-02-25 11:06
 **/
public class UserServiceImpl implements UserService {

    @Override
    public int insert() {
        System.out.println("insert");
        return 0;
    }

    @Override
    public String query() {
        System.out.println("query");
        return null;
    }
}
