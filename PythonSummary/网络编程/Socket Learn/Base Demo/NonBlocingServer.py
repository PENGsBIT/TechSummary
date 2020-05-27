import socket

sk = socket.socket()

sk.bind(('127.0.0.1', 8080))
# 不阻塞，即将socket当中的所有需要阻塞的方法都改变成非阻塞
sk.setblocking(False)
sk.listen()
# 用来存储所有来请求server端的conn
connectList = []
# 用来存储所有已经断开与server端连接的conn
connDisconnect = []
while True:
    # 不阻塞，但是没人连接就返回error
    try:
        conn, addr = sk.accept()
        print('connect success', addr)
        connectList.append(conn)
    except BlockingIOError:
        for conn in connectList:
            try:
                msg = conn.recv(1024)  # 不阻塞，但是没有消息会报错
                if msg == b'':
                    connDisconnect.append(conn)
                    continue
                print(msg)
                conn.send(b'bye')
            except BlockingIOError:
                pass
        for conn in connDisconnect:
            connectList.remove(conn)
        connDisconnect.clear()
