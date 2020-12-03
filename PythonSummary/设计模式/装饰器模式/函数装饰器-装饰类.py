# !/usr/bin/python
# -*- coding: utf-8 -*-
"""
@File    :  函数装饰器-装饰类.py
@Time    :  2020/12/3 17:42
@Author  :  ZPC
@Version :  1.0
@Contact :  zhoupengcheng03@corp.netease.com
@License :  (C)Copyright 2019-2020
@Desc    :  None
"""

# ================================例子1================================
def Singleton(cls):  # 这是第一层函数，相当于模板中的Decorator.目的是要实现一个“装饰器”，而且是对类型的装饰器
    '''
    cls:表示一个类名，即所要设计的单例类名称，
        因为python一切皆对象，故而类名同样可以作为参数传递
    '''
    instance = {}

    def singleton(*args, **kargs):  # 这是第二层，相当于wrapper，要匹配参数
        if cls not in instance:
            instance[cls] = cls(*args, **kargs)  # 如果没有cls这个类，则创建，并且将这个cls所创建的实例，保存在一个字典中
        return instance[cls]  # 返回创建的对象

    return singleton


@Singleton
class Student(object):
    def __init__(self, name, age):
        self.name = name
        self.age = age


s1 = Student('张三', 23)
s2 = Student('李四', 24)
print((s1 == s2))
print(s1 is s2)
print(id(s1), "     ", id(s2))

# ================================例子2================================
def ClassDecorator(cls):  # 第一层函数decorator
    height = 170
    weight = 65

    def wrapper(name, age):  # 第二层函数wrapper，参数要和类的构造函数匹配
        s = cls(name, age)
        s.height = height  # 添加两个额外属性
        s.weight = weight
        return s  # 返回创建的对象，因为类的构造函数是要返回实例的，即有返回值

    return wrapper


@ClassDecorator
class Student:
    def __init__(self, name, age):
        self.name = name
        self.age = age


stu = Student('张三', 25)
print(stu.name)
print(stu.age)
print(stu.height)  # 在 IDE中可能会有提示此处错误，学生没有height和weight属性，但是运行之后没错
print(stu.weight)  # 这就是python的魅力，动态添加属性