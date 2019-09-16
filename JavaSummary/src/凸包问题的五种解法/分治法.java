package 凸包问题的五种解法;/**
 * @program: javatest
 * @author: zpc
 * @create: 2019-09-16 15:30
 **/

import java.util.Arrays;
import java.util.Comparator;

/**
 * @Author: zpc
 * @Description:
 * 分治法
 * 时间复杂度：O(n㏒n)。
 * @Create: 2019-09-16 15:30
 **/


public class 分治法 {

    //分治法
    //时间复杂度：O(n㏒n)。 
    //思路：应用分治法思想，把一个大问题分成几个结构相同的子问题，把子问题再分成几个更小的子问题……。
    // 然后我们就能用递归的方法，分别求这些子问题的解。最后把每个子问题的解“组装”成原来大问题的解。 
    //步骤：
    //
    //把所有的点都放在二维坐标系里面。那么横坐标最小和最大的两个点 P1 和 Pn 一定是凸包上的点（为什么呢？用反证法很容易证明，这里不详讲）。
    // 直线 P1Pn 把点集分成了两部分，即 X 轴上面和下面两部分，分别叫做上包和下包。
    //对上包：求距离直线 P1Pn 最远的点，即下图中的点 Pmax 。
    //作直线 P1Pmax 、PnPmax，把直线 P1Pmax 左侧的点当成是上包，把直线 PnPmax 右侧的点也当成是上包。
    //重复步骤 2、3。
    //对下包也作类似操作。
    //然而怎么求距离某直线最远的点呢？我们还是用到解一中的公式： 
    //|x1 y1 1|
    //|x2 y2 1|=x1y2+x3y1+x2y3-x3y2-x2y1-x1y3
    //|x3 y3 1|            
    //设有一个点 P3 和直线 P1P2 。（坐标：p1(x1,y1)，p2(x2,y2)，p3(x3,y3)） 
    //对上式的结果取绝对值，绝对值越大，则距离直线越远。
    //注意：在步骤一，如果横坐标最小的点不止一个，那么这几个点都是凸包上的点，此时上包和下包的划分就有点不同了，需要注意。
        static Point[] point;
        static double[] s = new double[6];

        public static void main(String[] args) {
            int n = 6;
            point = new Point[n];
//        for (int i = 0; i < n; i++) {
//            int a = in.nextInt();
//            int b = in.nextInt();
//            point[i] = new Point(a, b);
//        }
            point[0] = new Point(1,3);
            point[1] = new Point(2,1);
            point[2] = new Point(3,5);
            point[3] = new Point(4,4);
            point[4] = new Point(5,2);
            point[5] = new Point(3,2);
            Arrays.sort(point,0, n, new Comparator<Point>() {
                @Override
                public int compare(Point o1, Point o2) {
                    if (o1.x - o2.x == 0) {
                        return (int) (o1.y - o2.y);
                    }
                    return (int) (o1.x - o2.x);
                }
            });
            System.out.println(point[0].x + "," + point[0].y);
            hull(1, n-1,point[0],point[0]);
        }

        private static void hull(int l,int r,Point p1,Point p2){
            int x=l;
            int i=l-1,j=r+1;
            /**
             * 找出距离直线p1-p2最远的点p3
             * */
            for (int k = l; k <= r; k++){
                if (s[x] - s[k] <= 0) {
                    x=k;
                }
            }
            Point p3 = point[x];
            /**
             * p1-p3左侧的点
             * */
            for (int k = l; k <= r; k++) {

                s[++i] = cross(point[k], p1, p3);
                if (s[i] > 0) {
                    Point temp = point[i];
                    point[i] = point[k];
                    point[k] = temp;
                } else {
                    i--;
                }
            }
            /**
             * 直线p3-p2右侧的点
             * */
            for (int k=r;k>=l;k--) {
                s[--j]=cross(point[k], p3, p2);
                if (s[j] > 0) {
                    Point temp = point[j];
                    point[j] = point[k];
                    point[k] = temp;
                } else {
                    j++;
                }
            }
            /**
             * 分治，并中序输出
             * */
            if (l <= i) {
                hull(l, i, p1, p3);
            }
            System.out.println(p3.x + "," + p3.y);
            if (j <= r) {
                hull(j, r, p3, p2);
            }
        }
        private static double cross (Point a, Point b, Point c) {
            return (b.x-a.x)*(c.y-a.y)-(b.y-a.y)*(c.x-a.x);
        }

}
