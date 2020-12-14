# Blog-CPU使用分析
CPU使用率与机器负载的关系与区别

load average:系统平均负载是CPU的Load，它所包含的信息不是CPU的使用率状况，而是在一段时间内CPU正在处理以及等待CPU处理的进程数之和的统计信息，也就是CPU使用队列的长度的统计信息。这个数字越小越好。
## CPU负载和CPU利用率的区别
* CPU利用率：显示的是程序在运行期间实时占用的CPU百分比  

* CPU负载：显示的是一段时间内正在使用和等待使用CPU的平均任务数。CPU利用率高，并不意味着负载就一定大。  
![load](/imgs/load.jpg)  
![load](/imgs/使用率%20和%20CPU%20Load%20对比jpg.jpg)

## CPU负载和CPU利用率的关系
正常情况下，cpu 使用率高，load 也会比较高。cpu 使用率低，load 也会比较低。

也有例外情况：

* load average低，利用率高：如果CPU执行的任务数很少，则load average会低，但是这些任务都是CPU密集型，那么利用率就会高。
* load average高，利用率低：如果CPU执行的任务数很多，则load average会高，但是在任务执行过程中CPU经常空闲（比如等待IO），那么利用率就会低。