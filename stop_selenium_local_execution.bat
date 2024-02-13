cd %~dp0
FOR /F "tokens=1" %%i IN ('jps -l ^| find "selenium-server-4.12.1.jar"') DO (taskkill /F /PID %%i)
