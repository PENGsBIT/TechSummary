# -*- coding: UTF-8 -*-

import socket  # 导入 socket 模块

host = socket.gethostname()  # 获取本地主机名
port = 12345  # 设置端口
# 创建 socket 对象
with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    s.bind((host, port))  # 绑定端口
    s.listen(5)  # 等待客户端连接
    c, addr = s.accept()  # 建立客户端连接
    while True:
        data = c.recv(1024)
        if not data: break
        c.send('send a message')
        c.close()  # 关闭连接
