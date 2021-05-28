package 设计模式DesignPatterns.proxy;

/**
 * @program: TechSummary
 * @author: zpc
 * @create: 2019-07-10 21:41
 **/

public class staticProxy implements Target {
    private Target target;

    public staticProxy(Target target) {
        this.target = target;
    }

    @Override
    public String execute() {
        System.out.println("perProcess");
        String result = this.target.execute();
        System.out.println("postProcess");
        return result;
    }
}
