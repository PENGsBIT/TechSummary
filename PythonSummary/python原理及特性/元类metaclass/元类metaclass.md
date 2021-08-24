#元类metaclass
在wiki上面，metaclass是这样定义的：In object-oriented programming,
a metaclass is a class whose instances are classes.
Just as an ordinary class defines the behavior of certain objects,
a metaclass defines the behavior of certain classes and their instances.

也就是说metaclass的实例化结果是类，而class实例化的结果是instance。可以这么理解的：
metaclass是类似创建类的模板，所有的类都是通过他来create的(调用__new__)，这使得你可以自由的控制
创建类的那个过程，实现你所需要的功能。

```
#当定义class的时候，我们可以使用__metaclass__ attribute来指定用来初始化当前class的metaclass。如下面的例子所示：
class Foo(object):
    __metaclass__ = something
    [other statements...]
#当Python试图创建class Foo的时候，Python会首先在class的定义中寻找__metaclass__ attribute。如果存在__metaclass__，
#Python将会使用指定的__metaclass__来创建class Foo。如果没有指定的话，Python就会使用默认的type作为metaclas创建Foo。
#如果class定义中不存在__metaclass__的话，Python将会寻找MODULE级别的__metaclass__。
```

__metaclass__可以是任何Python的callable，不必一定是一个正式的class。
```python
# the metaclass will automatically get passed the same argument 
# that is passed to `type()`
def upper_attr(class_name, class_parents, class_attr):
    '''Return a class object, with the list of its attribute turned into 
    uppercase.
    '''
    # pick up any attribute that doesn't start with '__' and turn it into uppercase.
    uppercase_attr = {}
    for name, val in class_attr.items():
        if name.startswith('__'):
            uppercase_attr[name] = val
        else:
            uppercase_attr[name.upper()] = val
    
    # let `type` do the class creation
    return type(class_name, class_parents, uppercase_attr)


class Foo(object):
    # this __metaclass__ will affect the creation of this new style class
    __metaclass__ = upper_attr
    bar = 'bar'


print(hasattr(Foo, 'bar'))
# False

print(hasattr(Foo, 'BAR'))
# True

f = Foo()
print(f.BAR)
# 'bar'

```
接下来我们通过继承type的方式实现一个真正的class形式的metaclass。

```python
class UpperAttrMetaclass(type):
	def __new__(mcs, class_name, class_parents, class_attr):
		uppercase_attr = {}
		for name, val in class_attr.items():
			if name.startswith('__'):
				uppercase_attr[name] = val
			else:
				uppercase_attr[name.upper()] = val
		# basic OOP. Reuse the parent's `__new__()`
		# == type.__new__(mcs, class_name, class_parents, uppercase_attr)
		return super(UpperAttrMetaclass, mcs).__new__(mcs, class_name, class_parents, uppercase_attr)

class Foo(object):
	# this __metaclass__ will affect the creation of this new style class
	__metaclass__ = UpperAttrMetaclass
	bar = 'bar'
```

metaclass主要的使用情况就是用来创建API。使用metaclass的一个典型的例子是Django ORM。
```python
class Person(models.Model):
    name = models.CharField(max_length=30)
    age = models.IntegerField()
guy = Person(name='bob', age='35')
print(guy.age)
```
其并不会返回一个IntegerField对象，而是会返回一个int，甚至可以直接从数据库中调用这个值。

正是因为models.Model定义了__metaclass__，并使用了一些操作来将我们使用简单的语句定义的Person转化成了与数据库相应的域相联系的类，这种逻辑才成为可能。