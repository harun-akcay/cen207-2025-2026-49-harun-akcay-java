@echo off
@setlocal enableextensions
@cd /d "%~dp0"

echo Running Application

cd inventorymanagement-app

if not exist "target\inventorymanagement-app-1.0-SNAPSHOT.jar" (
    echo JAR file not found. Building application...
    call mvn clean package -DskipTests
    if %ERRORLEVEL% NEQ 0 (
        echo ERROR: Build failed!
        pause
        exit /b 1
    )
)

if exist "target\inventorymanagement-app-1.0-SNAPSHOT.jar" (
    echo Starting application...
    java -jar target\inventorymanagement-app-1.0-SNAPSHOT.jar
) else (
    echo ERROR: JAR file not found at target\inventorymanagement-app-1.0-SNAPSHOT.jar
    echo Please run 7-build-app.bat first to build the application.
)

echo.
echo Operation Completed!
pause