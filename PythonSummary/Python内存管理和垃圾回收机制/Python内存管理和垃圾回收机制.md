# Python内存管理和垃圾回收机制  
Python中主要依靠gc（garbage collector）模块的引用计数技术来进行垃圾回收。
python的内存管理机制有三种

##引用计数
引用计数是一种非常高效的内存管理手段，当一个pyhton对象被引用时其引用计数增加1，当其不再被引用时引用计数减1，当引用计数等于0的时候，对象就被删除了。

优点：引用计数有一个最大的优点，即“实时性”，任何内存，一旦没有指向它的引用，就会立即被回收。
  
缺点：  
1.循环引用  (“标记-清除”，“分代回收”两种收集技术。)
2.引用计数机制所带来的维护引用计数的额外操作与 Python 运行中所进行的内存分配和释放，赋值的次数是成正比的。
##垃圾回收
###标记清除
循环引用一般在容器对象才会产生，比如字典，元祖，列表等。首先为了追踪对象，需要每个容器对象维护两个额外的指针，用来将容器对象组成一个链表，指针分别指向前后两个容器对象，这样可以将对象的循环引用摘除，就可以得出两个对象的有效计数。
  
首先将现在的内存链表一分为二，一条链表中维护 root object 集合，成为 root 链表，而另外一条链表中维护剩下的对象，成为 unreachable 链表。  

现在的unreachable可能存在被root链表中的对象，直接或间接引用的对象，这些对象是不能被回收的，一旦在标记的过程中，发现这样的对象，就将其从unreachable链表中移到root链表中；当完成标记后，unreachable链表中剩下的所有对象就是名副其实的垃圾对象了，接下来的垃圾回收只需限制在unreachable链表中即可。
###分代回收
**从理论上说，创建==释放数量应该是这样子。但是如果存在循环引用的话，肯定是创建>释放数量，当创建数与释放数量的差值达到规定的阈值的时候**
新生的对象被放入0代，如果该对象在第0代的一次gc垃圾回收中活了下来，那么它就被放到第1代里面（它就升级了）。如果第1代里面的对象在第1代的一次gc垃圾回收中活了下来，它就被放到第2代里面。  

从上一次第0代gc后，如果分配对象的个数减去释放对象的个数大于threshold0，那么就会对第0代中的对象进行gc垃圾回收检查。
## 内存池
第3层：最上层，用户对Python对象的直接操作

第1层和第2层：内存池，有Python的接口函数PyMem_Malloc实现-----若请求分配的内存在1~256字节之间就使用内存池管理系统进行分配，调用malloc函数分配内存，但是每次只会分配一块大小为256K的大块内存，不会调用free函数释放内存，将该内存块留在内存池中以便下次使用。

第0层：大内存-----若请求分配的内存大于256K，malloc函数分配内存，free函数释放内存。

第-1，-2层：操作系统进行操作

##性能改进
1.手动垃圾回收

2.避免循环引用（手动解循环引用和使用弱引用）

3.调高垃圾回收阈值