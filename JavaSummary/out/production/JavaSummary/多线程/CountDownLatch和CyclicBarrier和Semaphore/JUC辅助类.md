# Blog-JUC辅助类
CountDownLatch和CyclicBarrier和Semaphore使用和应用场景总结

## CountDownLatch
比如某个任务依赖于其他的两个任务，只有那两个任务执行结束后，它才能执行。
![CountDownLatch](../imgs/CountDownLatch.jpg)
## CyclicBarrier
用来挂起当前线程，直至所有线程都到达barrier状态再同时执行后续任务；
比如线程1，2，3运行，线程1达到barrier.awit()阻塞接着等待线程2、3达到阻塞状态，
当1，2，3都达到awit则1，2，3继续执行接下来的操作
![CyclicBarrier](../imgs/CyclicBarrier.jpg)
## Semaphore
同时让多个线程同时访问共享资源，通过 acquire() 获取一个许可，如果没有就等待，而 release() 释放一个许可。
![Semaphore](../imgs/Semaphore.jpg)
