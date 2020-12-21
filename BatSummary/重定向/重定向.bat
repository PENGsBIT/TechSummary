@echo off
echo hhhhhh 1>con.log rem 将清空原来的内容
echo hhhhhh 1>>con.log rem 不清空原来的内容，追加在末尾
echo hhhhhh >nul rem nul代表的是“空设备”,输出流重定向到空设备就相当于屏蔽掉了一样
echo hhhhhh 1>con 2>con  rem 意思是将echo命令的结果中的标准输出和标准错误输出输出到控制台con中
dir file.c 1> output.msg 2>&1  rem 用“2>&1”将标准错误输出重定向到标准输出。
start cmd /b python 1st.py arg1 arg2 ^> out.txt rem /b代表后台运行 /k代表不关闭 /c代表执行完以后关闭
rem ^>代表转义">"否则">"将作为python的参数传入脚本
start call delay.bat ^1^> log.txt ^2^>^&^1 rem 同理
start call delay.bat  ^>^> log.txt rem 同理
pause & exit
