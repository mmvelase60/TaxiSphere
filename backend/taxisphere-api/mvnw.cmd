@echo off
setlocal

set "MVNW_REPOURL=https://repo.maven.apache.org/maven2"
set "MVNW_VERSION=3.3.2"
set "MVNW_WRAPPER_DIR=%~dp0.mvn\wrapper"
set "MVNW_WRAPPER_JAR=%MVNW_WRAPPER_DIR%\maven-wrapper.jar"
set "MVNW_WRAPPER_MAIN=org.apache.maven.wrapper.MavenWrapperMain"

if not exist "%MVNW_WRAPPER_DIR%" mkdir "%MVNW_WRAPPER_DIR%"

if not exist "%MVNW_WRAPPER_JAR%" (
  echo Maven wrapper jar is missing.
  echo Install Maven locally and run: mvn -N wrapper:wrapper
  echo Or download dependencies in an environment with network access.
  exit /b 1
)

java -classpath "%MVNW_WRAPPER_JAR%" "-Dmaven.multiModuleProjectDirectory=%~dp0" %MVNW_WRAPPER_MAIN% %*
