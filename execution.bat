cd %~dp0
start cmd /k "start /B java -jar selenium-server-4.9.1.jar hub --port 4444 && title Selenium_Grid_Hub" 
start cmd /k "start /B java -Dwebdriver.chrome.driver=Drivers\chromedriver_v114.exe -jar selenium-server-4.9.1.jar node --port 5555 --host localhost && title Selenium_Grid_Node" 
start cmd /k "mvn clean test && mytaskkill.bat"