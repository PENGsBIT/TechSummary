#MongoDB事务（Transactions）
https://docs.mongodb.com/manual/core/transactions/
https://www.jianshu.com/p/21565744ada6
# 事务
事务成功提交之前，所有的数据变更在事务之外不可见；

* 事务中的任何一项操作失败后，所有的变更会被丢弃，事务回滚；

* 支持事务，需要保证使用的是 MongoDB 4.0 或以上版本，并且 featureCompatibilityVersion 需要设置为 4.0。从旧版本升级上来的复本集群可能为 3.6，需要特别注意。可以通过如下命令检查：
db.adminCommand( { getParameter: 1, featureCompatibilityVersion: 1 } )

* 只有 wiredTiger 引擎支持事务。同时请留意，4.0 已经标记 MMAPv1 为 Deprecated，并且将会在未来的某一版本移除对 MMAPv1 引擎的支持；
## 单文件支持事务，多文件和分布式事务4.0
mongo事务和会话(Sessions)关联，一个会话同一时刻只能开启一个事务操作，当一个会话断开，这个会话中的事务也会结束。
```
var session = db.getMongo().startSession();
session.startTransaction({
   readConcern: { level: <level> },
   writeConcern: { w: <value>, j: <boolean>, wtimeout: <number> }
});
var coll = session.getDatabase('test').getCollection('user');

coll.update({name: 'Jack'}, {$set: {age: 18}})

// 成功提交事务
session.commitTransaction();

// 失败事务回滚
session.abortTransaction();

```
# readConcern（读关注）
多文档事务支持 local、majority、snapshot 三个 read concern 设置，其中 snapshot 主要是在 majority 的基础上实现了对因果一致性的保证；  
请留意：  
1.readConcern 的设置是事务级别的，不能对事务内的操作单独设置 readConcern；  
2.snapshot 设置只在多文档事务中支持，如：session 并不支持设置 readConcern 为 snapshot；  
# Transactions and Write Concern（写关注）
```writeConcern: { w: <value>, j: <boolean>, wtimeout: <number> }```  
您可以在事务开始时设置事务级写关注：  
* 如果事务级写关注未设置，则事务级写关注默认为提交的会话级写关注。
* 如果事务级写关注和会话级写关注未设置，事务级写关注默认为客户端级写关注
Write conern支持以下值：
0|1|N|majority  
当w:0为Unacknowledged(无回执),会出现的数据丢失情况。（因为{writeConcern:{w:0}}导致出现异常时并没有返回错误信息给客户端。）
当w:1为Acknowledged。在日志写完之后返回确定信息，虽然解决了w:0出现的数据丢失问题，但是w:1时如果出现系统崩溃也会导致数据丢失，那就是在日志信息还没有刷新到磁盘的那一刻发生系统宕机，此时内存日志的确是写入成功了于是mongodb就会返回确定信息。
当j:1为Journal。当j:1时可以解决w:1数据丢失的问题，但是随之而来的是Mongodb的性能会下降。虽然解决了服务器宕机时数据丢失问题（会丢失60s左右的数据，就是一次间隔没有刷新到磁盘的数据）。
当w:2/N/majority replica Acknowledged。等待数据复制到n个节点再发送回执。majority是“多数”在提交已应用于多数 (M) 投票成员后返回确认； 即提交已应用于主要和（M-1）投票次要。

