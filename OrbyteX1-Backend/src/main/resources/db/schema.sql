-- ============================
-- OrbyteX1 Database Schema
-- PostgreSQL
-- ============================

-- Create Roles table
CREATE TABLE IF NOT EXISTS roles (
    id BIGSERIAL PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert roles
INSERT INTO roles (role_name, description) VALUES
('SUPER_ADMIN', 'System administrator with full access'),
('ADMIN', 'Business administrator'),
('MANAGER', 'Store/sales manager'),
('CASHIER', 'Point of Sale operator'),
('INVENTORY_MANAGER', 'Inventory management staff'),
('SALES_ANALYST', 'Sales and analytics staff'),
('CUSTOMER_SUPPORT', 'Customer support staff'),
('CUSTOMER', 'Customer role')
ON CONFLICT (role_name) DO NOTHING;

-- Create Users table
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    role_id BIGINT NOT NULL REFERENCES roles(id),
    is_active BOOLEAN DEFAULT true,
    last_login TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Categories table
CREATE TABLE IF NOT EXISTS categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    created_by BIGINT REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Brands table
CREATE TABLE IF NOT EXISTS brands (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    created_by BIGINT REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Products table
CREATE TABLE IF NOT EXISTS products (
    id BIGSERIAL PRIMARY KEY,
    sku VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(200) NOT NULL,
    description TEXT,
    category_id BIGINT NOT NULL REFERENCES categories(id),
    brand_id BIGINT REFERENCES brands(id),
    cost_price DECIMAL(12,2) NOT NULL,
    selling_price DECIMAL(12,2) NOT NULL,
    reorder_level INT DEFAULT 10,
    image_url VARCHAR(255),
    is_active BOOLEAN DEFAULT true,
    created_by BIGINT REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Product Variants table (for RAM, SSD, Color, etc.)
CREATE TABLE IF NOT EXISTS product_variants (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL REFERENCES products(id) ON DELETE CASCADE,
    variant_name VARCHAR(100) NOT NULL,
    variant_value VARCHAR(100) NOT NULL,
    additional_cost DECIMAL(12,2) DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(product_id, variant_name, variant_value)
);

-- Create Inventory table
CREATE TABLE IF NOT EXISTS inventory (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL UNIQUE REFERENCES products(id) ON DELETE CASCADE,
    quantity_on_hand INT NOT NULL DEFAULT 0,
    quantity_reserved INT NOT NULL DEFAULT 0,
    quantity_available INT NOT NULL DEFAULT 0,
    warehouse_location VARCHAR(100),
    last_counted_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Stock Movements table
CREATE TABLE IF NOT EXISTS stock_movements (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL REFERENCES products(id),
    movement_type VARCHAR(50) NOT NULL, -- 'RECEIPT', 'SALE', 'ADJUSTMENT', 'RETURN'
    quantity INT NOT NULL,
    reference_id BIGINT, -- Order ID, Purchase ID, etc.
    notes TEXT,
    created_by BIGINT REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Customers table
CREATE TABLE IF NOT EXISTS customers (
    id BIGSERIAL PRIMARY KEY,
    customer_name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    address TEXT,
    city VARCHAR(50),
    state VARCHAR(50),
    postal_code VARCHAR(20),
    customer_type VARCHAR(50) DEFAULT 'RETAIL', -- RETAIL, CORPORATE
    loyalty_points INT DEFAULT 0,
    total_purchases DECIMAL(12,2) DEFAULT 0,
    is_active BOOLEAN DEFAULT true,
    created_by BIGINT REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Suppliers table
CREATE TABLE IF NOT EXISTS suppliers (
    id BIGSERIAL PRIMARY KEY,
    supplier_name VARCHAR(100) NOT NULL UNIQUE,
    contact_person VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(20),
    address TEXT,
    city VARCHAR(50),
    state VARCHAR(50),
    postal_code VARCHAR(20),
    payment_terms VARCHAR(100),
    is_active BOOLEAN DEFAULT true,
    created_by BIGINT REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Orders table
CREATE TABLE IF NOT EXISTS orders (
    id BIGSERIAL PRIMARY KEY,
    order_number VARCHAR(50) NOT NULL UNIQUE,
    customer_id BIGINT NOT NULL REFERENCES customers(id),
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(12,2) NOT NULL,
    discount_amount DECIMAL(12,2) DEFAULT 0,
    tax_amount DECIMAL(12,2) DEFAULT 0,
    final_amount DECIMAL(12,2) NOT NULL,
    status VARCHAR(50) DEFAULT 'PENDING', -- PENDING, COMPLETED, CANCELLED, RETURNED
    payment_method VARCHAR(50),
    notes TEXT,
    created_by BIGINT REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Order Items table
CREATE TABLE IF NOT EXISTS order_items (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
    product_id BIGINT NOT NULL REFERENCES products(id),
    quantity INT NOT NULL,
    unit_price DECIMAL(12,2) NOT NULL,
    total_price DECIMAL(12,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Purchases table
CREATE TABLE IF NOT EXISTS purchases (
    id BIGSERIAL PRIMARY KEY,
    purchase_number VARCHAR(50) NOT NULL UNIQUE,
    supplier_id BIGINT NOT NULL REFERENCES suppliers(id),
    purchase_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expected_delivery TIMESTAMP,
    actual_delivery TIMESTAMP,
    total_amount DECIMAL(12,2) NOT NULL,
    status VARCHAR(50) DEFAULT 'PENDING', -- PENDING, RECEIVED, PARTIAL, CANCELLED
    notes TEXT,
    created_by BIGINT REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Purchase Items table
CREATE TABLE IF NOT EXISTS purchase_items (
    id BIGSERIAL PRIMARY KEY,
    purchase_id BIGINT NOT NULL REFERENCES purchases(id) ON DELETE CASCADE,
    product_id BIGINT NOT NULL REFERENCES products(id),
    quantity INT NOT NULL,
    unit_cost DECIMAL(12,2) NOT NULL,
    total_cost DECIMAL(12,2) NOT NULL,
    received_quantity INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Payments table
CREATE TABLE IF NOT EXISTS payments (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT REFERENCES orders(id),
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    amount DECIMAL(12,2) NOT NULL,
    payment_method VARCHAR(50), -- CASH, CARD, CHEQUE, ONLINE
    transaction_id VARCHAR(100),
    status VARCHAR(50) DEFAULT 'COMPLETED', -- COMPLETED, PENDING, FAILED, REFUNDED
    created_by BIGINT REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Expenses table
CREATE TABLE IF NOT EXISTS expenses (
    id BIGSERIAL PRIMARY KEY,
    expense_category VARCHAR(100),
    amount DECIMAL(12,2) NOT NULL,
    description TEXT,
    expense_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Notifications table
CREATE TABLE IF NOT EXISTS notifications (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    notification_type VARCHAR(100), -- LOW_STOCK, ORDER_STATUS, SYSTEM_ALERT
    title VARCHAR(200),
    message TEXT,
    is_read BOOLEAN DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Audit Logs table
CREATE TABLE IF NOT EXISTS audit_logs (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    action VARCHAR(100),
    entity_type VARCHAR(100),
    entity_id BIGINT,
    old_value TEXT,
    new_value TEXT,
    ip_address VARCHAR(45),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Support Tickets table
CREATE TABLE IF NOT EXISTS support_tickets (
    id BIGSERIAL PRIMARY KEY,
    ticket_number VARCHAR(50) NOT NULL UNIQUE,
    customer_id BIGINT NOT NULL REFERENCES customers(id),
    subject VARCHAR(200),
    description TEXT,
    priority VARCHAR(50) DEFAULT 'MEDIUM', -- LOW, MEDIUM, HIGH, CRITICAL
    status VARCHAR(50) DEFAULT 'OPEN', -- OPEN, IN_PROGRESS, RESOLVED, CLOSED
    assigned_to BIGINT REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    resolved_at TIMESTAMP
);

-- Create Support Messages table
CREATE TABLE IF NOT EXISTS support_messages (
    id BIGSERIAL PRIMARY KEY,
    ticket_id BIGINT NOT NULL REFERENCES support_tickets(id) ON DELETE CASCADE,
    user_id BIGINT NOT NULL REFERENCES users(id),
    message TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Indexes for better performance
CREATE INDEX idx_users_role_id ON users(role_id);
CREATE INDEX idx_products_category_id ON products(category_id);
CREATE INDEX idx_products_brand_id ON products(brand_id);
CREATE INDEX idx_orders_customer_id ON orders(customer_id);
CREATE INDEX idx_order_items_order_id ON order_items(order_id);
CREATE INDEX idx_purchases_supplier_id ON purchases(supplier_id);
CREATE INDEX idx_stock_movements_product_id ON stock_movements(product_id);
CREATE INDEX idx_audit_logs_user_id ON audit_logs(user_id);
CREATE INDEX idx_notifications_user_id ON notifications(user_id);
CREATE INDEX idx_support_tickets_customer_id ON support_tickets(customer_id);

-- ============================
-- Demo Data
-- ============================

-- Insert demo users
INSERT INTO users (username, email, password, first_name, last_name, role_id, is_active) VALUES
('super_admin', 'superadmin@orbytex1.com', '$2a$10$slYQmyNdGzin7olVN3p5Be0DlH.pkZbFQVNvlvDQK2e.TO5dHFl.e', 'Super', 'Admin', 1, true),
('admin', 'admin@orbytex1.com', '$2a$10$slYQmyNdGzin7olVN3p5Be0DlH.pkZbFQVNvlvDQK2e.TO5dHFl.e', 'John', 'Admin', 2, true),
('manager', 'manager@orbytex1.com', '$2a$10$slYQmyNdGzin7olVN3p5Be0DlH.pkZbFQVNvlvDQK2e.TO5dHFl.e', 'Jane', 'Manager', 3, true),
('cashier1', 'cashier1@orbytex1.com', '$2a$10$slYQmyNdGzin7olVN3p5Be0DlH.pkZbFQVNvlvDQK2e.TO5dHFl.e', 'Mike', 'Cashier', 4, true),
('inventory_mgr', 'inventory@orbytex1.com', '$2a$10$slYQmyNdGzin7olVN3p5Be0DlH.pkZbFQVNvlvDQK2e.TO5dHFl.e', 'Sarah', 'Inventory', 5, true),
('analyst', 'analyst@orbytex1.com', '$2a$10$slYQmyNdGzin7olVN3p5Be0DlH.pkZbFQVNvlvDQK2e.TO5dHFl.e', 'David', 'Analyst', 6, true),
('support', 'support@orbytex1.com', '$2a$10$slYQmyNdGzin7olVN3p5Be0DlH.pkZbFQVNvlvDQK2e.TO5dHFl.e', 'Lisa', 'Support', 7, true)
ON CONFLICT (username) DO NOTHING;

-- Insert demo categories
INSERT INTO categories (name, description, created_by) VALUES
('Laptops', 'Laptop computers', 2),
('Desktop Computers', 'Desktop PCs', 2),
('Accessories', 'Computer accessories', 2),
('Peripherals', 'Keyboards, mice, monitors', 2),
('Networking', 'Routers, switches, cables', 2)
ON CONFLICT (name) DO NOTHING;

-- Insert demo brands
INSERT INTO brands (name, description, created_by) VALUES
('Dell', 'Dell Technologies', 2),
('HP', 'HP Inc.', 2),
('Lenovo', 'Lenovo Group', 2),
('Apple', 'Apple Inc.', 2),
('Asus', 'Asus Computers', 2),
('Acer', 'Acer Inc.', 2),
('Samsung', 'Samsung Electronics', 2),
('LG', 'LG Electronics', 2)
ON CONFLICT (name) DO NOTHING;

-- Insert demo products
INSERT INTO products (sku, name, description, category_id, brand_id, cost_price, selling_price, reorder_level, is_active, created_by) VALUES
('DELL-XPS-13', 'Dell XPS 13 Laptop', 'Premium ultrabook with Intel i7, 16GB RAM, 512GB SSD', 1, 1, 70000, 89999, 5, true, 2),
('HP-PAVILION-15', 'HP Pavilion 15 Laptop', 'Mid-range laptop with Intel i5, 8GB RAM, 256GB SSD', 1, 2, 45000, 55000, 8, true, 2),
('LENOVO-THINKPAD-L14', 'Lenovo ThinkPad L14', 'Business laptop with Intel i7, 16GB RAM, 512GB SSD', 1, 3, 65000, 85000, 5, true, 2),
('APPLE-MACBOOK-PRO', 'MacBook Pro 14-inch', 'Apple M2 Pro, 16GB RAM, 512GB SSD', 1, 4, 120000, 149999, 3, true, 2),
('ASUS-ROG-GAMING', 'ASUS ROG Gaming Laptop', 'Gaming laptop with RTX 4060, Intel i7, 16GB RAM', 1, 5, 95000, 125000, 4, true, 2),
('MONITOR-SAMSUNG-27', 'Samsung 27" 4K Monitor', '4K IPS display, 60Hz', 2, 7, 22000, 28999, 10, true, 2),
('KEYBOARD-MECHANICAL', 'Mechanical Gaming Keyboard', 'RGB backlit mechanical keyboard', 3, 5, 3500, 5999, 20, true, 2),
('MOUSE-LOGITECH', 'Logitech Wireless Mouse', 'Ergonomic wireless mouse with USB receiver', 3, 8, 1500, 2499, 30, true, 2),
('SSD-SAMSUNG-1TB', 'Samsung 980 PRO 1TB NVMe SSD', 'Ultra-fast NVMe SSD storage', 3, 7, 8000, 11999, 15, true, 2),
('USB-HUB-ADAPTER', '7-Port USB 3.0 Hub', 'USB hub with 7 ports', 3, 5, 2000, 3499, 25, true, 2)
ON CONFLICT (sku) DO NOTHING;

-- Insert inventory for products
INSERT INTO inventory (product_id, quantity_on_hand, quantity_reserved, quantity_available, warehouse_location)
SELECT id, 20, 2, 18, 'Shelf-A1' FROM products WHERE sku = 'DELL-XPS-13'
UNION ALL
SELECT id, 15, 1, 14, 'Shelf-A2' FROM products WHERE sku = 'HP-PAVILION-15'
UNION ALL
SELECT id, 12, 0, 12, 'Shelf-A3' FROM products WHERE sku = 'LENOVO-THINKPAD-L14'
UNION ALL
SELECT id, 8, 1, 7, 'Shelf-B1' FROM products WHERE sku = 'APPLE-MACBOOK-PRO'
UNION ALL
SELECT id, 10, 2, 8, 'Shelf-B2' FROM products WHERE sku = 'ASUS-ROG-GAMING'
UNION ALL
SELECT id, 25, 3, 22, 'Shelf-C1' FROM products WHERE sku = 'MONITOR-SAMSUNG-27'
UNION ALL
SELECT id, 50, 5, 45, 'Shelf-C2' FROM products WHERE sku = 'KEYBOARD-MECHANICAL'
UNION ALL
SELECT id, 60, 8, 52, 'Shelf-C3' FROM products WHERE sku = 'MOUSE-LOGITECH'
UNION ALL
SELECT id, 18, 2, 16, 'Shelf-D1' FROM products WHERE sku = 'SSD-SAMSUNG-1TB'
UNION ALL
SELECT id, 35, 5, 30, 'Shelf-D2' FROM products WHERE sku = 'USB-HUB-ADAPTER'
ON CONFLICT DO NOTHING;

-- Insert demo customers
INSERT INTO customers (customer_name, email, phone, address, city, state, postal_code, customer_type, loyalty_points, total_purchases, is_active, created_by) VALUES
('Rajesh Kumar', 'rajesh@example.com', '9876543210', '123 Tech Street', 'Bangalore', 'KA', '560001', 'RETAIL', 500, 250000, true, 2),
('Priya Sharma', 'priya@example.com', '9876543211', '456 Digital Avenue', 'Mumbai', 'MH', '400001', 'RETAIL', 300, 180000, true, 2),
('Amit Patel', 'amit@example.com', '9876543212', '789 Innovation Road', 'Ahmedabad', 'GJ', '380001', 'CORPORATE', 1000, 500000, true, 2),
('Sneha Gupta', 'sneha@example.com', '9876543213', '321 Business Park', 'Delhi', 'DL', '110001', 'RETAIL', 200, 95000, true, 2),
('Vikram Singh', 'vikram@example.com', '9876543214', '654 Commerce Square', 'Pune', 'MH', '411001', 'RETAIL', 450, 320000, true, 2)
ON CONFLICT (email) DO NOTHING;

-- Insert demo suppliers
INSERT INTO suppliers (supplier_name, contact_person, email, phone, address, city, state, postal_code, payment_terms, is_active, created_by) VALUES
('Dell Distributors India', 'Mr. Sharma', 'sales@dellind.com', '9111111111', 'Industrial Area', 'Bangalore', 'KA', '560001', '30 days', true, 2),
('HP Wholesale', 'Ms. Gupta', 'wholesale@hpind.com', '9222222222', 'Business Complex', 'Mumbai', 'MH', '400001', '45 days', true, 2),
('Lenovo Direct', 'Mr. Mishra', 'direct@lenovoind.com', '9333333333', 'Tech Hub', 'Hyderabad', 'TG', '500001', '30 days', true, 2),
('Apple Authorized', 'Ms. Iyer', 'authorized@appledir.com', '9444444444', 'Premium Plaza', 'Chennai', 'TN', '600001', '14 days', true, 2),
('Tech Components Ltd', 'Mr. Nair', 'components@tech.com', '9555555555', 'Industrial Estate', 'Pune', 'MH', '411001', '30 days', true, 2)
ON CONFLICT (supplier_name) DO NOTHING;

COMMIT;

