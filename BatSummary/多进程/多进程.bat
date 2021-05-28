@echo off
    setlocal enableextensions disabledelayedexpansion

    for %%t in ("%temp%\%~nx0.%random%%random%%random%.tmp") do (

        echo Starting subprocesses
        9> "%%~ft" (
            start "" cmd /c subprocess1.bat ^>log1.log
            start "" cmd /c subprocess2.bat ^>log2.log
            start "" cmd /c subprocess3.bat ^>log3.log
            start "" cmd /c subprocess4.bat ^>log4.log
            start "" cmd /c subprocess5.bat ^>log5.log
        )

        echo Waiting for subprocesses to end
        break | >nul 2>nul (
            for /l %%a in (0) do @(ren "%%~ft" "%%~nxt" && exit || ping -n 2 "")
        )
        echo Done

    ) & del "%%~ft"