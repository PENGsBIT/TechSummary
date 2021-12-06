#MongoDB和MySQL的区别
##mongodb的所有数据实际上是存放在硬盘的，所有要操作的数据通过mmap的方式映射到内存某个区域内
mmap 系统调用使得进程之间通过映射同一个普通文件实现共享内存。普通文件被映射到进程地址空间后，进程可以像访问普通内存一样对文件进行访问，不必再调用。 read()，write（）等操作。mmap 并不分配空间, 只是将文件映射到调用进程的地址空间里, 然后你就可以用 memcpy 等操作写文件, 而不用 write() 了.写完后用 msync() 同步一下, 你所写的内容就保存到文件里了
mysql硬盘数据库
## MongoDB的free schema。如果想给一个表结构添加一个字段，会很容易的。终于不会像 MySQL 上 alter table 那样提心吊胆了（因为如果表比较大，会很耗时的哦）。
MySQL，在表中添加一列时发生了什么（相同处理速度差不多）
根据指定的算法，该操作可涉及以下步骤：  

1.创建表的完整副本  
2.创建临时表，以处理并发数据操控语言 (DML) 操作  
3.重建此表的所有索引  
4.应用并发 DML 更改时应用表锁定  
5.减慢并发 DML 吞吐量  
MySQL对两者的操作应该是相同的。消耗可见是很大的。  
## 单文件支持事务，多文件和分布式事务4.0
mongo事务和会话(Sessions)关联，一个会话同一时刻只能开启一个事务操作，当一个会话断开，这个会话中的事务也会结束。
```
var session = db.getMongo().startSession();
session.startTransaction({readConcern: { level: 'majority' },writeConcern: { w: 'majority' }});
var coll = session.getDatabase('test').getCollection('user');

coll.update({name: 'Jack'}, {$set: {age: 18}})

// 成功提交事务
session.commitTransaction();

// 失败事务回滚
session.abortTransaction();

```