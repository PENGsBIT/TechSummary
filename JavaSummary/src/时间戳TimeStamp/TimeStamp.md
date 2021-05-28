# Blog-Time
总结三种JAVA获取时间戳

## 方法 一  
System.currentTimeMillis();  
 
## 方法 二  
Calendar.getInstance().getTimeInMillis();  

## 方法 三  
new Date().getTime();  

System.currentTimeMillis() 这种方式速度最快

Calendar.getInstance().getTimeInMillis() 这种方式速度最慢,看看源码会发现，Canlendar因为要处理时区问题会耗费很多的时间。

所以建议多使用第一种方式。