# !/usr/bin/python
# -*- coding: utf-8 -*-
"""
@File    :  异步提交.py
@Time    :  2020/11/20 11:31
@Author  :  ZPC
@Version :  1.0
@Contact :  zhoupengcheng03@corp.netease.com
@License :  (C)Copyright 2019-2020
@Desc    :  None
"""

# from multiprocessing import Process,Pool
from concurrent.futures import ProcessPoolExecutor
import time, random, os


def task(name, n):
    print('%s is task %s' % (name, os.getpid()))
    time.sleep(1)
    return n ** 2


if __name__ == '__main__':
    p = ProcessPoolExecutor(2)
    objs = []
    start = time.time()
    for i in range(5):
        obj = p.submit(task, 'num: %s' % i, i)  # 异步调用
        objs.append(obj)

    p.shutdown(wait=True)
    print('main', os.getpid())
    for obj in objs:
        print(obj.result())

    stop = time.time()
    print(stop - start)
