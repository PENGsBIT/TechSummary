# Blog-topk
总结三种topK方法以及时间复杂度分析

## 方法1
先将原始数据排序,然后截取前k个数.
时间复杂度：O(n*logn)+O(k)=O(n*logn)。

## 方法2
最小堆.

维护容量为k的最小堆.根据最小堆性质,堆顶一定是最小的,如果小于堆顶,则直接pass,如果大于堆顶,则替换掉堆顶,并heapify整理堆,其中heapify的时间复杂度是logk.
时间复杂度:O(k+(n-k)*logk)=O(n*logk)

## 方法3
Quick select算法通常用来在未排序的数组中寻找第k小/第k大的元素。其方法类似于Quick sort。Quick select和Quick sort都是由Tony Hoare发明的，因此Quick select算法也被称为是Hoare's selection algorithm。
Quick select算法因其高效和良好的average case时间复杂度而被广为应用。Quick select的average case时间复杂度为O(n)，然而其worst case时间复杂度为O(n^2)。

## Quick Select 复杂度分析
一般来说，因为我们才用了随机选取pivot的过程，平均来看，我们可以假设每次pivot都能选在中间位置。设算法时间复杂度为T(n)。在第一层循环中，我们将pivot与n个元素进行了比较，这个时间为cn 。
所以第一步时间为：T(n) = cnc + T(n/2)。其中T(n/2)为接下来递归搜索其中一半的子数组所需要的时间。
在递归过程中，我们可以得到如下公式：  
T(n/2) = c(n/2) + T(n/4)  
T(n/4) = c(n/4) + T(n/8)  
...  
T(2) = 2*c + T(1)  
T(1) = 1*c     
将以上公式循环相加消除T项可以得到：  
T(n) = c(n + n/2 + n/4 + n/8 + ... + 2 + 1) = 2n = O(n)     
因此得到Quick Select算法的时间复杂度为O(n)。
## Quick Select 空间复杂度
算法没有使用额外空间，swap操作是inplace操作，所以算法的空间复杂度为O(1)。



