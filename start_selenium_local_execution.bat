cd %~dp0
start /B java -jar selenium-server-4.12.1.jar hub --port 4444
start /B java -jar selenium-server-4.12.1.jar node --port 5555 --host localhost --selenium-manager true --detect-drivers true
