# python import的一些机制

## import
示例见[import机制](import机制.py)
从 sys.modules 中 pop 掉 "a.b"，再次import，
竟然没有再次放入 sys.modules。也没有报任何异常，一切看起来都是OK的。
但“a.b"并没有再次转载入sys中  
“import a.b as b” 之后才发现可以找到

## 原理
Python中的import方式分为2种：
```
import_stmt: import_name | import_from
import_name: 'import' dotted_as_names
import_from: ('from' ('.'* dotted_name | '.'+)
              'import' ('*' | '(' import_as_names ')' | import_as_names)
```
第一种： import A.B.C。称之为 import_name。这种情况比较简单，只有1个参数："A.B.C"， 称之为 module。

第二种：from A.B import C, D。称之为 import_from。这种情况下就有2个参数： "A.B" 称之为 module，"C, D" 称之为 names (代码里也表示为 fromlist)。

Python在执行 import 加载模块时，对上述2种情况中的module，都是先检查 sys.modules 是否存在，不存在就重新加载。  
（参见 Import.c (line 2668) : import_submodule(...) 函数中检查sys.modules中是否存在）。  
但对于 import_from 中的 names，是通过 ensure_fromlist 函数来处理的，这个函数并不是立刻从 sys.modules 中查找是否存在，而是优先在刚才加载的module中查找。  
（参见 Import.c (line 2273) : import_module_level(..)函数中调用 ensure_fromlist）（参见 Import.c (line 2597) : ensure_fromlist 中从刚加载的module中判断是否存在）。

### 重点在上面例子中的第二个import：
[import机制](import机制.py)
```
sys.modules.pop("a.b")
from a import b
```
1.首先加载 a， sys.modules 中不存在 a，则从文件里加载 a  
2.<font color=red>**然后从 a 里面检查是否有 b**</font>，没有，从sys.modules中检查，也没有，则从文件里加载 b  
3.加载成功后，<font color=red>**a 中有了 b 的引用：a.b is b。**</font>
```
sys.modules.pop("a.b")
from a import b
```
4.这时候，a 已经存在于 sys.modules 中了，所以不会再次加载  
5.对于 b 是否存在优先检查 a.b 是否存在，此时 a.b 是存在的，所以 b 也不会再次被加载（当 a.b 不存在时，才会检查 sys.modules）  
6.于是一切都结束以后， sys.modules 中是没有 "a.b" 模块的
```
import a.b
```
7.这次就会到 sys.modules 中检查 "a.b" 是否存在了，由于不存在，则会重新加载 b

总结：

import A.B.C 这种 import_name 的形式，只会在 sys.modules 中检查是否存在，不存在则重新加载。

from A.B import C 这种 import_from 的形式，module的部分和 import_name 的形式是一样的，但 names 或 fromlist 的部分是通过 ensure_fromlist 函数处理的，此函数优先检查刚才加载的module中是否存在，如果不存在才会再执行和 import_name 相同的加载逻辑。  

