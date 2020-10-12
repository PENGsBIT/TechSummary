# !/usr/bin/python
# -*- coding: utf-8 -*-
"""
@File    :  DictKey相关操作.py
@Time    :  2020/10/12 15:46
@Author  :  ZPC
@Version :  1.0
@Contact :  zhoupengcheng03@corp.netease.com
@License :  (C)Copyright 2019-2020
@Desc    :  None
"""
if __name__ == '__main__':
    mylist = [{'k1': 'v1', 'k2': 'v2'}, {'k3': 'v3', 'k4': 'v4'}]
    dict = []

    for mydict in mylist:
        for k in mydict.keys():
            dict.append(k)
    print "dict:".format(dict)

    dict = []
    # 对字典的默认操作就是看做列表，内容是字典的Key
    for mydict in mylist:
        dict.extend(mydict)
    print "dict:".format(dict)