-- ============ POSTGRESQL DATABASE SCHEMA ============
-- Warehouse Management System Database
-- Created: April 7, 2026

-- Drop existing tables if they exist
DROP TABLE IF EXISTS order_items CASCADE;
DROP TABLE IF EXISTS sales_orders CASCADE;
DROP TABLE IF EXISTS purchase_orders CASCADE;
DROP TABLE IF EXISTS stock_movements CASCADE;
DROP TABLE IF EXISTS stock CASCADE;
DROP TABLE IF EXISTS locations CASCADE;
DROP TABLE IF EXISTS products CASCADE;
DROP TABLE IF EXISTS suppliers CASCADE;
DROP TABLE IF EXISTS warehouses CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- ============ USERS TABLE ============
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'STAFF', -- ADMIN, STAFF
    active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_email ON users(email);

-- ============ WAREHOUSES TABLE ============
CREATE TABLE warehouses (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    location VARCHAR(255) NOT NULL,
    capacity INTEGER NOT NULL,
    current_stock INTEGER DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_warehouses_name ON warehouses(name);
CREATE INDEX idx_warehouses_location ON warehouses(location);

-- ============ SUPPLIERS TABLE ============
CREATE TABLE suppliers (
    id SERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    phone VARCHAR(20) NOT NULL,
    address VARCHAR(255) NOT NULL,
    lead_time_days INTEGER DEFAULT 7,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE', -- ACTIVE, INACTIVE
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_suppliers_email ON suppliers(email);
CREATE INDEX idx_suppliers_name ON suppliers(name);

-- ============ PRODUCTS TABLE ============
CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    sku VARCHAR(100) NOT NULL UNIQUE,
    category VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    cost DECIMAL(10, 2) NOT NULL,
    supplier_id INTEGER REFERENCES suppliers(id) ON DELETE SET NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_products_sku ON products(sku);
CREATE INDEX idx_products_category ON products(category);
CREATE INDEX idx_products_supplier_id ON products(supplier_id);

-- ============ LOCATIONS TABLE ============
CREATE TABLE locations (
    id SERIAL PRIMARY KEY,
    warehouse_id INTEGER NOT NULL REFERENCES warehouses(id) ON DELETE CASCADE,
    rack_number VARCHAR(50) NOT NULL,
    shelf_number VARCHAR(50) NOT NULL,
    bin_number VARCHAR(50) NOT NULL,
    capacity INTEGER NOT NULL,
    current_stock INTEGER DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_locations_warehouse_id ON locations(warehouse_id);
CREATE UNIQUE INDEX idx_locations_unique ON locations(warehouse_id, rack_number, shelf_number, bin_number);

-- ============ STOCK TABLE ============
CREATE TABLE stock (
    id SERIAL PRIMARY KEY,
    product_id INTEGER NOT NULL REFERENCES products(id) ON DELETE CASCADE,
    warehouse_id INTEGER NOT NULL REFERENCES warehouses(id) ON DELETE CASCADE,
    location_id INTEGER REFERENCES locations(id) ON DELETE SET NULL,
    quantity INTEGER NOT NULL DEFAULT 0,
    last_updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(product_id, warehouse_id, location_id)
);

CREATE INDEX idx_stock_product_id ON stock(product_id);
CREATE INDEX idx_stock_warehouse_id ON stock(warehouse_id);
CREATE INDEX idx_stock_location_id ON stock(location_id);

-- ============ STOCK MOVEMENTS TABLE ============
CREATE TABLE stock_movements (
    id SERIAL PRIMARY KEY,
    product_id INTEGER NOT NULL REFERENCES products(id) ON DELETE CASCADE,
    warehouse_id INTEGER NOT NULL REFERENCES warehouses(id) ON DELETE CASCADE,
    type VARCHAR(20) NOT NULL, -- IN, OUT, TRANSFER, ADJUSTMENT
    quantity INTEGER NOT NULL,
    from_location_id INTEGER REFERENCES locations(id) ON DELETE SET NULL,
    to_location_id INTEGER REFERENCES locations(id) ON DELETE SET NULL,
    reference_number VARCHAR(100),
    user_id INTEGER REFERENCES users(id) ON DELETE SET NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_movements_product_id ON stock_movements(product_id);
CREATE INDEX idx_movements_warehouse_id ON stock_movements(warehouse_id);
CREATE INDEX idx_movements_type ON stock_movements(type);
CREATE INDEX idx_movements_created_at ON stock_movements(created_at);

-- ============ PURCHASE ORDERS TABLE ============
CREATE TABLE purchase_orders (
    id SERIAL PRIMARY KEY,
    po_number VARCHAR(100) NOT NULL UNIQUE,
    supplier_id INTEGER NOT NULL REFERENCES suppliers(id) ON DELETE RESTRICT,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING', -- PENDING, CONFIRMED, RECEIVED, CANCELLED
    total DECIMAL(15, 2) DEFAULT 0,
    expected_delivery TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_purchase_orders_po_number ON purchase_orders(po_number);
CREATE INDEX idx_purchase_orders_supplier_id ON purchase_orders(supplier_id);
CREATE INDEX idx_purchase_orders_status ON purchase_orders(status);

-- ============ SALES ORDERS TABLE ============
CREATE TABLE sales_orders (
    id SERIAL PRIMARY KEY,
    so_number VARCHAR(100) NOT NULL UNIQUE,
    customer_name VARCHAR(150) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING', -- PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED
    total DECIMAL(15, 2) DEFAULT 0,
    expected_delivery TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_sales_orders_so_number ON sales_orders(so_number);
CREATE INDEX idx_sales_orders_customer_name ON sales_orders(customer_name);
CREATE INDEX idx_sales_orders_status ON sales_orders(status);

-- ============ ORDER ITEMS TABLE ============
CREATE TABLE order_items (
    id SERIAL PRIMARY KEY,
    order_id INTEGER NOT NULL,
    product_id INTEGER NOT NULL REFERENCES products(id) ON DELETE RESTRICT,
    quantity INTEGER NOT NULL,
    unit_price DECIMAL(10, 2) NOT NULL,
    total_price DECIMAL(15, 2) NOT NULL,
    order_type VARCHAR(20) NOT NULL -- PURCHASE, SALES
);

CREATE INDEX idx_order_items_order_id ON order_items(order_id);
CREATE INDEX idx_order_items_product_id ON order_items(product_id);

-- ============ INSERT SAMPLE DATA ============

-- Sample Users
INSERT INTO users (username, email, password, role, active)
VALUES
    ('admin', 'admin@warehouse.com', '$2a$10$slYQmyNdGzin7olVN3p5Be7DlH.PKZbv5H8KnzzVgXXbVxzy990qm', 'ADMIN', true),
    ('staff1', 'staff1@warehouse.com', '$2a$10$slYQmyNdGzin7olVN3p5Be7DlH.PKZbv5H8KnzzVgXXbVxzy990qm', 'STAFF', true),
    ('staff2', 'staff2@warehouse.com', '$2a$10$slYQmyNdGzin7olVN3p5Be7DlH.PKZbv5H8KnzzVgXXbVxzy990qm', 'STAFF', true);

-- Sample Warehouses
INSERT INTO warehouses (name, location, capacity, current_stock)
VALUES
    ('Main Warehouse', 'New York', 100000, 45000),
    ('West Warehouse', 'Los Angeles', 80000, 32000),
    ('East Warehouse', 'Boston', 60000, 28000);

-- Sample Suppliers
INSERT INTO suppliers (name, email, phone, address, lead_time_days, status)
VALUES
    ('Tech Supplies Inc', 'contact@techsupplies.com', '555-0001', '123 Tech St, NY', 7, 'ACTIVE'),
    ('Electronics Ltd', 'info@electronics.com', '555-0002', '456 Circuit Ave, CA', 5, 'ACTIVE'),
    ('Global Import Co', 'sales@globalimport.com', '555-0003', '789 Trade Blvd, TX', 14, 'ACTIVE');

-- Sample Products
INSERT INTO products (name, sku, category, price, cost, supplier_id)
VALUES
    ('Laptop Computer', 'LAPTOP-001', 'Electronics', 1299.99, 800.00, 1),
    ('USB Cable', 'CABLE-USB-001', 'Accessories', 19.99, 5.00, 2),
    ('Monitor 27inch', 'MON-27-001', 'Electronics', 399.99, 250.00, 1),
    ('Keyboard Mechanical', 'KB-MECH-001', 'Accessories', 149.99, 60.00, 2),
    ('Mouse Wireless', 'MOUSE-WL-001', 'Accessories', 49.99, 20.00, 3);

-- Sample Locations
INSERT INTO locations (warehouse_id, rack_number, shelf_number, bin_number, capacity, current_stock)
VALUES
    (1, 'A', '1', '01', 1000, 250),
    (1, 'A', '1', '02', 1000, 180),
    (1, 'B', '2', '01', 1000, 300),
    (2, 'A', '1', '01', 800, 150),
    (3, 'C', '3', '01', 600, 100);

-- Sample Stock
INSERT INTO stock (product_id, warehouse_id, location_id, quantity)
VALUES
    (1, 1, 1, 250),
    (2, 1, 2, 5000),
    (3, 1, 3, 300),
    (4, 2, 4, 150),
    (5, 3, 5, 100);

-- Sample Stock Movements (audit trail)
INSERT INTO stock_movements (product_id, warehouse_id, type, quantity, reference_number, user_id)
VALUES
    (1, 1, 'IN', 250, 'PO-001', 1),
    (2, 1, 'IN', 5000, 'PO-002', 1),
    (3, 1, 'IN', 300, 'PO-003', 1),
    (1, 1, 'OUT', 25, 'SO-001', 2),
    (2, 1, 'OUT', 100, 'SO-002', 2);

-- Sample Purchase Orders
INSERT INTO purchase_orders (po_number, supplier_id, status, total, expected_delivery)
VALUES
    ('PO-2026-001', 1, 'CONFIRMED', 32499.75, CURRENT_TIMESTAMP + INTERVAL '7 days'),
    ('PO-2026-002', 2, 'RECEIVED', 99999.99, CURRENT_TIMESTAMP + INTERVAL '5 days'),
    ('PO-2026-003', 3, 'PENDING', 14999.99, CURRENT_TIMESTAMP + INTERVAL '14 days');

-- Sample Sales Orders
INSERT INTO sales_orders (so_number, customer_name, status, total, expected_delivery)
VALUES
    ('SO-2026-001', 'Acme Corp', 'CONFIRMED', 2699.95, CURRENT_TIMESTAMP + INTERVAL '3 days'),
    ('SO-2026-002', 'Tech Solutions Ltd', 'SHIPPED', 1499.98, CURRENT_TIMESTAMP + INTERVAL '2 days'),
    ('SO-2026-003', 'Global Industries', 'PENDING', 3949.97, CURRENT_TIMESTAMP + INTERVAL '5 days');

-- ============ CREATE VIEWS FOR DASHBOARDS ============

CREATE VIEW warehouse_utilization AS
SELECT
    w.id,
    w.name,
    w.location,
    w.capacity,
    w.current_stock,
    ROUND(((w.current_stock::numeric / w.capacity) * 100), 2) as utilization_percent
FROM warehouses w;

CREATE VIEW product_stock_summary AS
SELECT
    p.id,
    p.name,
    p.sku,
    p.category,
    COALESCE(SUM(s.quantity), 0) as total_quantity,
    p.price,
    COALESCE(SUM(s.quantity), 0) * p.price as total_value
FROM products p
LEFT JOIN stock s ON p.id = s.product_id
GROUP BY p.id, p.name, p.sku, p.category, p.price;

CREATE VIEW low_stock_products AS
SELECT
    p.id,
    p.name,
    p.sku,
    p.category,
    COALESCE(SUM(s.quantity), 0) as quantity
FROM products p
LEFT JOIN stock s ON p.id = s.product_id
GROUP BY p.id, p.name, p.sku, p.category
HAVING COALESCE(SUM(s.quantity), 0) < 100;

-- ============ SCHEMA CREATION COMPLETE ============
COMMIT;

