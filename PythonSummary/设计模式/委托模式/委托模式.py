#!/usr/bin/env python 
# -- coding: utf-8 -- 
# Time : 2021/12/2 17:11
# Author : zhou pengcheng
# ProjectName: PythonSummary


# 现在有一个名为Dog的类对象，我们希望通过它调用 voice 类产生“狗吠 ” 声，这时候就可以采用委托模式。
class Dog:
	def __init__(self, voice):
		self.voice = voice

	def __getattr__(self, name):
		"""
		重写__getattr__方法
		获得相应的属性

		Arguments:
			name {str} -- 目标变量/函数名

		Returns:
			attr -- 处理者的变量/函数
		"""

		attr = getattr(self.voice, name)

		if not callable(attr):
			return attr

		def wrapper(*args, **kwargs):
			return attr(*args, **kwargs)

		return wrapper


class voice:
	def __init__(self):
		self.p1 = 'test'

	def words(self, something):
		print("voice: %s" % something)
		return "voice: %s" % something


if __name__ == '__main__':
	John = Dog(voice())
	John.words('汪汪')
# 实际上，如果你不重写__getattr__，一样可以用以下的方式调用到voice类：
#
# if __name__ == '__main__':
#     John = Dog(voice())
#     John.voice.words('汪汪')
# 这两种有什么区别呢？使用委托模式，可以简化代码，优化可读性，你不需要再调用voice对象, 委托者自己会利用 __getattr__ 找到相应的对象里的方法。