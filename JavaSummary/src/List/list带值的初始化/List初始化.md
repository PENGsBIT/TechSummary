# Blog-List.list带值的初始化
总结list带值的初始化

## 方法1
使一个匿名的内部类的一个实例初始值设定项
```java
ArrayList<String> list = new ArrayList<String>() {{
    add("A");
    add("B");
    add("C");
}}

```

## 方法2
这样的话这个list的size就固定了，不能再add了，要注意。
```java
 List<String> test2 = Arrays.asList("xxx","yyy","zzz");
```       
用ArraysList报错
```java
ArrayList<String> test2 = Arrays.asList("xxx","yyy","zzz");
```
![Listwithvalue](../imgs/ListWithValue.PNG)

## 方法3
```java
List<String> name = new ArrayList();

name.add("xxx");

name.add("yyy");

name.add("zzz");
```




