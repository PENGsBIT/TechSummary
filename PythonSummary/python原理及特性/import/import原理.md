# import原理
## 包(package)和模块(module)
包是指带__init__.py的目录，模块是指单个.py文件。
## import xxx
1.首先看sys.modules（sys.modules是一个全局字典，该字典是python启动后就加载在内存中。每当程序员导入新的模块，sys.modules将自动记录该模块。
当第二次再导入该模块时，python会直接到字典中查找，从而加快了程序运行的速度。它拥有字典所拥有的一切方法.）里是否已经存在xxx，
是则直接返回，否则遍历sys.path，查找加载名为xxx的python包或模块。  
2.加载可以认为就是执行对应的.py文件（对包来说就是__init__.py），并以执行的结果填充xxx的__dict__ 。
3.执行一个局部变量赋值。这样在import xxx的上下文里就能以xxx这个变量名来使用它了。
ps：注意xxx可以是com.org.cn这种复合形式，会按序加载。顺利的话com、com.org、com.org.cn都会放到sys.modules里，但是，注意，这种情况下上面最后的那个局部变量引用到的是com，也正因此com.org.cn这么写才不会报错。
## from xxx import yyy
先对xxx执行上述1，然后在xxx的__dict__ 里查找yyy，找到则跟上述1最后一步一样，执行一个局部变量赋值。  
如果没找到，而xxx又是一个package，则尝试在xxx的__path__下加载yyy模块，加载成功的话会把yyy加到xxx的__dict__ ，
但是，**注意，加到sys.modules里的key不是yyy，而是xxx.yyy。**  
## 错误例子
1.循环import
加载模块是先在sys.modules里占了个坑，再执行.py文件来填充这个模块的__dict__，所以在b.py里import a的时候，
其实直接从sys.modules里返回了一个a的半成品，那个半成品只走到了from b import b_var那一步，还没设置a_var，所以就报错了。
```python
#a.py
from b import b_v
a_v='1'
# b.py
from a import a_v
b_v='2'

```
2.同一个module存在两份不同的实例
from xxx import yyy，并不是把yyy放到sys.modules里。胡乱修改sys.path 就可能导致这个错误
```python
#com/sub/a.py
from com.sub import a
>>>a
<moudule 'com.sub.a' from 'com/sub/a,py'>
import sys
sys.path.append('./com/sub')
import a as a2
>>>a2
<moudule 'a' from './com/sub/a,pyc'>
a==a2
>>>False
```