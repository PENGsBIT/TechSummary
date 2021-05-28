#Python 并发编程
最简单的进程池
```python
from concurrent.futures import ProcessPoolExecutor
import  time
def task(name):
    print("name",name)
    time.sleep(1)

if __name__ == "__main__":
    start = time.time()
    ex = ProcessPoolExecutor(2)

    for i in range(5):
        ex.submit(task,"safly%d"%i)
    ex.shutdown(wait=True)

    print("main")
    end = time.time()
    print(end - start)
```
```
E:\python\python_sdk\python.exe "E:/python/py_pro/4 进程池.py"
name safly0
name safly1
name safly2
name safly3
name safly4
main
run time:3.212218999862671
```
ProcessPoolExecutor（2）创建一个进程池，容量为2，循环submit出5个进程，然后就在线程池队列里面，执行多个进程，
ex.shutdown(wait=True)意思是进程都执行完毕，在执行主进程的内容
## shutdown
进程都执行完毕，在执行主进程的内容
上述如果改为ex.shutdown(wait=False)
```
main
0.01500844955444336
name safly0
name safly1
name safly2
name safly3
name safly4
```
## 使用submit异步调用
异步调用: 提交/调用一个任务，不在原地等着，直接执行下一行代码
```python

# from multiprocessing import Process,Pool
from concurrent.futures import ProcessPoolExecutor
import time, random, os

def piao(name, n):
    print('%s is piaoing %s' % (name, os.getpid()))
    time.sleep(1)
    return n ** 2


if __name__ == '__main__':
    p = ProcessPoolExecutor(2)
    objs = []
    start = time.time()
    for i in range(5):
        obj = p.submit(piao, 'safly %s' % i, i)  # 异步调用
        objs.append(obj)

    p.shutdown(wait=True)
    print('main', os.getpid())
    for obj in objs:
        print(obj.result())

    stop = time.time()
    print(stop - start)
```
## result
result()是阻塞式的，所以返回结果的到来以后才继续进行下面的代码
```python
from concurrent.futures import ProcessPoolExecutor
import time
import random
# executor = ThreadPoolExecutor()
executor = ProcessPoolExecutor()


def sayhello(k, v):
	x = "hello: "
	for i in xrange(2):
		if i:
			x += ' v:' + v
		else:
			x += 'k:'+k
		time.sleep(random.randint(1, 3))

	print('result {} in thread:{}'.format(x , os.getpid()))

	return x


def main():
	start3 = time.time()
	seed = {"a": "a", "b": "a", "q": "a", "w": "a", "e": "a", "r": "a", "t": "a", "y": "a", "u": "a", "i": "a",
	        "o": "a", "p": "a", "s": "a", "d": "f", "g": "a", "h": "l"}

	futures = [executor.submit(sayhello, k, v) for k, v in seed.iteritems()]

	for i in futures:
		print('i', i.done())
		print i.result()


# cnt = len(futures)
# for future in futures:
# 	data = future.result()
# 	print('data:', data)
# 	cnt -= 1
# 	print('cnt:', cnt)
# 	if cnt == 0:
# 		print('all compeleted')
# 		end3 = time.time()
# 		print("run_time: " + str(end3 - start3))


if __name__ == '__main__':
	main()
	print 'end'
```