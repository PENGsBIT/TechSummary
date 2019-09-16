package 凸包问题的五种解法;/**
 * @program: javatest
 * @author: zpc
 * @create: 2019-09-16 15:28
 **/

/**
 * @Author: zpc
 * @Description:
 *   穷举法（蛮力法）
 *  时间复杂度：O(n³）。
 * @Create: 2019-09-16 15:28
 **/


public class 穷举法 {
    //穷举法（蛮力法）
    //时间复杂度：O(n³）。
    //思路：两点确定一条直线，如果剩余的其它点都在这条直线的同一侧，则这两个点是凸包上的点，否则就不是。
    //将点集里面的所有点两两配对，组成 n(n-1)/2 条直线。
    //对于每条直线，再检查剩余的 (n-2) 个点是否在直线的同一侧。
    //如何判断一个点 p3 是在直线 p1p2 的左边还是右边呢？（坐标：p1(x1,y1)，p2(x2,y2)，p3(x3,y3)）
    //|x1 y1 1|
    //|x2 y2 1|=x1y2+x3y1+x2y3-x3y2-x2y1-x1y3
    //|x3 y3 1|
    //当上式结果为正时，p3在直线 p1p2 的左侧；当结果为负时，p3在直线 p1p2 的右边。
    //蛮力法解决凸包问题，返回点集合中凸多边形的点集合
    public static Point[] getConvexPoint(Point[] A) {
        Point[] result = new Point[A.length];
        int len = 0;  //用于计算最终返回结果中是凸包中点的个数
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A.length; j++) {
                if (j == i)     //除去选中作为确定直线的第一个点
                    continue;

                int[] judge = new int[A.length];   //存放点到直线距离所使用判断公式的结果

                for (int k = 0; k < A.length; k++) {
                    int a = A[j].y - A[i].y;
                    int b = A[i].x - A[j].x;
                    int c = (A[i].x) * (A[j].y) - (A[i].y) * (A[j].x);

                    judge[k] = a * (A[k].x) + b * (A[k].y) - c;  //根据公式计算具体判断结果
                }

                if (JudgeArray(judge)) {  // 如果点均在直线的一边,则相应的A[i]是凸包中的点
                    result[len++] = A[i];
                    break;
                }
            }
        }
        Point[] result1 = new Point[len];
        for (int m = 0; m < len; m++)
            result1[m] = result[m];
        return result1;
    }

    //判断数组中元素是否全部大于等于0或者小于等于0，如果是则返回true，否则返回false
    public static boolean JudgeArray(int[] Array) {
        boolean judge = false;
        int len1 = 0, len2 = 0;

        for (int i = 0; i < Array.length; i++) {
            if (Array[i] >= 0)
                len1++;
        }
        for (int j = 0; j < Array.length; j++) {
            if (Array[j] <= 0)
                len2++;
        }

        if (len1 == Array.length || len2 == Array.length)
            judge = true;
        return judge;
    }

    public static void main(String[] args) {
        Point[] A = new Point[8];
        A[0] = new Point(1, 0);
        A[1] = new Point(0, 1);
        A[2] = new Point(0, -1);
        A[3] = new Point(-1, 0);
        A[4] = new Point(2, 0);
        A[5] = new Point(0, 2);
        A[6] = new Point(0, -2);
        A[7] = new Point(-2, 0);

        Point[] result = getConvexPoint(A);
        System.out.println("集合A中满足凸包的点集为：");
        for (int i = 0; i < result.length; i++)
            System.out.println("(" + result[i].x + "," + result[i].y + ")");

    }
}
