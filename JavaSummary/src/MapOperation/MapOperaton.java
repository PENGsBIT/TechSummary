package MapOperation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapOperaton {
    public static void main(String[] args) {
        Map<String, List<String>> map = new HashMap<>();
        //*****computeIfAbsent*****
        //如果我们要放一个元素进去，很多人会这么写：
        //这个方法有两个参数，Key 和一个根据 Key 来产生 Value 的 Function；然后返回一个 Value。
        //这个方法会检查 Map 中的 Key，如果发现 Key 不存在或者对应的值是 null，则调用 Function 来产生一个值，然后将其放入 Map，最后返回这个值；否则的话返回 Map 已经存在的值。
        List<String> list = map.get("list1");
        if (list == null) {
            list = new ArrayList<>();
            map.put("list1", list);
        }
        list.add("A");
        //实际上从 Java 8 开始，Map 提供了 computeIfAbsent() 方法，我们可以写成一行即可：

        map.computeIfAbsent("list1", k -> new ArrayList<>()).add("A");


        //*****putIfAbsent*****
        //这个方法的逻辑完全不同，注意它不是一个 get() 方法，而是 put() 方法的变种！这个方法的逻辑是，
        // 如果 Key 不存在或者对应的值是 null，则将 Value 设置进去，然后返回 null；否则只返回 Map 当中对应的值，而不做其他操作。
        //put与putIfAbsent区别，put在放入数据时，如果放入数据的key已经存在与Map中，最后放入的数据会覆盖之前存在的数据，而putIfAbsent在放入数据时，如果存在重复的key，那么putIfAbsent不会放入值。
        Map<Integer, String> map1 = new HashMap<>();
        map1.put(1, "张三");
        map1.put(2, "李四");
        map1.put(1, "王五");
        map1.forEach((key,value)->{
            System.out.println("key = " + key + ", value = " + value);
        });
        //key = 1, value = 王五
        //key = 2, value = 李四
        map1 = new HashMap<>();
        map1.putIfAbsent(1, "张三");
        map1.putIfAbsent(2, "李四");
        map1.putIfAbsent(1, "王五");
        map1.forEach((key,value)->{
            System.out.println("key = " + key + ", value = " + value);
        });
        //key = 1, value = 张三
        //key = 2, value = 李四

        //*****getOrDefault*****
        //这个方法同样检查 Map 中的 Key，如果发现 Key 不存在或者对应的值是 null，则返回第二个参数即默认值。要注意，这个默认值不会放入 Map。所以如果你这样写：
        Map<String, List<String>> map2 = new HashMap<>();
        map2.getOrDefault("list1", new ArrayList<>()).add("A");
//        执行完之后 map 仍然是空的！
    }
}
