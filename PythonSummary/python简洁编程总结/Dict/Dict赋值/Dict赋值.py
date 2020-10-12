# !/usr/bin/python
# -*- coding: utf-8 -*-
"""
@File    :  Dict赋值.py
@Time    :  2020/10/12 16:35
@Author  :  ZPC
@Version :  1.0
@Contact :  zhoupengcheng03@corp.netease.com
@License :  (C)Copyright 2019-2020
@Desc    :  None
"""
if __name__ == '__main__':
    cond = {}
    gid = '1'
    oid = '2'
    if gid:
        cond["gid"] = gid
    if oid:
        cond["oid"] = oid

    # 使用内置函数locals() 获取局部变量
    vardict = locals()
    for key in ["gid", "oid"]:
        value = vardict.get(key)
        if value:
            cond[key] = value