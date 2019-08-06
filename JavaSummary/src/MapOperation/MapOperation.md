# Blog-MapOperation
总结Map java8 getOrDefault()，putIfAbsent() 和 computeIfAbsent() 三个方法

## computeIfAbsent
Map<String, List<String>> map = new HashMap<>();  
如果我们要放一个元素进去，很多人会这么写：
```java
List<String> list = map.get("list1");
if (list == null) {
  list = new ArrayList<>();
  map.put("list1", list);
}
list.add("A");
```

实际上从 Java 8 开始，Map 提供了 computeIfAbsent() 方法，我们可以写成一行即可：
```java
map.computeIfAbsent("list1", k -> new ArrayList<>()).add("A");
```
其中变量 k 是 Map 的 key。Map定义的时候注意使用<anything,List<>>(List)定义不然无法使用lamada的k

## computeIfPresent
如果指定的key存在，则根据旧的key和value计算新的值newValue, 如果newValue不为null，则设置key新的值为newValue, 如果newValue为null, 则删除该key的值
```java
map.computeIfPresent(1, (key, value) -> (key + 1) + value);
// 存在key为1， 则根据旧的key和value计算新的值，输出 2a
System.out.println(map.get(1));

map.computeIfPresent(2, (key, value) -> null);
// 存在key为2， 根据旧的key和value计算得到null，删除该值，输出 null
System.out.println(map.get(2));
```
## putIfAbsent
这个方法的逻辑完全不同，注意它不是一个 get() 方法，而是 put() 方法的变种！这个方法的逻辑是，如果 Key 不存在或者对应的值是 null，则将 Value 设置进去，然后返回 null；否则只返回 Map 当中对应的值，而不做其他操作。
put与putIfAbsent区别，put在放入数据时，如果放入数据的key已经存在与Map中，最后放入的数据会覆盖之前存在的数据，而putIfAbsent在放入数据时，如果存在重复的key，那么putIfAbsent不会放入值。
不会覆盖
put
```java
Map<Integer, String> map = new HashMap<>();
map.put(1, "张三");
map.put(2, "李四");
map.put(1, "王五");
map.forEach((key,value)->{
    System.out.println("key = " + key + ", value = " + value);
});
key = 1, value = 王五
key = 2, value = 李四
```
putIfAbsent
```java
Map<Integer, String> putIfAbsent = new HashMap<>();
putIfAbsent.putIfAbsent(1, "张三");
putIfAbsent.putIfAbsent(2, "李四");
putIfAbsent.putIfAbsent(1, "王五");
putIfAbsent.forEach((key,value)->{
	System.out.println("key = " + key + ", value = " + value);
});
key = 1, value = 张三
key = 2, value = 李四
```
## getOrDefault
这个方法同样检查 Map 中的 Key，如果发现 Key 不存在或者对应的值是 null，则返回第二个参数即默认值。要注意，这个默认值不会放入 Map。所以如果你这样写：
  ```java
Map<String, List<String>> map = new HashMap<>();
map.getOrDefault("list1", new ArrayList<>()).add("A");
```
        
执行完之后 map 仍然是空的！