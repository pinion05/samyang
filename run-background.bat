@echo off
echo Spring Boot Application Background Starter
echo =========================================

rem 기존 application.log 파일 삭제
if exist application.log del application.log

rem 현재 시간 기록
echo Starting application at %date% %time%

rem gradlew.bat bootRun을 백그라운드로 실행
echo Starting Spring Boot application in background...
start /b cmd /c "gradlew.bat bootRun > application.log 2>&1"

rem 잠시 대기하여 프로세스가 시작되도록 함
timeout /t 5 /nobreak > nul

rem PID를 찾기 위해 java 프로세스 확인
for /f "tokens=2" %%i in ('wmic process where "commandline like '%%bootRun%%' and name='java.exe'" get processid ^| findstr /r "[0-9]"') do set PID=%%i

if defined PID (
    echo Process started with PID: %PID%
) else (
    echo Warning: Could not capture PID
)

rem 최대 180초 동안 Started 로그 확인
echo Waiting for application to start (max 180 seconds)...
set /a count=0

:LOOP
rem 로그 파일이 존재하는지 확인
if not exist application.log (
    timeout /t 2 /nobreak > nul
    set /a count+=2
    if %count% lss 180 goto LOOP
    goto TIMEOUT
)

rem "Started" 키워드 확인
findstr /i "Started" application.log > nul
if %errorlevel% == 0 (
    goto SUCCESS
)

rem localhost:8080 연결 시도 (curl이 없으므로 powershell 사용)
powershell -Command "try { $response = Invoke-WebRequest -Uri 'http://localhost:8080/actuator/health' -UseBasicParsing -TimeoutSec 2; exit 0 } catch { exit 1 }" > nul 2>&1
if %errorlevel% == 0 (
    goto SUCCESS
)

rem 2초 대기
timeout /t 2 /nobreak > nul
set /a count+=2

rem 180초 미만이면 계속 확인
if %count% lss 180 goto LOOP

:TIMEOUT
echo.
echo TIMEOUT: Application failed to start within 180 seconds
echo.
echo Last 20 lines of application log:
echo ================================
if exist application.log (
    powershell -Command "Get-Content application.log -Tail 20"
) else (
    echo No log file found
)

rem PID가 있으면 프로세스 종료
if defined PID (
    echo.
    echo Terminating process with PID %PID%...
    taskkill /F /PID %PID% > nul 2>&1
    if %errorlevel% == 0 (
        echo Process terminated successfully
    ) else (
        echo Failed to terminate process
    )
) else (
    echo.
    echo Attempting to terminate all bootRun java processes...
    wmic process where "commandline like '%%bootRun%%' and name='java.exe'" delete > nul 2>&1
)
exit /b 1

:SUCCESS
echo.
echo SUCCESS: Spring Boot application started successfully!
if defined PID (
    echo Process ID: %PID%
) else (
    rem PID를 다시 시도
    for /f "tokens=2" %%i in ('wmic process where "commandline like '%%bootRun%%' and name='java.exe'" get processid ^| findstr /r "[0-9]"') do set PID=%%i
    if defined PID (
        echo Process ID: %PID%
    )
)
echo.
echo Application is running at: http://localhost:8080
echo Health check available at: http://localhost:8080/actuator/health
echo.
echo To stop the application, use: taskkill /F /PID %PID%
exit /b 0