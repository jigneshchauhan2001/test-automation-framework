cd %~dp0
start cmd /c "start /B java -jar selenium-server-4.12.1.jar hub --port 4444 && title Selenium_Grid_Hub" 
start cmd /c "start /B java -jar selenium-server-4.12.1.jar node --port 5555 --host localhost  --selenium-manager true --detect-drivers true && title Selenium_Grid_Node" 
