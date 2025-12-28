@echo off
@setlocal enableextensions
@cd /d "%~dp0"

cd inventorymanagement-app

echo Ensuring Jacoco XML exists...
if not exist "target\site\jacoco\jacoco.xml" (
    echo Jacoco XML not found. Running tests to generate it...
    call mvn test jacoco:report
)

echo Running mvn site first...
call mvn site

echo Generating coverage report after mvn site...
if exist "target\site\jacoco\jacoco.xml" (
    if exist "target\site\coveragereport" (
        rd /S /Q "target\site\coveragereport"
    )
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
echo Run Web Site
echo to Exit Use CTRL+Z CTRL+C
echo.
start http://localhost:9000/
call mvn site:run

echo Operation Completed!
pause
