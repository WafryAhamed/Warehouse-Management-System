@echo off
REM ====================================================================
REM OrbyteX1 Complete System Startup
REM ====================================================================

cls
echo.
echo ╔══════════════════════════════════════════════════════════════╗
echo ║                                                              ║
echo ║         🎉 ORBYTEX1 COMPLETE SYSTEM STARTUP 🎉             ║
echo ║                                                              ║
echo ║     Enterprise Inventory & E-Commerce Management System     ║
echo ║                                                              ║
echo ╚══════════════════════════════════════════════════════════════╝
echo.

echo STARTUP CHECKLIST:
echo ═══════════════════════════════════════════════════════════════
echo.

REM Check PostgreSQL
echo [1/4] Checking PostgreSQL database...
psql -U postgres -l >nul 2>&1
if %ERRORLEVEL% equ 0 (
    echo     ✅ PostgreSQL is running
) else (
    echo     ❌ PostgreSQL not found or not running
    echo        Start PostgreSQL and try again
    pause
    exit /b 1
)
echo.

REM Check Java
echo [2/4] Checking Java installation...
java -version >nul 2>&1
if %ERRORLEVEL% equ 0 (
    echo     ✅ Java is installed
) else (
    echo     ❌ Java not found - Install Java 17+
    pause
    exit /b 1
)
echo.

REM Check Maven
echo [3/4] Checking Maven installation...
mvn -version >nul 2>&1
if %ERRORLEVEL% equ 0 (
    echo     ✅ Maven is installed
) else (
    echo     ❌ Maven not found - Install Maven 3.8+
    pause
    exit /b 1
)
echo.

REM Check Backend Project
echo [4/4] Checking Backend project...
if exist "E:\Inventory Management System\OrbyteX1-Backend\pom.xml" (
    echo     ✅ Backend project found
) else (
    echo     ❌ Backend project not found
    pause
    exit /b 1
)
echo.

echo ═══════════════════════════════════════════════════════════════
echo.
echo ✅ ALL CHECKS PASSED!
echo.
echo INSTRUCTIONS:
echo ─────────────────────────────────────────────────────────────
echo.
echo 1. Open PowerShell/CMD in: E:\Inventory Management System\OrbyteX1-Backend
echo    Then run: mvn spring-boot:run
echo.
echo 2. In another terminal, open: E:\Inventory Management System\OrbyteX1-Frontend
echo    Then run: mvn javafx:run
echo.
echo 3. Login with:
echo    Username: admin
echo    Password: password
echo    (OR click "Use Demo Credentials" button)
echo.
echo 4. Enjoy the system! 🚀
echo.
echo ═══════════════════════════════════════════════════════════════
echo.
pause

