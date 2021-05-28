# !/usr/bin/python
# -*- coding: utf-8 -*-
"""
@File    :  类装饰器.py
@Time    :  2020/12/3 17:45
@Author  :  ZPC
@Version :  1.0
@Contact :  zhoupengcheng03@corp.netease.com
@License :  (C)Copyright 2019-2020
@Desc    :  None
"""


class MethodDecorator:
    def __init__(self, function):
        self.function = function

    def __call__(self):
        print('开始')
        self.function()
        print('结束')


@MethodDecorator
def myfunc():
    print('我是函数myfunc')


myfunc()