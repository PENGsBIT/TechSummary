# !/usr/bin/python
# -*- coding: utf-8 -*-
"""
@File    :  __init__.py.py
@Time    :  2020/11/3 10:35
@Author  :  ZPC
@Version :  1.0
@Contact :  zhoupengcheng03@corp.netease.com
@License :  (C)Copyright 2019-2020
@Desc    :  None
"""

add = lambda x, y : x+y
add(1,2)  # 结果为3

# 函数式编程的特性，如：map、reduce、filter、sorted等这些函数都支持函数作为参数，lambda函数就可以应用在函数式编程中。
# 需求：将列表中的元素按照绝对值大小进行升序排列
list1 = [3,5,-4,-1,0,-2,-6]
sorted(list1, key=lambda x: abs(x))

# 应用在闭包中
def get_y(a, b):
    return lambda x: a*x + b

y1 = get_y(1, 1)
y1(1)  # 结果为2