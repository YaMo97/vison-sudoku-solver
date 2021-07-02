cls 

@ECHO OFF

goto COMPILE
END

:COMPILE
CALL .\compile.bat
IF %ERRORLEVEL% NEQ 0 ( 
    echo.
    echo "Compilation Failed..."
    echo.
    goto END
)

echo.
echo "Compiled Successfully..."
echo "Executing Program..."
echo.
goto RUN


:RUN 
java SudokuSolverScript
goto END

:END
pause
EXIT /B %ERRORLEVEL%