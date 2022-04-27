#!/usr/bin/env python 
# -- coding: utf-8 -- 
# Time : 2022/4/27 14:52
# Author : zhou pengcheng
# ProjectName: PythonSummary

# Python2.x中，默认都是经典类，只有显式继承了object的才是新式类，即：
class Person(object):pass    #新式类
class Person():pass          # 经典类

# 新式类和经典类（旧式类）的区别的在于子类多继承的情况下，经典类多继承搜索顺序是深度优先，新式类多继承搜索顺序是广度优先。
# 新式类相同父类只执行一次构造函数，经典类重复执行多次。

# 如果x是一个旧式类，那么x.__class__定义了x的类名，但是type(x)总是返回<type ‘instance’>。
# 这反映了所有的旧式类的实例是通过一个单一的叫做instance的内建类型来实现的，这是它和类不同的地方。
# 为什么要在2.2中引进new style class呢？官方给的解释是：
# 为了统一类(class)和类型(type)。
# 在2.2之前，比如2.1版本中，类和类型是不同的，如a是ClassA的一个实例，那么a.__class__返回 ‘ class    __main__.ClassA‘ ，type(a)返回总是<type 'instance'>。
# 而引入新类后，比如ClassB是个新类，b是ClassB的实例，b.__class__和type(b)都是返回‘class '__main__.ClassB' ，这样就统一了。