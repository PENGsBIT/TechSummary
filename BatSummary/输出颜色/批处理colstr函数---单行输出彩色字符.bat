:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::��ɫ�ַ��������{code by Jvive@cn-dos.net|2008-4-9}
::call:Colstr <attr> <sp> <"str"> <bk> <sp> <enter>  
::               |     |     |      |    |     |
::             ��ɫ   �ո�  �ַ���   �˸�  �ո� �س�����
::Ч�ʣ�      Լ18��/s  (XP 5.1/2.4GHz/256M)      
::���ļ�ʹ�ø�ʽ��
::       �ļ��� <attr> <sp> <"str"> <bk> <sp> <enter>
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::˵����
::   attr   16��λ������ɫ���ԡ���ο�16��λ������ɫ����������ʾ���롣
::   sp     ��Χ��{0��������} ��ʾ����ո�ĸ���
::  "str"   Ҫ��ʾ���ַ�����ע�⵱�ַ����к��пո�ʱ����˫���š�
::   bk     ��Χ��{������} �˸��������������λ��,���趨����λ��Ϊ0��������ǿ��ת��Ϊ1
::   sp     ��Χ��{0��������} 0�����1��ð�ţ�������������ʾ�ո���
::   enter  ��Χ��{0��������} 0��ʾ�����У���겻������������������ʾ<enter>����
::   �ַ����п��԰�����/\:*?,<>|"�ַ�����κ��ַ�����������ȫ�ǡ��ո񡱻�.���ȡ�
::   �ж��ַ����Ƿ�Ϸ���һ����Ч�İ취�ǿ����Ƿ������Ϊ�ļ�����ʹ�� 
::   ������ͼ���Բ��Ϸ��Ĳ���������ֻ��֤�Ϸ��Ĳ�������ȷ��Ч����
::   ���⣬��ĳЩ����Ҫ����Գ�����0100ɫ����ɫ���ַ��������ء�ð��
::����
::call:colstr 0c 3 "abcdef" 1 2 2
::��0cɫ���ʺ�ɫ���������3���ո� abcdef: [�������1λ] 2���ո� 2���س����з���
::������http://www.cn-dos.net/forum/viewthread.php?tid=38940
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
@echo off
::Colstr������ʾ��
mode con lines=24 cols=69
set s=I am from www.cn-dos.net

echo\&echo\&echo\&echo\&echo\&echo\&echo/
call:colstr e 15 "%s%" 0 1 0
call:colstr 0100 0 0 17 0 0
title �����������...&pause>nul
call:colstr a 1 "�й� DOS ����" 0 1 0
call:colstr c 0 "%s:~-15%" 0 1 9
pause>nul
call:colstr 2c 21 "лл����!    ��bug��֨һ��" 0 1 9
title Jvive QQ��275928264

pause>nul&exit/b
rem /*--------- colstr ���� -------------
:Colstr <attr> <sp> <"str"> <bk> <sp> <enter>
for %%a in (+%2 +%4 +%5 +%6) do (
   if "%%a"=="+" echo ���Ʋ�������Ϊ��&exit/b
   if %%a lss +0 echo ����Խ��-&exit/b
   if %%a geq +a echo ����Խ��+&exit/b)
if %3 == "" echo �ַ�������Ϊ��&exit/b
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