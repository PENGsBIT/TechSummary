# !/usr/bin/python
# -*- coding: utf-8 -*-
"""
@File    :  result阻塞.py
@Time    :  2020/11/20 14:31
@Author  :  ZPC
@Version :  1.0
@Contact :  zhoupengcheng03@corp.netease.com
@License :  (C)Copyright 2019-2020
@Desc    :  None
"""
import random
from concurrent.futures import ProcessPoolExecutor
import time

# executor = ThreadPoolExecutor()
executor = ProcessPoolExecutor()


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

    futures = [executor.submit(sayhello, k, v) for k, v in seed.iteritems()]

    for i in futures:
        print('i', i.done())
        print i.result()


# cnt = len(futures)
# for future in futures:
# 	data = future.result()
# 	print('data:', data)
# 	cnt -= 1
# 	print('cnt:', cnt)
# 	if cnt == 0:
# 		print('all compeleted')
# 		end3 = time.time()
# 		print("run_time: " + str(end3 - start3))


if __name__ == '__main__':
    main()
    print 'end'
