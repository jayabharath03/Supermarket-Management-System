@echo off
set MAVEN_PROJECTBASEDIR=%~dp0
set MAVEN_PROJECTBASEDIR=%MAVEN_PROJECTBASEDIR:~0,-1%
set MAVEN_WRAPPER_JAR=%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.jar
set MAVEN_WRAPPER_MAIN=org.apache.maven.wrapper.MavenWrapperMain
java -Dmaven.multiModuleProjectDirectory="%MAVEN_PROJECTBASEDIR%" -cp "%MAVEN_WRAPPER_JAR%" %MAVEN_WRAPPER_MAIN% %*
