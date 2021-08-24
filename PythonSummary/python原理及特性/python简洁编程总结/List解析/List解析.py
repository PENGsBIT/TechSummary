# !/usr/bin/python
# -*- coding: utf-8 -*-
"""
@File    :  List解析.py
@Time    :  2020/10/12 15:30
@Author  :  ZPC
@Version :  1.0
@Contact :  zhoupengcheng03@corp.netease.com
@License :  (C)Copyright 2019-2020
@Desc    :  None
"""

if __name__ == '__main__':
    newList = []  # 先定义一个空列表
    for i in range(11):
        newList.append(i * 2)  # 将每个元素都乘以2
    print(newList)
    # 列表解析式：
    print([i * 2 for i in range(11)])

    project_name = '1'
    data_list = []
    all_data = None
    # 没有使用List解析的时候，使用for in 循环
    for data in data_list:
        if data.get("project_name") and data.get("project_name") == project_name:
            all_data += data
    # 使用了List解析写在一行中：
    all_data += [data for data in data_list if (data.get("project_name") and data.get("project_name") == project_name)]


    # 没有使用List解析的时候，使用if else 循环
    data_all = []
    for i in xrange(10):
        if i == 0:
            data_all.append(0)
        else:
            data_all.append(1)
    print(data_all)
    # 使用了List解析写在一行中：
    print([data if data == 0 else 1 for data in range(10)])