# !/usr/bin/python
# -*- coding: utf-8 -*-
"""
@File    :  ThreadPoolExecutor使用.py
@Time    :  2020/11/16 14:38
@Author  :  ZPC
@Version :  1.0
@Contact :  zhoupengcheng03@corp.netease.com
@License :  (C)Copyright 2019-2020
@Desc    :  None
"""

# 相比 threading 等模块，该模块通过 submit 返回的是一个 future 对象，
# 它是一个未来可期的对象，通过它可以获悉线程的状态主线程(或进程)中可以获取某一个线程(进程)执行的状态或者某一个任务执行的状态及返回值：
# 主线程可以获取某一个线程（或者任务的）的状态，以及返回值。
# 当一个线程完成的时候，主线程能够立即知道。
# 让多线程和多进程的编码接口一致。
# coding: utf-8
from concurrent.futures import ThreadPoolExecutor, wait, FIRST_COMPLETED, ALL_COMPLETED, as_completed
import time


def spider(page):
    time.sleep(page)
    print "crawl task{} finished".format(page)
    return page + 10


with ThreadPoolExecutor(max_workers=5) as t:
    print('========test ThreadPoolExecutor =========')
    # 创建一个最大容纳数量为5的线程池
    task1 = t.submit(spider, 1)
    task2 = t.submit(spider, 2)  # 通过submit提交执行的函数到线程池中
    task3 = t.submit(spider, 3)

    # 通过done来判断线程是否完成
    print "task1:{}".format(task1.done())
    print "task2:{}".format(task2.done())
    print"task3:{}".format(task3.done())

    time.sleep(2.5)
    print "task1:{}".format(task1.done())
    print "task2:{}".format(task2.done())
    print"task3:{}".format(task3.done())
    # 通过result来获取返回值
    print(task1.result())

with ThreadPoolExecutor(max_workers=5) as t:
    print('========test wait =========')
    all_task = [t.submit(spider, page) for page in range(1, 5)]
    # 当完成第一个任务的时候，就停止等待，继续主线程任务
    wait(all_task, return_when=FIRST_COMPLETED)
    print('finished')
    print(wait(all_task, timeout=2.5))

with ThreadPoolExecutor(max_workers=5) as t:
    print('========test as_completed =========')
    obj_list = [t.submit(spider, page) for page in range(1, 5)]
    # obj_list = []
    # for page in range(1, 5):
    #     obj = t.submit(spider, page)
    #     obj_list.append(obj)

    for future in as_completed(obj_list):
        data = future.result()
        print("main: {}".format(data))

with ThreadPoolExecutor(max_workers=5) as t:
    print('========test map =========')
    i = 0
    # def map(self, func, *iterables, **kwargs):
    for result in t.map(spider, [2, 3, 1, 4]):
        print("task{}:{}".format(i, result))
        i += 1