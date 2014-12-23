@echo off

if not defined JAVA_HOME (goto :askEnvironmentVar) else (goto :maven)

:askEnvironmentVar
set /p javahome=Enter JAVA_HOME path (jdk folder): 
SET JAVA_HOME=%javahome%
goto :maven

:maven
call mvn clean compile package