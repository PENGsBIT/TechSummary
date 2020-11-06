package List.list带值的初始化;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class List的初始化 {
    public static void main(String[] args) {
        //使一个匿名的内部类的一个实例初始值设定项
        ArrayList<String> test1 = new ArrayList<String>() {{
            add("A");
            add("B");
            add("C");
        }};

        //(不过这样的话这个list的size就固定了，不能再add了，要注意。)
        List<String> test2 = Arrays.asList("xxx","yyy","zzz");

        //onebyone赋值
        List<String> test3 = new ArrayList();

        test3.add("xxx");

        test3.add("yyy");

        test3.add("zzz");
    }
}
