#Python3 dict优化
Python3.6之后，修改了dict的数据结构，内存利用率有一定提升。（有些文章称之为compact dict）

原理如下：

假设字典的数据为：
 
    d = {'timmy': 'red', 'barry': 'green', 'guido': 'blue'}
 
旧dict的数据布局如下，每个entry的内容是hash value，key pointer、value pointer，在64bit机器上，占用3 * 8 = 24bytes。查找数据时，根据key计算出hash值，取模，得到entries的下标，获取相应内容（实际还要考虑hash值冲突）
 
    entries = [['--', '--', '--'],
               [-8522787127447073495, 'barry', 'green'],
               ['--', '--', '--'],
               ['--', '--', '--'],
               ['--', '--', '--'],
               [-9092791511155847987, 'timmy', 'red'],
               ['--', '--', '--'],
               [-6480567542315338377, 'guido', 'blue']]
 
新dict加了一个中间层indices数组，用于记录数据在entries下标。entries的数据布局变为更稠密(中间没有空洞)。查找数据时，也是根据key，计算出hash值，取模，而这里得到的是indices的下标，再通过其值，得到entries的下标，从而获取key对应的数据。
```
indices =  [None, 1, None, None, None, 0, None, 2]
entries =  [[-9092791511155847987, 'timmy', 'red'],
           [-8522787127447073495, 'barry', 'green'],
           [-6480567542315338377, 'guido', 'blue']]

```
 
indices数组中的整数，会按照数组的大小使用变长整数(int8、int16、int32、int64)表示。
当dict元素数量 > 容量的2/3，就会发生扩容。 详见CPython的dictresize()函数。
dict的容量(即len(indices ))规则：2^n（2的指数倍）。选这个规则，[原因之一](Python3 dict优化.py)：根据容量大小计算出mask值的bit位末尾都是1，这样在计算key的hash值，计算放在哪个slot(槽位)，可以直接用与运算，而非取模运算，可以加快速度。
entries 的大小(即len(entries))规则：2/3的dict容量，即len(entries ) == 2/3*len(indices)。
总结起来，新dict节省内存的原因：

(1)indices数组的整数采用变长整数表示；

(2)entries的大小是dict容量的2/3;

注：

(1)使用del删除dict数据，不会触发缩容;

(2)使用clear()清空dict，会触发缩容;
## 新dict优点
* 节省内存。
* 数据遍历是有序的(即按插入数据的顺序遍历)，功能相当于OrderedDict。
* 数据遍历操作变快，例如:keys(), values(), items()操作。entries数据分布比较密集，中间没有空洞，遍历时，有助于CPU cache预加载。(详见：[Python-Dev] Guarantee ordered dict literals in v3.7?)
* hash表扩容变快。当entries采用combined table时，使用memcpy()函数进行数据搬移，而memcpy()使用了4字节或8字节的指令批量进行数据搬移，比较快。而旧dict，在数据搬移时，需要过滤value为NULL的数据。(详见：glibc--memcpy源码分析)
* 使用内存比较少，有助于减少内存碎片。  

关于新dict的详细介绍，可以看下面的文章：


[[Python-Dev] More compact dictionaries with faster iteration](https://mail.python.org/pipermail/python-dev/2012-December/123028.html)

[[Python-Dev] Guarantee ordered dict literals in v3.7?](https://mail.python.org/pipermail/python-dev/2017-December/151283.html)

[python3.7源码分析－字典](https://blog.csdn.net/qq_33339479/article/details/90446988)

新dict的python实现代码：

[PROOF-OF-CONCEPT FOR A MORE SPACE-EFFICIENT, FASTER-LOOPING DICTIONARY (PYTHON RECIPE)](https://code.activestate.com/recipes/578375/)