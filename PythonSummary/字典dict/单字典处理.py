# !/usr/bin/python
# -*- coding: utf-8 -*-
"""
@File    :  单字典处理.py
@Time    :  2020/10/30 15:39
@Author  :  ZPC
@Version :  1.0
@Contact :  zhoupengcheng03@corp.netease.com
@License :  (C)Copyright 2019-2020
@Desc    :  之前写代码很多时候会遇到这么一种情况:在python的字典中只有一个key/value键值对，想要获取其中的这一个元素还要写个for循环获取。
"""

d = {}
# 方法一
d = {'name': 'haohao'}
(key, value), = d.items()

# 方法二
d = {'name': 'haohao'}
key = list(d)[0]
value = list(d.values())[0]

# 方法三
d = {'name': 'haohao'}
key, = d
value, = d.values()
