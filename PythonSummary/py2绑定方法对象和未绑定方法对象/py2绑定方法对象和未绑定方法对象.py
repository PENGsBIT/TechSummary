# !/usr/bin/python
# -*- coding: utf-8 -*-
"""
@File    :  py2绑定方法对象和未绑定方法对象.py
@Time    :  2021/4/16 10:55
@Author  :  ZPC
@Version :  1.0
@Contact :  zhoupengcheng03@corp.netease.com
@License :  (C)Copyright 2019-2020
@Desc    :  None
"""


class Foo(object):
    def foo():
        print 'call foo'

	def foo_one(self):
			print 'call foo_one'

Foo.foo() # TypeError: unbound method foo() must be called with Foo instance as first argument (got nothing instead)
Foo().foo() # TypeError: foo() takes no arguments (1 given)

Foo.foo #<unbound method Foo.foo>
Foo().foo #<bound method Foo.foo of <__main__.Foo object at 0x0000000002835278>>

Foo.foo_one() # TypeError: unbound method foo() must be called with Foo instance as first argument (got nothing instead)
Foo().foo_one() # print call foo_one