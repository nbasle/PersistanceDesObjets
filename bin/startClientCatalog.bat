@echo off

set JAVA=%JAVA_HOME%\bin\java
set DEPLOY_DIR=..\build

set CLASSPATH=%DEPLOY_DIR%\clientCatalog.jar

%JAVA% -cp %CLASSPATH% com.yaps.petstore.ui.MenuCatalog