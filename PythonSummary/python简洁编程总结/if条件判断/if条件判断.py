# !/usr/bin/python
# -*- coding: utf-8 -*-
"""
@File    :  if条件判断.py
@Time    :  2020/10/12 16:05
@Author  :  ZPC
@Version :  1.0
@Contact :  zhoupengcheng03@corp.netease.com
@License :  (C)Copyright 2019-2020
@Desc    :  None
"""
if __name__ == '__main__':
    gid = []
    service = []
    if ('1' in gid) or ('2' in gid):
        if ('a' in service) or ('b' in service):
            print True

    # 代码改进后
    # 同理 set(gid).intersection(set(['1', '2']))，检查两个set 中相同的元素
    # 适用于全是or的情况
    gid_set = set(gid) & set(['1', '2'])
    service_set = set(service) & set(['a', 'b'])
    if gid_set and service:
        print True

    r = ""
    if r == 'a' or r == 'b':
        print
        "or 判断"

    if r in ['a', 'b']:
        print
        "使用集合"