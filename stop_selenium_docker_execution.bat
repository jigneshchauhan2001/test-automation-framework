echo BATCH_OUTPUT: Maven Tests Completed. Removing docker containers...
docker-compose down

curl -I http://localhost:4444/ui  2>nul | findstr "200 OK" >nul
if %errorlevel% EQU 0 (
    echo BATCH_OUTPUT: Containers are not stopped yet. Waiting.
    goto :WAIT_FOR_CONTAINERS_REMOVAL
)

:WAIT_FOR_CONTAINERS_REMOVAL
echo BATCH_OUTPUT: Waiting for containers to be removed...
timeout /t 180 /nobreak >nul 2>&1


echo BATCH_OUTPUT: Containers Are Removed.