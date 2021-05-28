# !/usr/bin/python
# -*- coding: utf-8 -*-
"""
@File    :  函数装饰器-装饰函数.py
@Time    :  2020/12/3 17:38
@Author  :  ZPC
@Version :  1.0
@Contact :  zhoupengcheng03@corp.netease.com
@License :  (C)Copyright 2019-2020
@Desc    :  None
"""


"""
两个点需要注意的，

第一：wrapper需要保证与add_function参数一致。因为返回的wrapper就是add_function，所以要统一，我们可以使用*arg,和**args去匹配任何参数；

第二：wrapper一定要返回值。因为add_function函数是需要返回值的。
"""


def MethodDecoration(function):  # 外层decorator
    c = 150
    d = 200

    def wrapper(a, b):  # 内层wrapper。和add_function参数要一样
        result = function(a, b)
        result = result * c / d  # 加密，相当于添加额外功能
        return result  # 此处一定要返回值

    return wrapper


@MethodDecoration
def add_function(a, b):
    return a + b


result = add_function(100, 300)  # 函数调用
print(result)