@echo off
set time_begin=%time%
set /A time_begin_minute=%time_begin:~3,2%
set /A time_begin_second=%time_begin:~-5,2%
set /A time_begin_millisec=%time_begin:~-2,2%

ping -n 70 -w 1000 127.0.0.1 > nul
rem your program

set time_end=%time%
set /A time_end_minute=%time_end:~3,2%
set /A time_end_second=%time_end:~-5,2%
set /A time_end_millisec=%time_end:~-2,2%

if %time_end_millisec% lss %time_begin_millisec% set /A time_end_millisec+=100&set /A time_end_second-=1
if %time_end_second% lss %time_begin_second% set /A time_end_second+=60&set /A time_end_minute-=1

set /A minute=time_end_minute-time_begin_minute
set /A second=time_end_second-time_begin_second
set /A millisec=time2_millisec-time1_millisec

echo 程序运行开始时间:%time_begin%  结束时间:%time_end%
echo 程序运行时间为%minute%分%second%秒%millisec%毫秒

pause & exit
