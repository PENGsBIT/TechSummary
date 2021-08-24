# !/usr/bin/python
# -*- coding: utf-8 -*-
"""
@File    :  py类方法.py
@Time    :  2021/4/16 11:42
@Author  :  ZPC
@Version :  1.0
@Contact :  zhoupengcheng03@corp.netease.com
@License :  (C)Copyright 2019-2020
@Desc    :  None
"""


class Foo(object):
    @classmethod  # 定义类方法要点1
    def foo(cls):  # 定义类方法要点2
        print 'call foo'


Foo.foo()  # call foo
Foo().foo()  # call foo
