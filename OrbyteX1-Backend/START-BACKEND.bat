@echo off
REM ====================================================================
REM OrbyteX1 System Startup Script
REM ====================================================================

echo.
echo ╔════════════════════════════════════════════════════════════╗
echo ║                                                            ║
echo ║          🚀 ORBYTEX1 SYSTEM STARTUP SCRIPT 🚀            ║
echo ║                                                            ║
echo ╚════════════════════════════════════════════════════════════╝
echo.

REM Check if we're in the right directory
if not exist "pom.xml" (
    echo ❌ ERROR: Not in OrbyteX1-Backend directory!
    echo.
    echo Please run this from: E:\Inventory Management System\OrbyteX1-Backend
    echo.
    pause
    exit /b 1
)

echo ✅ Backend project found!
echo.
echo Starting Maven build and Spring Boot application...
echo.
echo Prerequisites:
echo  ✓ PostgreSQL running on localhost:5432
echo  ✓ Database 'orbytex1' created
echo  ✓ User 'orbytex_user' with password '2001'
echo.
echo ═══════════════════════════════════════════════════════════
echo.

REM Start backend
echo Starting Backend Application...
call mvn spring-boot:run

REM If it fails, show error
if %ERRORLEVEL% neq 0 (
    echo.
    echo ❌ ERROR: Backend failed to start!
    echo.
    echo Troubleshooting:
    echo  1. Check PostgreSQL is running
    echo  2. Check database exists: psql -U postgres -l
    echo  3. Check port 8080 is available: netstat -ano ^| findstr :8080
    echo  4. Check Java 17+ installed: java -version
    echo  5. Check Maven installed: mvn -version
    echo.
    pause
)

