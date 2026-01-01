@echo off
@setlocal enableextensions
@cd /d "%~dp0"

cd inventorymanagement-app

echo Stopping any running Java processes...
taskkill /F /IM java.exe >nul 2>&1
timeout /t 3 /nobreak >nul

echo Cleaning project...
call mvn clean
if %ERRORLEVEL% NEQ 0 (
    echo WARNING: Clean failed, but continuing...
    echo Stopping Java processes again...
    taskkill /F /IM java.exe >nul 2>&1
    timeout /t 2 /nobreak >nul
    echo Trying to manually remove problematic directories...
    if exist "target\site\doxygen\latex" (
        rd /S /Q "target\site\doxygen\latex" >nul 2>&1
    )
    if exist "target\site" (
        rd /S /Q "target\site" >nul 2>&1
    )
)

echo Running tests and generating site with JaCoCo report...
call mvn test site
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Tests or site generation failed.
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
echo Generating Doxygen documentation...
cd ..
if exist "Doxyfile" (
    echo Running doxygen...
    call doxygen Doxyfile
    if %ERRORLEVEL% NEQ 0 (
        echo WARNING: Doxygen generation failed, but continuing...
    ) else (
        echo Doxygen documentation generated successfully.
    )
) else (
    echo WARNING: Doxyfile not found. Skipping Doxygen generation.
)
cd inventorymanagement-app

echo.
echo Regenerating Coverxygen documentation coverage report after mvn site...
if exist "target\site\doxygen\xml" (
    echo Removing old coverxygen directory if it exists...
    if exist "target\site\coverxygen" (
        rd /S /Q "target\site\coverxygen"
        timeout /t 1 /nobreak >nul
    )
    
    echo Creating coverxygen directory...
    mkdir "target\site\coverxygen"
    
    echo Running coverxygen...
    cd ..
    echo Checking for coverxygen module...
    py -3.13 -m pip show coverxygen >nul 2>&1
    if %ERRORLEVEL% NEQ 0 (
        echo WARNING: coverxygen module not found. Trying to install...
        py -3.13 -m pip install coverxygen --quiet
        if %ERRORLEVEL% NEQ 0 (
            echo WARNING: Failed to install coverxygen. Skipping coverxygen generation.
        ) else (
            echo Coverxygen module installed successfully.
            call py -3.13 -m coverxygen --xml-dir ./inventorymanagement-app/target/site/doxygen/xml --src-dir ./inventorymanagement-app/src/main/java --format lcov --output ./inventorymanagement-app/target/site/coverxygen/lcov.info --prefix inventorymanagement-app/src/main/java/
            if %ERRORLEVEL% NEQ 0 (
                echo WARNING: Coverxygen generation failed, but continuing...
            ) else (
                echo Coverxygen LCOV file generated successfully.
                
                echo Fixing LCOV file paths...
                powershell -Command "$content = Get-Content 'inventorymanagement-app\target\site\coverxygen\lcov.info' -Raw; $lines = $content -split \"`n\"; $filtered = @(); $skip = $false; foreach ($line in $lines) { if ($line -match '^SF:.*(README\.md|\[generated\])') { $skip = $true } elseif ($line -match '^end_of_record') { if (-not $skip) { $filtered += $line } $skip = $false } elseif (-not $skip) { $filtered += $line } }; $fixed = ($filtered -join \"`n\") -replace 'inventorymanagement-app\\src\\main\\java\\inventorymanagement-app\\src\\main\\java', 'inventorymanagement-app\src\main\java' -replace 'C:\\[^:]+\\inventorymanagement-app\\src\\main\\java\\inventorymanagement-app\\src\\main\\java', 'inventorymanagement-app\src\main\java' -replace '^SF:C:\\[^:]+\\inventorymanagement-app\\src\\main\\java', 'SF:inventorymanagement-app\src\main\java'; Set-Content 'inventorymanagement-app\target\site\coverxygen\lcov.info' -Value $fixed"
                
                echo Running lcov genhtml...
                if exist "C:\ProgramData\chocolatey\lib\lcov\tools\bin\genhtml" (
                    call perl C:\ProgramData\chocolatey\lib\lcov\tools\bin\genhtml --legend --title "Documentation Coverage Report" ./inventorymanagement-app/target/site/coverxygen/lcov.info -o inventorymanagement-app/target/site/coverxygen
                    if %ERRORLEVEL% NEQ 0 (
                        echo WARNING: genhtml failed, but continuing...
                    ) else (
                        echo Coverxygen HTML report generated successfully.
                        echo Verifying index.html exists...
                        if exist "inventorymanagement-app\target\site\coverxygen\index.html" (
                            echo Coverxygen index.html verified!
                        ) else (
                            echo WARNING: index.html not found after generation!
                        )
                    )
                ) else (
                    echo WARNING: genhtml not found. Skipping HTML generation.
                )
            )
        )
    ) else (
        call py -3.13 -m coverxygen --xml-dir ./inventorymanagement-app/target/site/doxygen/xml --src-dir ./inventorymanagement-app/src/main/java --format lcov --output ./inventorymanagement-app/target/site/coverxygen/lcov.info --prefix inventorymanagement-app/src/main/java/
        if %ERRORLEVEL% NEQ 0 (
            echo WARNING: Coverxygen generation failed, but continuing...
        ) else (
            echo Coverxygen LCOV file generated successfully.
            
            echo Fixing LCOV file paths...
            powershell -Command "$content = Get-Content 'inventorymanagement-app\target\site\coverxygen\lcov.info' -Raw; $lines = $content -split \"`n\"; $filtered = @(); $skip = $false; foreach ($line in $lines) { if ($line -match '^SF:.*(README\.md|\[generated\])') { $skip = $true } elseif ($line -match '^end_of_record') { if (-not $skip) { $filtered += $line } $skip = $false } elseif (-not $skip) { $filtered += $line } }; $fixed = ($filtered -join \"`n\") -replace 'inventorymanagement-app\\src\\main\\java\\inventorymanagement-app\\src\\main\\java', 'inventorymanagement-app\src\main\java' -replace 'C:\\[^:]+\\inventorymanagement-app\\src\\main\\java\\inventorymanagement-app\\src\\main\\java', 'inventorymanagement-app\src\main\java' -replace '^SF:C:\\[^:]+\\inventorymanagement-app\\src\\main\\java', 'SF:inventorymanagement-app\src\main\java'; Set-Content 'inventorymanagement-app\target\site\coverxygen\lcov.info' -Value $fixed"
            
            echo Running lcov genhtml...
            if exist "C:\ProgramData\chocolatey\lib\lcov\tools\bin\genhtml" (
                call perl C:\ProgramData\chocolatey\lib\lcov\tools\bin\genhtml --legend --title "Documentation Coverage Report" ./inventorymanagement-app/target/site/coverxygen/lcov.info -o inventorymanagement-app/target/site/coverxygen
                if %ERRORLEVEL% NEQ 0 (
                    echo WARNING: genhtml failed, but continuing...
                ) else (
                    echo Coverxygen HTML report generated successfully.
                    echo Verifying index.html exists...
                    if exist "inventorymanagement-app\target\site\coverxygen\index.html" (
                        echo Coverxygen index.html verified!
                    ) else (
                        echo WARNING: index.html not found after generation!
                    )
                )
            ) else (
                echo WARNING: genhtml not found. Skipping HTML generation.
            )
        )
    )
    cd inventorymanagement-app
) else (
    echo WARNING: Doxygen XML directory not found. Skipping Coverxygen generation.
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
