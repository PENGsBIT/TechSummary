# !/usr/bin/python
# -*- coding: utf-8 -*-
"""
@File    :  阻塞主线程.py
@Time    :  2020/11/20 14:35
@Author  :  ZPC
@Version :  1.0
@Contact :  zhoupengcheng03@corp.netease.com
@License :  (C)Copyright 2019-2020
@Desc    :  None
"""
import os
import random

from concurrent.futures._base import wait, ALL_COMPLETED
from concurrent.futures import ThreadPoolExecutor, as_completed
from concurrent.futures import ProcessPoolExecutor
import time


def sayhello(k, v):
    x = "hello: "
    for i in xrange(2):
        if i:
            x += ' v:' + v
        else:
            x += 'k:' + k
        time.sleep(random.randint(1, 3))

    print('result {} in thread:{}'.format(x, os.getpid()))

    return x


def main():
    start3 = time.time()
    seed = {"a": "a", "b": "a", "q": "a", "w": "a", "e": "a", "r": "a", "t": "a", "y": "a", "u": "a", "i": "a",
            "o": "a", "p": "a", "s": "a", "d": "f", "g": "a", "h": "l"}
    with ProcessPoolExecutor() as pool:
        futures = [pool.submit(sayhello, k, v) for k, v in seed.iteritems()]
        wait(futures, return_when=ALL_COMPLETED)
        print 'wait all'
        pool.map(sayhello, seed.keys(), seed.values())
        pool.shutdown()
        print 'shutdown pool'


if __name__ == '__main__':
    main()
    print 'end'
