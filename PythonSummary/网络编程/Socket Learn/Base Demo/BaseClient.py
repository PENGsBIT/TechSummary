# -*- coding: UTF-8 -*-

import socket  # 导入 socket 模块

host = socket.gethostname()  # 获取本地主机名
port = 12345  # 设置端口号

# 创建 socket 对象
with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    s.connect((host, port))
    s.sendall(b'this message from cient')
    data = s.recv(1024)
    print('Received', repr(data))
    s.close()
