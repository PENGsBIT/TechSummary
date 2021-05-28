package 设计模式DesignPatterns.proxy;

/**
 * @program: TechSummary
 * @author: zpc
 * @create: 2019-07-10 21:39
 **/

public class TargetImpl implements Target {
    @Override
    public String execute() {
        System.out.println("TargetImpl execute！");
        return "execute";
    }
}
