@echo off

echo BATCH_OUTPUT: Starting Docker Container Creation...

cd %~dp0

docker ps -q | findstr "." >nul
if %errorlevel% EQU 0 (
    echo BATCH_OUTPUT: Containers are already running. Skipping container creation.
    goto :CHECK_CONTAINER_READY
)

REM Containers are not running already, Starting them.
docker-compose up -d

:CHECK_CONTAINER_READY
echo BATCH_OUTPUT: Waiting for containers to be fully operational...
:WAIT_FOR_CONTAINER_READY
timeout /t 180 /nobreak >nul 2>&1
curl -I http://localhost:4444/ui  2>nul | findstr "200 OK" >nul
if %errorlevel% NEQ 0 (
    echo BATCH_OUTPUT: Containers not yet fully operational. Waiting...
    goto :WAIT_FOR_CONTAINER_READY
)

echo BATCH_OUTPUT: Containers are fully operational. Starting Maven tests.
