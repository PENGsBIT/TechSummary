# coding: utf-8
import select
import socket
import queue
from time import sleep

# Create a TCP/IP
server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server.setblocking(False)

# Bind the socket to the port
server_address = ('localhost', 8090)
print('starting up on %s port %s' % server_address)
server.bind(server_address)

# Listen for incoming connections
server.listen(5)

# Sockets from which we expect to read
inputs = [server]

# Sockets to which we expect to write
# 处理要发送的消息
outputs = []

# Outgoing message queues (socket: Queue)
message_queues = {}

while inputs:
    # Wait for at least one of the sockets to be ready for processing
    print('waiting for the next event')
    # 开始select 监听, 对input_list 中的服务器端server 进行监听    # 一旦调用socket的send, recv函数，将会再次调用此模块
    readable, writable, exceptional = select.select(inputs, outputs, inputs)

    # Handle inputs
    # 循环判断是否有客户端连接进来, 当有客户端连接进来时select 将触发
    for s in readable:
        # 判断当前触发的是不是服务端对象, 当触发的对象是服务端对象时,说明有新客户端连接进来了
        # 表示有新用户来连接
        if s is server:
            # A "readable" socket is ready to accept a connection
            connection, client_address = s.accept()
            print('connection from', client_address)
            # this is connection not server
            connection.setblocking(0)
            # 将客户端对象也加入到监听的列表中, 当客户端发送消息时 select 将触发
            inputs.append(connection)

            # Give the connection a queue for data we want to send
            # 为连接的客户端单独创建一个消息队列，用来保存客户端发送的消息
            message_queues[connection] = queue.Queue()
        else:
            # 有老用户发消息, 处理接受
            # 由于客户端连接进来时服务端接收客户端连接请求，将客户端加入到了监听列表中(input_list), 客户端发送消息将触发
            # 所以判断是否是客户端对象触发
            data = s.recv(1024)
            # 客户端未断开
            if data != '':
                # A readable client socket has data
                print('received "%s" from %s' % (data, s.getpeername()))
                # 将收到的消息放入到相对应的socket客户端的消息队列中
                message_queues[s].put(data)
                # Add output channel for response
                # 将需要进行回复操作socket放到output 列表中, 让select监听
                if s not in outputs:
                    outputs.append(s)
            else:
                # 客户端断开了连接, 将客户端的监听从input列表中移除
                # Interpret empty result as closed connection
                print('closing', client_address)
                # Stop listening for input on the connection
                if s in outputs:
                    outputs.remove(s)
                inputs.remove(s)
                s.close()

                # Remove message queue
                # 移除对应socket客户端对象的消息队列
                del message_queues[s]

    # Handle outputs
    # 如果现在没有客户端请求, 也没有客户端发送消息时, 开始对发送消息列表进行处理, 是否需要发送消息
    # 存储哪个客户端发送过消息
    for s in writable:
        try:
            # 如果消息队列中有消息,从消息队列中获取要发送的消息
            message_queue = message_queues.get(s)
            send_data = ''
            if message_queue is not None:
                send_data = message_queue.get_nowait()
            else:
                # 客户端连接断开了
                print("has closed ")
        except queue.Empty:
            # 客户端连接断开了
            print("%s" % (s.getpeername()))
            outputs.remove(s)
        else:
            # print "sending %s to %s " % (send_data, s.getpeername)
            # print "send something"
            if message_queue is not None:
                s.send(send_data)
            else:
                print("has closed ")
            # del message_queues[s]
            # writable.remove(s)
            # print "Client %s disconnected" % (client_address)

    # # Handle "exceptional conditions"
    # 处理异常的情况
    for s in exceptional:
        print('exception condition on', s.getpeername())
        # Stop listening for input on the connection
        inputs.remove(s)
        if s in outputs:
            outputs.remove(s)
        s.close()

        # Remove message queue
        del message_queues[s]

    sleep(1)
