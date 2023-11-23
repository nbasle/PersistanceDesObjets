@echo off

set JAVA=%JAVA_HOME%\bin\java
set DEPLOY_DIR=..\build

set CLASSPATH=%DEPLOY_DIR%\clientCustomer.jar

%JAVA% -cp %CLASSPATH% com.yaps.petstore.ui.MenuCustomer