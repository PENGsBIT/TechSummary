# 系统cmd编码 Blog
坑：系统编码问题有可能会导致log或者print 的中文乱码，导致相关的模块有可能报错  
系统cmd默认编码，使用
```commandline
chcp
```
查看936（GBK）  
需要将其转换为项目的encoding编码  
例如 utf-8
```commandline
chcp 65001
```
 