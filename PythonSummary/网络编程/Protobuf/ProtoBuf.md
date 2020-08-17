# Learn Google.ProtoBuf summary
(版本：python 2.7，protobuf2 和 protobuf3)
ProtoBuf 所生成的二进制文件在存储效率上比 XML 高 3~10 倍，并且处理性能高 1~2 个数量级
这是为什么选择 ProtoBuf 作为序列化方案的一个重要因素
## Protobuf 使用方法

1.安装 protoc: ProtoBuf基于 C++ 进行的实现, protoc作为编译器编译 *.proto
在python_out指定的path中生成 *_pb2.py 文件
```bash
$ protoc --python_out=. *.proto
```  
2.安装 ProtoBuf 相关的 python 依赖库
```$python
$ pip install protobuf
```

## ProtoBuf字段与方法
```protobuf
//声明使用的protobuf版本
syntax = "proto2";
//package的声明，为了帮助防止在不同的工程中命名冲突。在Python中，包通常由目录结构决定的，
// 所以这个由你的.proto文件定义的包，在你生成你代码中是没有效果的。  
//但是，你应该坚持声明这条语句，为了在protocol Buffers的命名空间中防止名子的冲突，就像其它非Python的语言那样  
package tutorial;

message Person {
//“＝1”，“＝2”标记每个元素的识别，作为二进制编码中字段的唯一的标签。标签要求数字1－15比更高的数字少一个字节编码，
//所以，作为最优化的方案，你可以决定对常用的和要重复使用的元素使用这些标签，把16或最高的数字留给不常用和可选择的元素。
  required string name = 1;
//修饰语修饰
//required：一定要提供一个值给这个字段，否则这条Message会被认为“没有初始化”。
  required int32 id = 2;
  optional string email = 3;

  enum PhoneType {
    MOBILE = 0;
    HOME = 1;
    WORK = 2;
  }

  message PhoneNumber {
    required string number = 1;
//optional：这个字段可以设置也可以不设置 。如果一个可选字段没有设置值，一个默认值将赋予该字段，也可以指定自己的默认值
    optional PhoneType type = 2 [default = HOME];
  }

  repeated PhoneNumber phone = 4;
}

message AddressBook {
//repeated：这个字段会重复几次一些号码（包括0）
//这个addressbook例子便有个很好的该修饰符的应用场景，即每个人可能有多个电话号码。(类似动态的数组)
  repeated Person person = 1;
}
```

```protobuf
syntax = "proto3";
package RpcMsg;
//使用any需要把any.protobuf放入当前目录用于编译
import "google/protobuf/any.proto";

message RpcPacket{
  //不需要关键字optional，每一个都是一个optional
  string method = 1;
  google.protobuf.Any args = 2;
}

message LoginInfoFromClient {
  string name = 1;
  string password = 2;
}

message LoginInfoFromServer {
  int32 feedbackCode = 2;
  string name = 3;
  int32 hid = 4;
}

```

##遇到的问题与解决方案
1.protobuf2 在optional的序列化没有[default = HOME]的话，反序列化就不会得到相应字段  
&ensp;-使用proto3就不会存在1的问题  
2.protobuf3 文件中使用any泛型，编译 not found "google/protobuf/any.proto"  
&ensp;-在官网上下载相应的protoc-x.xx.x-win64.zip，解压得到include中的[google](./google)文件，
    放在自己需要编译的.proto文件下