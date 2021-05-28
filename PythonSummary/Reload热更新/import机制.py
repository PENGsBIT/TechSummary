# !/usr/bin/python
# -*- coding: utf-8 -*-
"""
@File    :  import机制.py
@Time    :  2020/11/5 15:15
@Author  :  ZPC
@Version :  1.0
@Contact :  zhoupengcheng03@corp.netease.com
@License :  (C)Copyright 2019-2020
@Desc    :  None
"""
# 第一次测试很容易理解：import 的模块会存放在 sys.modules 里面。
#
# 但第二次测试就很出乎意料：从 sys.modules 中 pop 掉 "a.b"，再次import，竟然没有再次放入 sys.modules。也没有报任何异常，一切看起来都是OK的。
#
# 从第三次测试来看，a 模块中引用了 b 模块。
import sys
from a import b

print "a.b" in sys.modules
# True


sys.modules.pop("a.b")
from a import b
print "a.b" in sys.modules
# False

import a.b  # *** 这里发生了改动 ****
print "a.b" in sys.modules
# True

import a

print a.b
# <module 'a.b' from 'a\b.py'>
