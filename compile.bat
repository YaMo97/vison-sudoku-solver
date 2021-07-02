cls 

@ECHO OFF

goto COMPILE
END

:COMPILE
echo "Compiling..."
cd build 
javac ..\*.java -d .
goto END

:END
pause
EXIT /B %ERRORLEVEL%