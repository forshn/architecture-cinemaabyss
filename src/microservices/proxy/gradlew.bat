@echo off
set DEFAULT_JVM_OPTS=-Xmx64m -Xms64m
set DIR=%~dp0
set APP_HOME=%DIR%..
set CLASSPATH=%APP_HOME%\gradle\wrapper\gradle-wrapper.jar

if not defined JAVA_HOME (
  set JAVACMD=java
) else (
  set JAVACMD=%JAVA_HOME%\bin\java.exe
)

"%JAVACMD%" %DEFAULT_JVM_OPTS% -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %*
