#Learn Python select summary
**select模块专注于I/O多路复用,提供了select poll epoll三个方法(其中后两个在Linux中可用,windows仅支持select)**
##select方法
select()时:内核监听哪些文件描述符(最多监听1024个fd)的哪些事件,当没有文件描述符事件发生时,进程被阻塞;当一个或者多个文件描述符事件发生时,进程被唤醒。
1.上下文切换转换为内核态
2.将fd从用户空间复制到内核空间
3.内核遍历所有fd,查看其对应事件是否发生
4.如果没发生,将进程阻塞,当设备驱动产生中断或者timeout时间后,将进程唤醒,再次进行遍历
5.返回遍历后的fd
6.将fd从内核空间复制到用户空间
(fd:file descriptor 文件描述符:内核（kernel）利用文件描述符（file descriptor）来访问文件。文件描述符是非负整数。)

###方法详解
select.select模块其实主要就是要理解它的参数, 以及其三个返回值.
**fd_r_list, fd_w_list, fd_e_list = select.select(rlist, wlist, xlist, [timeout])**

参数: 可接受四个参数(前三个必须)
rlist: wait until ready for reading
wlist: wait until ready for writing
xlist: wait for an “exceptional condition”
timeout: 超时时间

返回值:三个列表
select方法用来监视文件描述符(当文件描述符条件不满足时,select会阻塞),当某个文件描述符状态改变后,会返回三个列表
* 当参数1 序列中的fd满足“可读”条件时,则获取发生变化的fd并添加到fd_r_list中
* 当参数2 序列中含有fd时,则将该序列中所有的fd添加到 fd_w_list中
* 当参数3 序列中的fd发生错误时,则将该发生错误的fd添加到 fd_e_list中
* 当超时时间为空,则select会一直阻塞,直到监听的句柄发生变化，当超时时间 = n(正整数)时,那么如果监听的句柄均无任何变化,则select会阻塞n秒,之后返回三个空列表,如果监听的句柄有变化,则直接执行。

##select方法
epoll很好的改进了select:

1.epoll的解决方案在epoll_ctl函数中。每次注册新的事件到epoll句柄中时,会把所有的fd拷贝进内核,而不是在epoll_wait的时候重复拷贝。epoll保证了每个fd在整个过程中只会拷贝一次。
2.epoll会在epoll_ctl时把指定的fd遍历一遍(这一遍必不可少)并为每个fd指定一个回调函数,当设备就绪,唤醒等待队列上的等待者时,就会调用这个回调函数,而这个回调函数会把就绪的fd加入一个就绪链表。epoll_wait的工作实际上就是在这个就绪链表中查看有没有就绪的fd
3.epoll对文件描述符没有额外限制


