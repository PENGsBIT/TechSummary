# !/usr/bin/python
# -*- coding: utf-8 -*-
"""
@File    :  ProcessPoolExecutor使用.py
@Time    :  2020/11/16 14:40
@Author  :  ZPC
@Version :  1.0
@Contact :  zhoupengcheng03@corp.netease.com
@License :  (C)Copyright 2019-2020
@Desc    :  None
"""
from concurrent.futures import ProcessPoolExecutor,ThreadPoolExecutor
import time


def task(name):
    print("name", name)
    time.sleep(1)


if __name__ == "__main__":
    start = time.time()
    ex = ProcessPoolExecutor(2)

    for i in range(5):
        ex.submit(task, "safly%d" % i)
    ex.shutdown(wait=True)

    print("main")
    end = time.time()
    print(end - start)
