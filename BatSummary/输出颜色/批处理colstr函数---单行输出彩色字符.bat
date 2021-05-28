:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::彩色字符控制输出{code by Jvive@cn-dos.net|2008-4-9}
::call:Colstr <attr> <sp> <"str"> <bk> <sp> <enter>  
::               |     |     |      |    |     |
::             颜色   空格  字符串   退格  空格 回车换行
::效率：      约18次/s  (XP 5.1/2.4GHz/256M)      
::批文件使用格式：
::       文件名 <attr> <sp> <"str"> <bk> <sp> <enter>
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::说明：
::   attr   16进位数字颜色属性。请参考16进位数字颜色属性配置演示代码。
::   sp     范围：{0，正整数} 表示输出空格的格数
::  "str"   要显示的字符串。注意当字符串中含有空格时带上双引号。
::   bk     范围：{正整数} 退格数，即光标左移位数,若设定左移位数为0，函数将强制转换为1
::   sp     范围：{0，正整数} 0将输出1个冒号，其他正整数表示空格数
::   enter  范围：{0，正整数} 0表示不换行（光标不动），其他正整数表示<enter>个数
::   字符串中可以包含除/\:*?,<>|"字符外的任何字符，但不可以全是“空格”或“.”等。
::   判断字符串是否合法的一个有效的办法是看它是否可以作为文件名来使用 
::   请勿试图尝试不合法的参数，函数只保证合法的参数有正确的效果。
::   另外，对某些特殊要求可以尝试用0100色（黑色）字符来“隐藏”冒号
::例：
::call:colstr 0c 3 "abcdef" 1 2 2
::用0c色（鲜红色）依次输出3个空格 abcdef: [光标左移1位] 2个空格 2个回车换行符。
::出处：http://www.cn-dos.net/forum/viewthread.php?tid=38940
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
@echo off
::Colstr函数演示。
mode con lines=24 cols=69
set s=I am from www.cn-dos.net

echo\&echo\&echo\&echo\&echo\&echo\&echo/
call:colstr e 15 "%s%" 0 1 0
call:colstr 0100 0 0 17 0 0
title 按任意键继续...&pause>nul
call:colstr a 1 "中国 DOS 联盟" 0 1 0
call:colstr c 0 "%s:~-15%" 0 1 9
pause>nul
call:colstr 2c 21 "谢谢测试!    有bug请吱一声" 0 1 9
title Jvive QQ∶275928264

pause>nul&exit/b
rem /*--------- colstr 函数 -------------
:Colstr <attr> <sp> <"str"> <bk> <sp> <enter>
for %%a in (+%2 +%4 +%5 +%6) do (
   if "%%a"=="+" echo 控制参数不能为空&exit/b
   if %%a lss +0 echo 参数越界-&exit/b
   if %%a geq +a echo 参数越界+&exit/b)
if %3 == "" echo 字符串不能为空&exit/b
pushd %tmp%&setlocal ENABLEEXTENSIONS
if exist "%~3?" del/a/q "%~3?">nul 2>nul
if %2 gtr 0 call:%0_bs %2 sp " "&call set/p=%%sp%%<nul
if %4 gtr 0 (call:%0_bs %4 bk "") else set "bk="
call:%0_bs %5 sp " "
set/p=%bk%%sp%<nul>"%~3"&findstr /a:%1 .* "%~3?" 2>nul
if not %6 equ 0 for /l %%a in (1 1 %6)do echo.
endlocal&popd&goto:eof
:Colstr_bs
set "p="&for /l %%a in (1 1 %1)do call set "p=%%p%%%~3"
set "%2=%p%"&goto:eof
rem ------------------------------------*/