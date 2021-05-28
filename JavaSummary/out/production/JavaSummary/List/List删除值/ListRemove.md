# Blog-List/Map Remove
总结List/Map Remove相关问题

## List.remove(index) or List.remove(OBJ)
当LIST删除.remove(int)时int作为index删除，当List.remove(Integer)，Integer做为包装类可以直接被删除掉

## java.util.ConcurrentModificationException产生
当我们迭代一个ArrayList或者HashMap时，如果尝试对集合做一些修改操作（例如删除元素），可能会抛出java.util.ConcurrentModificationException的异常。
### 异常原因
ArrayList的父类AbstarctList中有一个域modCount，每次对集合进行修改（增添元素，删除元素……）时都会modCount++
而foreach的背后实现原理其实就是Iterator（关于Iterator可以看Java Design Pattern: Iterator），等同于注释部分代码。
在这里，迭代ArrayList的Iterator中有一个变量expectedModCount，该变量会初始化和modCount相等，但如果接下来如果集合进行修改modCount改变，就会造成expectedModCount!=modCount，此时就会抛出java.util.ConcurrentModificationException异常
###异常的解决
1. 单线程环境:Itr中的也有一个remove方法，实质也是调用了ArrayList中的remove，但增加了expectedModCount = modCount;保证了不会抛出java.util.ConcurrentModificationException异常。  
这个办法的有两个弊端  
1.只能进行remove操作，add、clear等Itr中没有。  
2.而且只适用单线程环境。 
 
2. 多线程环境
CopyOnWriteArrayList，解决了多线程问题，同时可以add、clear等操作  
CopyOnWriteArrayList也是一个线程安全的ArrayList，其实现原理在于，每次add,remove等所有的操作都是重新创建一个新的数组，再把引用指向新的数组
