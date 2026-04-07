@echo off
REM ====================================================================
REM OrbyteX1 Frontend Startup Script
REM ====================================================================

echo.
echo ╔════════════════════════════════════════════════════════════╗
echo ║                                                            ║
echo ║          🖥️ ORBYTEX1 FRONTEND STARTUP SCRIPT 🖥️          ║
echo ║                                                            ║
echo ╚════════════════════════════════════════════════════════════╝
echo.

REM Check if we're in the right directory
if not exist "pom.xml" (
    echo ❌ ERROR: Not in OrbyteX1-Frontend directory!
    echo.
    echo Please run this from: E:\Inventory Management System\OrbyteX1-Frontend
    echo.
    pause
    exit /b 1
)

echo ✅ Frontend project found!
echo.
echo Starting JavaFX Desktop Application...
echo.
echo Prerequisites:
echo  ✓ Backend running on localhost:8080
echo  ✓ Maven installed
echo  ✓ Java 17+ installed
echo.
echo ═══════════════════════════════════════════════════════════
echo.

REM Start frontend
echo Starting Frontend Application (JavaFX)...
call mvn javafx:run

REM If it fails, show error
if %ERRORLEVEL% neq 0 (
    echo.
    echo ❌ ERROR: Frontend failed to start!
    echo.
    echo Troubleshooting:
    echo  1. Check Backend is running: http://localhost:8080/api/v1/auth/health
    echo  2. Check Java 17+ installed: java -version
    echo  3. Check Maven installed: mvn -version
    echo  4. Check JavaFX dependencies: mvn dependency:resolve
    echo.
    pause
)

