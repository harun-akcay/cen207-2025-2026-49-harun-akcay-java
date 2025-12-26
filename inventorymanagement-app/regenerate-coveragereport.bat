@echo off
echo Regenerating Coverage Report...
cd /d "%~dp0"
echo Stopping any running mvn site:run processes...
taskkill /F /IM java.exe /FI "WINDOWTITLE eq *site:run*" 2>nul
timeout /t 2 /nobreak >nul
echo Running ReportGenerator...
call reportgenerator "-reports:target/site/jacoco/jacoco.xml" "-sourcedirs:src/main/java" "-targetdir:target/site/coveragereport" -reporttypes:Html
if %ERRORLEVEL% EQU 0 (
    echo Coverage report regenerated successfully!
    echo You can now run: mvn site:run
) else (
    echo Error generating coverage report. Make sure:
    echo 1. mvn site:run is stopped
    echo 2. Browser is closed
    echo 3. reportgenerator is in PATH
)
pause

