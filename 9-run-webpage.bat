@echo off
@setlocal enableextensions
@cd /d "%~dp0"

cd inventorymanagement-app

echo Stopping any running Java processes...
taskkill /F /IM java.exe >nul 2>&1
timeout /t 3 /nobreak >nul

echo Running tests and generating Jacoco report...
call mvn test jacoco:report
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Tests failed or Jacoco report generation failed.
    pause
    exit /b 1
)

echo Running mvn site...
call mvn site -DskipTests=true
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Site generation failed.
    pause
    exit /b 1
)

echo Generating coverage report after mvn site...
if exist "target\site\jacoco\jacoco.xml" (
    echo Stopping any running Java processes...
    taskkill /F /IM java.exe >nul 2>&1
    timeout /t 2 /nobreak >nul
    
    if exist "target\site\coveragereport" (
        echo Removing old coveragereport directory...
        rd /S /Q "target\site\coveragereport"
        timeout /t 1 /nobreak >nul
    )
    
    echo Generating new coverage report...
    call reportgenerator "-reports:target\site\jacoco\jacoco.xml" "-sourcedirs:src\main\java" "-targetdir:target\site\coveragereport" -reporttypes:Html
    if %ERRORLEVEL% NEQ 0 (
        echo WARNING: Coverage report generation failed, but continuing...
    ) else (
        echo Coverage report generated successfully.
    )
) else (
    echo ERROR: Could not generate coverage report. Jacoco XML not found.
    pause
    exit /b 1
)

echo.
echo Starting web server...
echo Web site will be available at: http://localhost:9000/
echo To stop the server, press CTRL+C
echo.
timeout /t 2 /nobreak >nul
start http://localhost:9000/
call mvn site:run -DskipTests=true

echo.
echo Web server stopped.
pause
