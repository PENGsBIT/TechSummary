# Blog-设计模式DesignPatterns.proxy
总结静态代理、动态代理
一种软件设计模式，目的地希望能做到代码重用
## 静态代理
静态代理其实就是在程序运行之前，提前写好被代理方法的代理类，编译后运行。  
静态代理需要针对被代理的方法提前写好代理类，如果被代理的方法非常多则需要编写很多代码

## 动态代理：JDK
JDK中生成代理对象主要涉及的类有

* java.lang.reflect Proxy，主要方法为
```
static Object    newProxyInstance(ClassLoader loader,  //指定当前目标对象使用类加载器

 Class<?>[] interfaces,    //目标对象实现的接口的类型
 InvocationHandler h      //事件处理器
) 
//返回一个指定接口的代理类实例，该接口可以将方法调用指派到指定的调用处理程序。
```
* java.lang.reflect InvocationHandler，主要方法为
```
@Override
Object    invoke(Object 设计模式DesignPatterns.proxy, Method method, Object[] args) 
// 在代理实例上处理方法调用并返回结果。
```
## 动态代理：CGlib
cglib (Code Generation Library )是一个第三方代码生成类库，运行时在内存中动态生成一个子类对象从而实现对目标对象功能的扩展。
* JDK的动态代理有一个限制，就是使用动态代理的对象必须实现一个或多个接口。如果想代理没有实现接口的类，就可以使用CGLIB实现。
* CGLIB是一个强大的高性能的代码生成包，它可以在运行期扩展Java类与实现Java接口。
它广泛的被许多AOP的框架使用，例如Spring AOP和dynaop，为他们提供方法的interception（拦截）。  
* CGLIB包的底层是通过使用一个小而快的字节码处理框架ASM，来转换字节码并生成新的类。
不鼓励直接使用ASM，因为它需要你对JVM内部结构包括class文件的格式和指令集都很熟悉。

## 静态代理与动态代理的区别主要在：
静态代理在编译时就已经实现，编译完成后代理类是一个实际的class文件

动态代理是在运行时动态生成的，即编译完成后没有实际的class文件，而是在运行时动态生成类字节码，并加载到JVM中


