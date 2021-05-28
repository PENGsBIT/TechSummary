# py2绑定方法对象和未绑定方法对象
本篇主要总结Python2中绑定方法对象（Bound method object）和未绑定方法对象（Unboud method object）的区别和联系。  
在Python 3.x中，unbound method的概念已经被取消了。取而代之的是 function对象。
但是不妨碍它让我窥探到了Python语言动态特性的一角


## 遇到的实际问题
是由于公司特有的组合类，导致在继承的子类通过super()调用父类方法的时候,由于绑定方法对象（Bound method object）
和未绑定方法对象（Unboud method object）的区别，***导致遇到 TypeError: unbound method xxx() must be called with Foo instance as 
first argument (got nothing instead)的问题。***  
因此特此查找资料，总结学习相关的~~姿势~~知识。

error重现见[py2绑定方法对象和未绑定方法对象.py](py2绑定方法对象和未绑定方法对象.py)
实际上
```
>>> type(Foo.foo)
<type 'instancemethod'>
>>> type(Foo().foo)
<type 'instancemethod'>
```
为什么同样是实例方法(instancemethod)，获取方式的不同，会导致获得不同的对象呢？

## bound/unbound method 由来
通过查看Foo.__dict__
```
>>> Foo.__dict__
{'__dict__': <attribute '__dict__' of 'Foo' objects>, '__module__': '__main__', 'foo': <function foo at 0x00000000032CF4A8>, '__weakref__': <attribute '__weakref__' of 'Foo' objects>, '__doc__': None}
>>> Foo.__dict__['foo']
<function foo at 0x7ff33b42a5f0>
```
在Python中使用描述器（有翻译的链接）来表示具有“绑定”行为的对象属性，使用描述器协议方法来控制对具有绑定行为属性的访问，这些描述器协议方法包括：
__get__()、__set__()和__delete__()。根据上面这段难以让人理解的描述，我们可以大胆的猜测，Foo的属性foo是一个描述器，它通过__get__()方法来控制对foo的访问。
根据描述器协议方法descr.__get__(self, obj, type=None) --> value，我们尝试如下：
```
>>> Foo.__dict__['foo'].__get__(None,Foo)
<unbound method Foo.foo>
```
也就是，调用Foo.foo时，Python会根据查找链从Foo.__dict__['foo']开始，然后查找type(Foo).__dict__['foo']，一路向上查找type(Foo)的所有基类。Foo.foo会被转换为Foo.__dict__['foo'].__get__(None,Foo)。  
***也就是说，我们在代码中使用Foo.foo实际上会被转化成 Foo.__dict__['foo'].__get__(None,Foo)***  
descr.__get__(self, obj, type=None)其中，self参数在这里被赋予了None，所以没有给定实例，因此认为是未绑定(unbound)  
那么一个很简单的推理就是：如果self参数给定了实例对象，那么，得到的就是bound method，如下。
```
>>> Foo.__dict__['foo'].__get__(Foo(),Foo)
<bound method Foo.foo of <__main__.Foo object at 0x7ff33b424d50>>

Foo.foo.im_self    # None
Foo().foo.im_self    # <__main__.Foo object at 0x0241E070>
```
因此在[py2绑定方法对象和未绑定方法对象.py](py2绑定方法对象和未绑定方法对象.py)中
同样使用类名.方法名()调用时，所报的错误相同。但是可以可以通过Foo.foo(Foo())这种方式来进行手动的绑定
但是在使用实例名.方法名()调用时，foo_one是可以调用成功的。  
原因在于当使用Foo().foo_one()调用时，Python做了如下修改：  
```
>>> Foo.foo_one(Foo())
call foo_one
```
  
所以 有的人把foo()这种参数列表中没有self的方法称为类方法，而把带有self的方法称为实例方法，根据上面的描述可以发现，这种划分是错误的。  

## Pyhon 类方法
例子见[py类方法.py](py类方法.py)
看到这里会发现，在Python中定义方法，总要带两个参数self或者cls。其中通过self限定的method必须使用实例才能调用。

## Python 静态方法
除了类方法，还有静态方法，请看下面这个例子：
```
>>> class Foo(object):
...     @staticmethod
...     def foo():
...             print 'call foo'
... 
>>> Foo.foo()
call foo
>>> Foo().foo()
call foo
```
静态方法可以通过类名.方法名()和实例.方法名()的形式调用。
查看type结果如下：
```
>>> type(Foo.foo)
<type 'function'>
```
可以看到，静态方法的类型是function，而类方法的类型是instancemethod。

## Python语言的动态特性
这么设计其实是为了实现Python的动态特性，我们来看一个例子：
```
class A(object):
    def foo(self):
        return 'A'

def foo(self):
    return 'B'

a = A()

print foo, A.foo.im_func, a.foo.im_func # <function foo at 0x0230A2B0> <function foo at 0x0239A4B0> <function foo at 0x0239A4B0>
A.foo = foo
print foo, A.foo.im_func, a.foo.im_func # <function foo at 0x0230A2B0> <function foo at 0x0230A2B0> <function foo at 0x0230A2B0>
print a.foo()   # B
print A.foo(a)  # B
```
这段代码其实是游戏中常用的hotfix的一种实现原来的demo。所谓hotfix，是指在玩家不知情的情况下，替换掉客户端脚本代码中的部分逻辑，实现修复bug的目的。  
对于静态语言，进行这种运行时修改代码比较麻烦，而且像ios这样的平台禁止了在数据段执行代码，也就是你无法动态替换dll，使用native的语言或者C#的几乎不能方便地进行hotfix，这也是脚本语言在游戏行业里（尤其国内）面非常常用的原因。  
上述例子中，A.foo = foo这句代码替换了A的__dict__中的foo对象，由于方法对象都是在使用时动态生成的，因此无论是新创建的对象还是已经在内存中存在的对象，都会在调用方法的时候重新生成方法对象，它们的im_func属性就指向了类中的新的属性对象。  

### 动态的代价，就是慢。
C++静态语言的方法调用，即使考虑继承的情况，也不过是一次虚表的查询操作，而python中不但有各种__dict__检索，而且要通过__mro__属性向继承的父类进行搜索，这块具体的过程在后面进行分析。然后加上对象的创建过程，影响效率可想而知。  
因此，我们在代码中常用的一种优化是：  
***如果在一段代码中有对于对象属性的频繁访问，在不会修改其内容的前提下，通常会使用一个局部变量保存属性的应用供后面的代码逻辑使用。***
