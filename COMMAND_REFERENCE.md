# 🚀 COMMAND REFERENCE - COPY & PASTE READY

## QUICK COPY-PASTE COMMANDS

---

## 🔧 INITIAL SETUP (Run Once)

### Build Backend
```powershell
cd "E:\Inventory Management System\InventoryServer"
mvn clean package -DskipTests
```

### Build Frontend
```powershell
cd "E:\Inventory Management System\InventoryClient"
mvn clean package
```

---

## ▶️ RUN SYSTEM (Every Time)

### Terminal 1: Start Backend
```powershell
cd "E:\Inventory Management System\InventoryServer"
java -jar target\inventory-backend-1.0.0.jar
```

### Terminal 2: Start Frontend
```powershell
cd "E:\Inventory Management System\InventoryClient"
mvn javafx:run
```

---

## 🔍 VERIFICATION COMMANDS

### Check PostgreSQL Running
```powershell
Get-Service postgresql-x64-*
```

### Check Port 8080 Available
```powershell
netstat -ano | findstr ":8080"
```

### Test Backend API
```powershell
Invoke-WebRequest -Uri "http://localhost:8080/api/v1/auth/health"
```

### Test Database Connection
```powershell
psql -U postgres -d wms -c "SELECT COUNT(*) FROM roles;"
```

### List All Tables
```powershell
psql -U postgres -d wms -c "\dt"
```

### View Users
```powershell
psql -U postgres -d wms -c "SELECT * FROM users;"
```

### View Roles
```powershell
psql -U postgres -d wms -c "SELECT * FROM roles;"
```

---

## 🐛 TROUBLESHOOTING COMMANDS

### Kill Process on Port 8080
```powershell
netstat -ano | findstr ":8080"
taskkill /PID <PID> /F
```

### Start PostgreSQL Service
```powershell
Get-Service postgresql-x64-* | Start-Service
```

### Check Java Version
```powershell
java -version
```

### Check Maven Version
```powershell
mvn -version
```

### Rebuild Backend (Clean)
```powershell
cd "E:\Inventory Management System\InventoryServer"
rm -Recurse -Force target
mvn clean package -DskipTests
```

### Rebuild Frontend (Clean)
```powershell
cd "E:\Inventory Management System\InventoryClient"
rm -Recurse -Force target
mvn clean package
```

---

## 📊 DATABASE COMMANDS

### Create Database
```powershell
psql -U postgres -c "CREATE DATABASE wms;"
```

### Drop Database (WARNING: Deletes data)
```powershell
psql -U postgres -c "DROP DATABASE wms;"
```

### Connect to Database
```powershell
psql -U postgres -d wms
```

### List Databases
```powershell
psql -U postgres -c "\l"
```

### Show Table Structure
```powershell
psql -U postgres -d wms -c "\d users"
```

---

## 🔐 LOGIN TEST COMMANDS

### Test Login via API
```powershell
$body = @{username="admin"; password="admin123"} | ConvertTo-Json
Invoke-WebRequest -Uri "http://localhost:8080/api/v1/auth/login" -Method Post -Body $body -ContentType "application/json"
```

---

## 📝 LOG VIEWING

### View Backend Logs (while running)
```
Check Terminal 1 where backend is running
Look for lines starting with [INFO] or [DEBUG]
```

### Enable Debug Mode
```powershell
$env:DEBUG_MODE="true"
# Then start backend
```

### View Full Maven Build Output
```powershell
cd "E:\Inventory Management System\InventoryServer"
mvn clean package -DskipTests
```

---

## 🔄 COMPLETE WORKFLOW

### One-Command Setup & Run (Requires 2 terminals)

**Terminal 1:**
```powershell
cd "E:\Inventory Management System\InventoryServer" && mvn clean package -DskipTests && java -jar target\inventory-backend-1.0.0.jar
```

**Terminal 2:**
```powershell
cd "E:\Inventory Management System\InventoryClient" && mvn clean package && mvn javafx:run
```

---

## 🆘 EMERGENCY RESET

### Complete System Reset
```powershell
# 1. Kill all Java processes
Get-Process java | Stop-Process -Force

# 2. Drop and recreate database
psql -U postgres -c "DROP DATABASE IF EXISTS wms;"
psql -U postgres -c "CREATE DATABASE wms;"

# 3. Rebuild both systems
cd "E:\Inventory Management System\InventoryServer"
rm -Recurse -Force target
mvn clean package -DskipTests

cd "E:\Inventory Management System\InventoryClient"
rm -Recurse -Force target
mvn clean package

# 4. Restart services
# Then run commands from "RUN SYSTEM" section above
```

---

## 📍 IMPORTANT PATHS

### Backend Project
```
E:\Inventory Management System\InventoryServer
```

### Frontend Project
```
E:\Inventory Management System\InventoryClient
```

### Configuration Files
```
Backend:  InventoryServer/src/main/resources/application.properties
Frontend: InventoryClient/src/main/java/com/wafry/inventory/config/ApplicationConfig.java
```

### Built JAR Files
```
Backend:  InventoryServer/target/inventory-backend-1.0.0.jar
Frontend: InventoryClient/target/classes/
```

---

## 🎯 TYPICAL WORKFLOW

```powershell
# 1. Open PowerShell Window 1
cd "E:\Inventory Management System\InventoryServer"
java -jar target\inventory-backend-1.0.0.jar

# Wait for: "Started WarehouseApplication"

# 2. Open PowerShell Window 2
cd "E:\Inventory Management System\InventoryClient"
mvn javafx:run

# Wait for: JavaFX window to appear

# 3. In JavaFX window
# Enter: admin / admin123
# Click: Login
# Enjoy! 🎉
```

---

## 💾 BACKUP & RESTORE

### Backup Database
```powershell
pg_dump -U postgres wms > backup.sql
```

### Restore Database
```powershell
psql -U postgres < backup.sql
```

---

## 📌 NOTES

- All commands assume PowerShell is running as Administrator
- Paths use backslashes `\` (Windows style)
- Replace `<PID>` with actual process ID from netstat output
- Backend must start BEFORE frontend
- PostgreSQL must be running before backend starts
- Default login: admin / admin123

---

**Last Updated:** April 8, 2026
**System Version:** 2.0-STABLE

