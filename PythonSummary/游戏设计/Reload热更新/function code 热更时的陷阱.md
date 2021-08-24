# function code 热更时的陷阱

## reload
function code做的事情，可以总结为：
1. 将希望reload的模块，从 sys.modules 中 pop 出去

2. 对于每一个希望reload的模块，调用 __import__ 函数重新加载

这里就存在了上文[import机制](import.md)阐述的情况：当执行 from A.B import C 这类 import_from 语句时，不会真的发生重新加载的情况！  
因为 function code 热更时，并不会创建新的Module，而是在原有的Module上执行reload操作。  
于是 A 模块里已经有 B 模块的引用，B 模块里也有 C 模块的引用，所以执行 from A.B import C 时，不会真正的重新加载C模块

## 修正
在热更新前，首先将模块间的互相引用删除，这样 import_from 就不会无辜的忽略掉某个模块了：  
```
def remove_old_modules(module_names):
        for name in module_names:
        tmp = []
        m = sys.modules[name]
        for k, v in m.__dict__.iteritems():
            if inspect.ismodule(v):
                tmp.append(k)

        for k in tmp:
            del m.__dict__[k]
```
在load一个模块时，显式的reload其引用的所有其他模块
```
class finder(object):
	def setup_old_module(self, name):
		old_module = self.get_old_module(name)
		if not old_module:
			return

		old_module.__dict__.pop('_reload_all', None)
		sys.modules[name] = old_module  # for imp.load_module to reload the module

		for k, v in old_module.__dict__.iteritems():
			if inspect.ismodule(v):
				name = getattr(v, "__name__", "")
				if name not in sys.modules:
					self.get_module(name, None)ne)
```