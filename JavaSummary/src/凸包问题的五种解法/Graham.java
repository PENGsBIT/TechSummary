package 凸包问题的五种解法;/**
 * @program: javatest
 * @author: zpc
 * @create: 2019-09-16 16:09
 **/

import java.util.Scanner;

/**
 * @Author: zpc
 * @Description:
 * @Create: 2019-09-16 16:09
 **/


public class Graham {




        Point[] ch; //点集p的凸包
        Point[] p ; //给出的点集
        int n;
        int l;
        int len=0;

        public Graham(Point[] p,int n,int l){
            this.p=p;
            this.n=n;
            this.l=l;
            ch= new Point[n];
        }

        //小于0,说明向量p0p1的极角大于p0p2的极角
        public  double multiply(Point p1, Point p2, Point p0) {
            return ((p1.x - p0.x) * (p2.y - p0.y) - (p2.x - p0.x) * (p1.y - p0.y));
        }

        //求距离
        public  double distance(Point p1, Point p2) {
            return (Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y)
                    * (p1.y - p2.y)));
        }

        public void answer(){
            double sum = 0;
            for (int i = 0; i < len - 1; i++) {
                sum += distance(ch[i], ch[i + 1]);
            }
            if (len > 1) {
                sum += distance(ch[len - 1], ch[0]);
            }
            sum += 2 * l * Math.PI;
            System.out.println(Math.round(sum));
        }

        public  int Graham_scan() {
            int k = 0, top = 2;
            Point tmp;

            //找到最下且偏左的那个点
            for (int i = 1; i < n; i++)
                if ((p[i].y < p[k].y)
                        || ((p[i].y == p[k].y) && (p[i].x < p[k].x)))
                    k = i;
            //将这个点指定为pts[0],交换pts[0]与pts[k]
            tmp = p[0];
            p[0] = p[k];
            p[k] = tmp;

            //按极角从小到大,距离偏短进行排序
            for (int i = 1; i < n - 1; i++) {
                k = i;
                for (int j = i + 1; j < n; j++)
                    if ((multiply(p[j], p[k], p[0]) > 0)
                            || ((multiply(p[j], p[k], p[0]) == 0) && (distance(
                            p[0], p[j]) < distance(
                            p[0], p[k]))))
                        k = j; //k保存极角最小的那个点,或者相同距离原点最近
                tmp = p[i];
                p[i] = p[k];
                p[k] = tmp;
            }

            //前三个点先入栈
            ch[0] = p[0];
            ch[1] = p[1];
            ch[2] = p[2];

            //判断与其余所有点的关系
            for (int i = 3; i < n; i++) {
                //不满足向左转的关系,栈顶元素出栈
                while (top > 0 && multiply(p[i], ch[top], ch[top - 1]) >= 0)
                    top--;

                //当前点与栈内所有点满足向左关系,因此入栈.
                ch[++top] = p[i];
            }
            len=top+1;
            return len;
        }

        public static void main(String[] args)  {

            Scanner in=new Scanner(System.in);
            int n = in.nextInt();
            int l = in.nextInt();
            int x, y;
            Point[] p = new Point[n];
            for (int i = 0; i < n; i++) {
                x = in.nextInt();
                y = in.nextInt();
                p[i] = new Point(x, y);
            }

            Graham graham=new Graham(p,n,l);
            graham.Graham_scan();
            graham.answer();
        }


}
