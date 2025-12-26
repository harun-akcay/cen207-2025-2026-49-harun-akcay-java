# Fix coveragereport by regenerating it
Write-Host "Fixing coveragereport..."

Set-Location $PSScriptRoot

# Step 1: Stop any running Java processes (mvn site:run)
Write-Host "Stopping any running mvn site:run processes..."
Get-Process java -ErrorAction SilentlyContinue | Where-Object {
    $_.Path -like "*java*"
} | Stop-Process -Force -ErrorAction SilentlyContinue
Start-Sleep -Seconds 2

# Step 2: Remove old coveragereport directory
Write-Host "Removing old coveragereport directory..."
if (Test-Path "target\site\coveragereport") {
    Remove-Item "target\site\coveragereport" -Recurse -Force -ErrorAction SilentlyContinue
}
Start-Sleep -Seconds 1

# Step 3: Create new directory
Write-Host "Creating new coveragereport directory..."
New-Item -ItemType Directory -Path "target\site\coveragereport" -Force | Out-Null

# Step 4: Generate report in temp directory first
Write-Host "Generating report in temp directory..."
$tempDir = "target\site\coveragereport_temp"
if (Test-Path $tempDir) {
    Remove-Item $tempDir -Recurse -Force -ErrorAction SilentlyContinue
}
New-Item -ItemType Directory -Path $tempDir -Force | Out-Null

reportgenerator "-reports:target/site/jacoco/jacoco.xml" "-sourcedirs:src/main/java" "-targetdir:$tempDir" -reporttypes:Html

if ($LASTEXITCODE -eq 0) {
    Write-Host "Report generated successfully in temp directory."
    
    # Step 5: Copy files to final location
    Write-Host "Copying files to final location..."
    Copy-Item "$tempDir\*" -Destination "target\site\coveragereport\" -Recurse -Force
    
    # Step 6: Clean up temp directory
    Remove-Item $tempDir -Recurse -Force -ErrorAction SilentlyContinue
    
    Write-Host "Coveragereport fixed successfully!"
    Write-Host "You can now run: mvn site:run"
} else {
    Write-Host "Error generating report. Make sure reportgenerator is in PATH."
}

