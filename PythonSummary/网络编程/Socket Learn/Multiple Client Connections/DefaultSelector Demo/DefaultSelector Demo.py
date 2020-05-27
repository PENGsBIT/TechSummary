import selectors
import socket

sel = selectors.DefaultSelector()


def accept(sock, mask):
    conn, addr = sock.accept()  # Should be ready
    print('accepted', conn, 'from', addr)
    # 设置socket的阻塞或非阻塞模式
    # 阻塞模式下当试图对该文件描述符进行读写时，如果当时没有东西可读，或者暂时不可写，程序就进入等待状态，直到有东西可读或者可写为止
    # 非阻塞模式下如果没有东西可读，或者不可写，读写函数马上返回，而不会等待
    conn.setblocking(False)
    # 对描述符进行注册，也就是对该描述符的EVENT_READ事件进行监听，当又READ事件通知时，调用回调函数read
    # selectors库提供了两个监听事件：EVENT_READ和EVENT_WRITE
    sel.register(conn, selectors.EVENT_READ, read)


def read(conn, mask):
    data = conn.recv(1000)  # Should be ready
    if data:
        print('echoing', repr(data), 'to', conn)
        conn.send(data)  # Hope it won't block
    else:
        print('closing', conn)
        # 注销描述符
        sel.unregister(conn)
        conn.close()


sock = socket.socket()
sock.bind(('localhost', 1234))
sock.listen(100)
sock.setblocking(False)
sel.register(sock, selectors.EVENT_READ, accept)

while True:
    # select()函数是实现I/O异步的关键，等待，直到一些已注册的文件对象准备就绪，或者超时。
    # 如果timeout＞0，则指定最大等待时间，以秒为单位，如果超时没有，则调用将阻塞，
    #     直到被监视的文件对象准备就绪。如果timeout< 0，调用将不会阻塞，并将报告当前就绪的文件对象。
    # 　　该函数返回一个元组(key, events)
    # key为class selectors.SelectorKey对象，SelectorKey = namedtuple('SelectorKey', ['fileobj', 'fd', 'events', 'data'])
    # 　　fileobj为注册的文件对象
    # 　　fd为文件描述符
    # 　　data为与文件对象相关联的自定义数据，如上面的回调函数
    events = sel.select()
    for key, mask in events:
        callback = key.data
        callback(key.fileobj, mask)
