# coding=utf-8

# 计算下标，通过&运算取模
# 为什么和（len - 1）&运算呢？
# 容器大小是2的次幂，（newCap - 1）转化成二进制后，后面位数全是1，
# 这样可以进行取模了，与运算速度快
# 设计确实非常的巧妙，既省去了重新计算hash值的时间，
# 而且同时，由于新增的1bit是0还是1可以认为是随机的，因此resize的过程，
# 均匀的把之前的冲突的节点分散到新的bucket了

list_length = 8
for i in xrange(10):
	stri = str(i)
	str_in_list_index = hash(stri) & (list_length - 1)
	print str_in_list_index
# 1
# 0
# 3
# 2
# 5
# 4
# 7
# 6
# 1
# 0