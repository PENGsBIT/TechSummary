# Blog-Java11 New Features
总结Java 11 8 个新特性相关问题

## 1.本地变量类型推断
```java
var javastack = "javastack";
System.out.println(javastack);
```
局部变量类型推断就是左边的类型直接使用 var 定义，而不用写具体的类型，编译器能根据右边的表达式自动推断类型，如上面的 String 。
## 2.字符串加强
```java
// 判断字符串是否为空白
" ".isBlank();                // true

// 去除首尾空格
" Javastack ".strip();          // "Javastack"

// 去除尾部空格 
" Javastack ".stripTrailing();  // " Javastack"

// 去除首部空格 
" Javastack ".stripLeading();   // "Javastack "

// 复制字符串
"Java".repeat(3);             // "JavaJavaJava"

// 行数统计
"A\nB\nC".lines().count();    // 3
```

## 3.集合加强
集合（List/ Set/ Map）都添加了 of 和 copyOf 方法，它们两个都用来创建不可变的集合，来看下它们的使用和区别。
```java
var list = List.of("Java", "Python", "C");
var copy = List.copyOf(list);
System.out.println(list == copy);   // true
```
```java
var list = new ArrayList<String>();
var copy = List.copyOf(list);
System.out.println(list == copy);   // false
```
copyOf 方法会先判断来源集合是不是 AbstractImmutableList 类型的，如果是，就直接返回，如果不是，则调用 of 创建一个新的集合。

示例2因为用的 new 创建的集合，不属于不可变 AbstractImmutableList 类的子类，所以 copyOf 方法又创建了一个新的实例，所以为false.

注意：使用 of 和 copyOf 创建的集合为不可变集合，不能进行添加、删除、替换、排序等操作，不然会报 java.lang.UnsupportedOperationException 异常。
上面演示了 List 的 of 和 copyOf 方法，Set 和 Map 接口都有。

## 4.Stream 加强
1) 增加单个参数构造方法，可为null
```java
Stream.ofNullable(null).count(); // 0
```

2) 增加 takeWhile 和 dropWhile 方法
```java
Stream.of(1, 2, 3, 2, 1)
    .takeWhile(n -> n < 3)
    .collect(Collectors.toList());  // [1, 2]
```

从开始计算，当 n < 3 时就截止。
```java
Stream.of(1, 2, 3, 2, 1)
    .dropWhile(n -> n < 3)
    .collect(Collectors.toList());  // [3, 2, 1]
```

这个和上面的相反，一旦 n < 3 不成立就开始计算。

3）iterate重载

这个 iterate 方法的新重载方法，可以让你提供一个 Predicate (判断条件)来指定什么时候结束迭代。
## 5.Optional 加强
现在可以很方便的将一个 Optional 转换成一个 Stream, 或者当一个空 Optional 时给它一个替代的。
```java
Optional.of("javastack").orElseThrow();     // javastack
Optional.of("javastack").Stream().count();  // 1
Optional.ofNullable(null)
    .or(() -> Optional.of("javastack"))
    .get();   // javastack
```

## 6.InputStream 加强
InputStream 终于有了一个非常有用的方法：transferTo，可以用来将数据直接传输到 OutputStream，这是在处理原始数据流时非常常见的一种用法，如下示例。
```java
var classLoader = ClassLoader.getSystemClassLoader();
var inputStream = classLoader.getResourceAsStream("javastack.txt");
var javastack = File.createTempFile("javastack2", "txt");
try (var outputStream = new FileOutputStream(javastack)) {
    inputStream.transferTo(outputStream);
}
```

## 7.HTTP Client API
这是 Java 9 开始引入的一个处理 HTTP 请求的的孵化 HTTP Client API，该 API 支持同步和异步，而在 Java 11 中已经为正式可用状态，你可以在 java.net 包中找到这个 API。
```java
var request = HttpRequest.newBuilder()
    .uri(URI.create("https://javastack.cn"))
    .GET()
    .build();
var client = HttpClient.newHttpClient();

// 同步
HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
System.out.println(response.body());

// 异步
client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
    .thenApply(HttpResponse::body)
    .thenAccept(System.out::println);
```
上面的 .GET() 可以省略，默认请求方式为 Get
## 8.命令编译运行源代码
```
// 编译
javac Javastack.java


// 运行
java Javastack
```
在我们的认知里面，要运行一个 Java 源代码必须先编译，再运行，两步执行动作。而在未来的 Java 11 版本中，通过一个 java 命令就直接搞定了，如以下所示。
```youtrack
java Javastack.java
```

