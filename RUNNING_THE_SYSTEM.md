# 🚀 RUNNING THE INVENTORY MANAGEMENT SYSTEM

## System Status: ✅ READY TO RUN

**Last Updated:** April 8, 2026

---

## 📋 PREREQUISITES

- ✅ Java 17+
- ✅ PostgreSQL 15+ (Running on localhost:5432)
- ✅ Maven 3.9.5+

---

## 🔧 STEP 1: BACKEND SETUP

### Database Check
```bash
# Verify PostgreSQL is running
Get-Service postgresql-x64-*

# Create database if needed
psql -U postgres -c "CREATE DATABASE wms;"
```

### Start Backend (Two Options)

**Option A: Using JAR (Recommended - Faster)**
```bash
cd "E:\Inventory Management System\InventoryServer"
java -jar target\inventory-backend-1.0.0.jar
```

**Option B: Using Maven**
```bash
cd "E:\Inventory Management System\InventoryServer"
mvn spring-boot:run
```

### Backend Configuration
- **Port:** 8080
- **Base URL:** http://localhost:8080/api/v1
- **Database:** wms (PostgreSQL)
- **Username:** postgres
- **Password:** 2001

### Test Backend
```bash
curl -X GET http://localhost:8080/api/v1/auth/health
# Expected: 200 OK (or 401 if authentication required)
```

---

## 🖥️ STEP 2: FRONTEND SETUP

### Build Frontend
```bash
cd "E:\Inventory Management System\InventoryClient"
mvn clean package
```

### Start Frontend (JavaFX Application)
```bash
cd "E:\Inventory Management System\InventoryClient"
mvn javafx:run
```

### Frontend Configuration
- **API Base URL:** http://localhost:8080/api/v1 (auto-configured)
- **Port:** N/A (Desktop Application)
- **Main Class:** com.wafry.inventory.app.Launcher

---

## 🔐 LOGIN CREDENTIALS

### Default Admin User
- **Username:** admin
- **Password:** admin123
- **Role:** SUPER_ADMIN

### Available Roles
1. SUPER_ADMIN - Full system access
2. ADMIN - Administrative functions
3. MANAGER - Department management
4. INVENTORY_MANAGER - Stock management
5. CASHIER - POS operations
6. SALES_ANALYST - Analytics & reports
7. CUSTOMER_SUPPORT - Customer service

---

## ✅ VERIFICATION CHECKLIST

### Backend
- [ ] Port 8080 is listening
- [ ] PostgreSQL is connected
- [ ] Database "wms" exists
- [ ] Roles are initialized (7 roles)
- [ ] Default admin user exists
- [ ] API responds to /auth/health

### Frontend
- [ ] Compiles without errors
- [ ] Launcher starts the application
- [ ] Login screen appears
- [ ] Can login with admin/admin123
- [ ] Dashboard loads successfully

---

## 🐛 TROUBLESHOOTING

### Backend Won't Start

**Error: Connection refused**
- Check PostgreSQL is running: `Get-Service postgresql-x64-*`
- Verify database exists: `psql -U postgres -d wms`

**Error: ClassNotFoundException**
- Clean rebuild: `mvn clean package -DskipTests`
- Delete target folder and rebuild

**Error: Port 8080 already in use**
- Find process: `netstat -ano | findstr ":8080"`
- Kill process: `taskkill /PID <PID> /F`

### Frontend Won't Connect

**Error: Failed to fetch products**
- Check backend is running on port 8080
- Verify API_BASE_URL in ApplicationConfig.java
- Currently set to: `http://localhost:8080/api/v1`

**Error: Login fails**
- Check database has roles initialized
- Run: `psql -U postgres -d wms -c "SELECT * FROM roles;"`
- Run: `psql -U postgres -d wms -c "SELECT * FROM users;"`

---

## 📊 API ENDPOINTS (Sample)

```
POST   /api/v1/auth/login           - User login
GET    /api/v1/auth/health          - Health check
GET    /api/v1/auth/me              - Current user info

GET    /api/v1/products             - List all products
POST   /api/v1/products             - Create product
GET    /api/v1/products/{id}        - Get product by ID
PUT    /api/v1/products/{id}        - Update product
DELETE /api/v1/products/{id}        - Delete product

GET    /api/v1/orders               - List orders
POST   /api/v1/orders               - Create order
GET    /api/v1/orders/{id}          - Get order details
```

---

## 🔧 CONFIGURATION FILES

### Backend Configuration
- **Location:** `InventoryServer/src/main/resources/application.properties`
- **Key Settings:**
  - `server.port=8080`
  - `spring.datasource.url=jdbc:postgresql://localhost:5432/wms`
  - `spring.datasource.username=postgres`
  - `spring.datasource.password=2001`
  - `spring.jpa.hibernate.ddl-auto=create`

### Frontend Configuration
- **Location:** `InventoryClient/src/main/java/com/wafry/inventory/config/ApplicationConfig.java`
- **Key Settings:**
  - `API_BASE_URL = "http://localhost:8080/api/v1"`
  - `APP_TITLE = "Inventory Management System - Enterprise Edition"`
  - `APP_VERSION = "2.0-STABLE"`

---

## 📝 NOTES

1. **Database Initialization**
   - On first run, Hibernate will create all tables automatically
   - DatabaseInitializer will seed roles and default admin user
   - No manual SQL needed

2. **JWT Authentication**
   - Token valid for 24 hours
   - All protected endpoints require `Authorization: Bearer <token>` header

3. **CORS Configuration**
   - Frontend and backend on same machine
   - CORS enabled for localhost

4. **Development Mode**
   - Spring Boot DevTools enabled (faster restarts)
   - Live reload available during development

---

## 🚀 QUICK START (One-Liner)

### In Two Separate Terminal Windows

**Terminal 1 (Backend):**
```bash
cd "E:\Inventory Management System\InventoryServer" && java -jar target\inventory-backend-1.0.0.jar
```

**Terminal 2 (Frontend):**
```bash
cd "E:\Inventory Management System\InventoryClient" && mvn javafx:run
```

---

## 📞 SUPPORT

If you encounter issues:
1. Check logs in terminal where services are running
2. Verify database connection
3. Ensure ports are not blocked by firewall
4. Rebuild projects if files were modified
5. Clear Maven cache if build fails: `mvn clean`

---

**Happy using! 🎉**

