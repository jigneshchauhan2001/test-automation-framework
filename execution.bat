@echo off
start cmd /k "cd %~dp0 && java -jar selenium-server-4.9.1.jar hub --port 4444" 
start cmd /k "cd %~dp0 && java -Dwebdriver.chrome.driver=%~dp0\Drivers\chromedriver_v114.exe -jar selenium-server-4.9.1.jar node --port 5555 --host localhost" 
start cmd /k "cd %~dp0 && mvn test"