#!/usr/bin/env python
#-*- coding:utf-8 -*-

import select
import socket

s = socket.socket()
s.bind(('192.168.12.172',9999))
s.listen(5)

#创建一个epoll对象
epoll_obj = select.epoll()
# 在服务端socket上面注册对读事件（event）的关注。一个读event随时会触发服务端socket去接收一个socket连接
epoll_obj.register(s,select.EPOLLIN)

connections = {}

while True:
    # 查询epoll对象，看是否有任何关注的event被触发。参数“1”表示，我们会等待1秒来看是否有event发生。
    # 如果有任何我们感兴趣的event发生在这次查询之前，这个查询就会带着这些event的列表立即返回，返回一个元组列表[(fd1, events1), (...)]
    events = epoll_obj.poll(1)

    # event作为一个序列（fileno，event code）的元组返回。fileno是文件描述符的代名词，始终是一个整数。
    for fd, event in events:
        help(event)
        # 如果是服务端产生event,表示有一个新的连接进来
        if fd == s.fileno():
            #创建一个新套接字，并且连接到对应到客户端（ip:port）
            conn, addr = s.accept()

            print('client connected: ', addr)
            #将上述新增服务端套接字注册到epoll_obj对象中
            epoll_obj.register(conn,select.EPOLLIN)
            connections[conn.fileno()] = conn
            print("connections: {0}".format(connections))

            print("Starting receving data...")

            #将客户端发送的数据接收进来
            msg = conn.recv(200)

            #给客户端响应
            conn.sendall('Server OK'.encode())
        else:
            #当fd不是上面声明的服务端套接字s的时候，执行以下操作
            try:
                #说明该客户端是已经来过的，则直接操作数据
                fd_obj = connections[fd]#获取文件描述符指向的套接字对象
                msg = fd_obj.recv(200)
                fd_obj.sendall('Client OK'.encode())
            except BrokenPipeError:
                print("ending up ....")
                # 注销对此socket连接的关注
                epoll_obj.unregister(fd)
                # 关闭socket连接
                connections[fd].close()
                del connections[fd]
s.close()
epoll_obj.close()
