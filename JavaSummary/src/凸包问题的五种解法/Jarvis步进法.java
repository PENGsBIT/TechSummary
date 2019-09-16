package 凸包问题的五种解法;/**
 * @program: javatest
 * @author: zpc
 * @create: 2019-09-16 15:32
 **/

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.abs;

/**
 * @Author: zpc
 * @Description:
 * @Create: 2019-09-16 15:32
 **/


public class Jarvis步进法 {
    //Jarvis步进法
    //时间复杂度：O(nH)。（其中 n 是点的总个数，H 是凸包上的点的个数） 
    //思路：
    //
    //纵坐标最小的那个点一定是凸包上的点，例如图上的 P0。
    //从 P0 开始，按逆时针的方向，逐个找凸包上的点，每前进一步找到一个点，所以叫作步进法。
    //怎么找下一个点呢？利用夹角。假设现在已经找到 {P0，P1，P2} 了，要找下一个点：
    // 剩下的点分别和 P2 组成向量，设这个向量与向量P1P2的夹角为 β 。当 β 最小时就是所要求的下一个点了，此处为 P3 。
    //
    //注意：
    //找第二个点 P1 时，因为已经找到的只有 P0 一个点，所以向量只能和水平线作夹角 α，当 α 最小时求得第二个点。
    //共线情况：如果直线 P2P3 上还有一个点 P4，即三个点共线，此时由向量P2P3 和向量P2P4 产生的两个 β 是相同的。
    // 我们应该把 P3、P4 都当做凸包上的点，并且把距离 P2 最远的那个点（即图中的P4）作为最后搜索到的点，继续找它的下一个连接点。

}


class JarvisMarch {
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private static int MAX_ANGLE = 4;
    private double currentMinAngle = 0;
    private List<Point> hullPointList;
    private List<Integer> indexList;
    private PointFactory pf;
    private Point[] ps;

    public Point[] getPs() {
        return ps;
    }

    private int firstIndex;

    public int getFirstIndex() {
        return firstIndex;
    }

    public JarvisMarch() {
        this(10);
    }

    public JarvisMarch(int count) {
        pf = PointFactory.getInstance(count);
        initialize();
    }

    public JarvisMarch(int[] x, int[] y) {
        pf = PointFactory.getInstance(x, y);
        initialize();
    }

    private void initialize() {
        hullPointList = new LinkedList<Point>();
        indexList = new LinkedList<Integer>();
        firstIndex = pf.getFirstIndex();
        ps = pf.getPoints();
        addToHull(firstIndex);
    }

    private void addToHull(int index) {
        indexList.add(index);
        hullPointList.add(ps[index]);
    }

    public int calculateHull() {
        for (int i = getNextIndex(firstIndex); i != firstIndex; i = getNextIndex(i)) {
            addToHull(i);
        }
        showHullPoints();
        return 0;
    }

    private void showHullPoints() {
        Iterator<Point> itPoint = hullPointList.iterator();
        Iterator<Integer> itIndex = indexList.iterator();
        Point p;
        int i;
        int index = 0;
        System.out.println("The hull points is: -> ");
        while (itPoint.hasNext()) {
            i = itIndex.next();
            p = itPoint.next();
            System.out.print(i + ":(" + p.getX() + "," + p.getY() + ")  ");
            index++;
            if (index % 10 == 0)
                System.out.println();
        }
        System.out.println();
        System.out.println("****************************************************************");
        System.out.println("The count of all hull points is " + index);
    }

    public int getNextIndex(int currentIndex) {
        double minAngle = MAX_ANGLE;
        double pseudoAngle;
        int minIndex = 0;
        for (int i = 0; i < ps.length; i++) {
            if (i != currentIndex) {
                pseudoAngle = getPseudoAngle(ps[i].getX() - ps[currentIndex].getX(),
                        ps[i].getY() - ps[currentIndex].getY());
                if (pseudoAngle >= currentMinAngle && pseudoAngle < minAngle) {
                    minAngle = pseudoAngle;
                    minIndex = i;
                } else if (pseudoAngle == minAngle){
                    if((abs(ps[i].getX() - ps[currentIndex].getX()) >
                            abs(ps[minIndex].getX() - ps[currentIndex].getX()))
                            || (abs(ps[i].getY() - ps[currentIndex].getY()) >
                            abs(ps[minIndex].getY() - ps[currentIndex].getY()))){
                        minIndex = i;
                    }
                }
            }

        }
        currentMinAngle = minAngle;
        return minIndex;
    }

    public double getPseudoAngle(double dx, double dy) {//计算极角
        if (dx > 0 && dy >= 0)
            return dy / (dx + dy);
        if (dx <= 0 && dy > 0)
            return 1 + (abs(dx) / (abs(dx) + dy));
        if (dx < 0 && dy <= 0)
            return 2 + (dy / (dx + dy));
        if (dx >= 0 && dy < 0)
            return 3 + (dx / (dx + abs(dy)));
        throw new Error("Impossible");
    }


    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        JarvisMarch j = new JarvisMarch(100000);
        Point[] points = j.getPs();
        int firstIndex = j.getFirstIndex();

        System.out.println("the first point is: " + firstIndex + ":(" +
                points[firstIndex].getX() + "," + points[firstIndex].getY() + ")");
        System.out.println("*****************************************************************");
        j.calculateHull();
        System.out.println("The total running time is " + (System.currentTimeMillis() - start) + " millis.");
    }
}


class PointFactory {
    /**
     * 单例模式，大批量产生Point，也可以手动产生Point
     */
    private Point[] points = null;
    private int newIndex;
    private int firstIndex = 0;

    public Point[] getPoints() {
        return points;
    }

    public int getFirstIndex() {
        return firstIndex;
    }

    public static PointFactory getInstance() {
        return new PointFactory();
    }

    public static PointFactory getInstance(int count) {
        return new PointFactory(count);
    }

    public static PointFactory getInstance(int[] x, int[] y) {
        return new PointFactory(x, y);
    }

    private PointFactory() {
        this(10);
    }

    private PointFactory(int count) {
        points = new Point[count];
        for (int i = 0; i < count; i++) {
            points[i] = new Point();
            newIndex = i;
            validatePoints();
        }
        firstIndex = getFirstPoint();
    }

    public PointFactory(int[] x, int[] y) {
        points = new Point[y.length];
        for (int i = 0; i < y.length; i++) {
            points[i] = new Point(x[i], y[i]);
        }
        firstIndex = getFirstPoint();
    }

    private void validatePoints() {
        for(int i = 0; i < newIndex; i++) {
            if(points[i].equals(points[newIndex])) {
                points[newIndex] = new Point();
                validatePoints();
            }
        }
    }

    public int getFirstPoint() {
        int minIndex = 0;
        for (int i = 1; i < points.length; i++) {
            if (points[i].getY() < points[minIndex].getY()) {
                minIndex = i;
            } else if ((points[i].getY() == points[minIndex].getY())
                    && (points[i].getX() < points[minIndex].getX())) {
                minIndex = i;
            }
        }
        return minIndex;
    }

}

