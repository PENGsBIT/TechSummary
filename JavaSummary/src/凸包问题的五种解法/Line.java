package 凸包问题的五种解法;/**
 * @program: javatest
 * @author: zpc
 * @create: 2019-09-16 15:29
 **/

/**
 * @Author: zpc
 * @Description:
 * @Create: 2019-09-16 15:29
 **/
class Line {//线
    Point p1, p2;

    Line(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }

    public double getLength() {
        double dx = Math.abs(p1.x - p2.x);
        double dy = Math.abs(p1.y - p2.y);
        return Math.sqrt(dx * dx + dy * dy);
    }
}
