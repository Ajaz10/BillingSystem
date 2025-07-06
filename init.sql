-- Supermarket Management System - Sample Data Initialization
-- This script is automatically executed when PostgreSQL container starts

-- Enable UUID extension (if needed in future)
-- CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Insert sample categories
INSERT INTO categories (name, description, is_active, created_at, updated_at) VALUES
('Electronics', 'Electronic devices and accessories', true, NOW(), NOW()),
('Groceries', 'Food and grocery items', true, NOW(), NOW()),
('Clothing', 'Apparel and fashion items', true, NOW(), NOW()),
('Books', 'Books and educational materials', true, NOW(), NOW()),
('Sports', 'Sports equipment and accessories', true, NOW(), NOW()),
('Home & Garden', 'Home improvement and garden supplies', true, NOW(), NOW()),
('Health & Beauty', 'Personal care and health products', true, NOW(), NOW()),
('Toys & Games', 'Toys, games, and entertainment', true, NOW(), NOW())
ON CONFLICT (name) DO NOTHING;

-- Insert sample products
INSERT INTO products (name, description, price, stock, discount, image_url, low_stock_threshold, is_active, category_id, created_at, updated_at) VALUES
-- Electronics
('iPhone 15 Pro', 'Latest Apple smartphone with advanced features', 999.99, 25, 5.00, '/images/iphone15pro.jpg', 5, true, 1, NOW(), NOW()),
('Samsung Galaxy S24', 'Premium Android smartphone', 899.99, 30, 0.00, '/images/galaxys24.jpg', 5, true, 1, NOW(), NOW()),
('MacBook Air M2', 'Lightweight laptop with M2 chip', 1199.99, 15, 10.00, '/images/macbookair.jpg', 3, true, 1, NOW(), NOW()),
('Sony WH-1000XM5', 'Noise-cancelling wireless headphones', 349.99, 50, 15.00, '/images/sony-headphones.jpg', 10, true, 1, NOW(), NOW()),
('iPad Pro 12.9"', 'Professional tablet for creative work', 1099.99, 20, 8.00, '/images/ipadpro.jpg', 5, true, 1, NOW(), NOW()),

-- Groceries
('Organic Bananas', 'Fresh organic bananas - 1 lb', 2.99, 100, 0.00, '/images/bananas.jpg', 20, true, 2, NOW(), NOW()),
('Whole Milk', 'Fresh whole milk - 1 gallon', 4.49, 75, 0.00, '/images/milk.jpg', 15, true, 2, NOW(), NOW()),
('Sourdough Bread', 'Artisan sourdough bread loaf', 5.99, 40, 0.00, '/images/bread.jpg', 10, true, 2, NOW(), NOW()),
('Free-Range Eggs', 'Organic free-range eggs - dozen', 6.99, 60, 0.00, '/images/eggs.jpg', 12, true, 2, NOW(), NOW()),
('Avocados', 'Fresh Hass avocados - 4 pack', 7.99, 80, 10.00, '/images/avocados.jpg', 15, true, 2, NOW(), NOW()),

-- Clothing
('Levi''s 501 Jeans', 'Classic straight-leg denim jeans', 89.99, 45, 20.00, '/images/levis-jeans.jpg', 8, true, 3, NOW(), NOW()),
('Nike Air Max Sneakers', 'Comfortable running sneakers', 129.99, 35, 15.00, '/images/nike-airmax.jpg', 7, true, 3, NOW(), NOW()),
('Cotton T-Shirt', 'Premium cotton crew neck t-shirt', 24.99, 120, 0.00, '/images/tshirt.jpg', 20, true, 3, NOW(), NOW()),
('Winter Jacket', 'Waterproof winter jacket with hood', 199.99, 25, 25.00, '/images/winter-jacket.jpg', 5, true, 3, NOW(), NOW()),

-- Books
('The Great Gatsby', 'Classic American literature', 12.99, 50, 0.00, '/images/gatsby.jpg', 10, true, 4, NOW(), NOW()),
('Programming Java', 'Comprehensive Java programming guide', 49.99, 30, 10.00, '/images/java-book.jpg', 5, true, 4, NOW(), NOW()),
('Cookbook Deluxe', 'Collection of gourmet recipes', 29.99, 40, 5.00, '/images/cookbook.jpg', 8, true, 4, NOW(), NOW()),

-- Sports
('Yoga Mat', 'Non-slip exercise yoga mat', 39.99, 60, 12.00, '/images/yoga-mat.jpg', 12, true, 5, NOW(), NOW()),
('Basketball', 'Official size basketball', 24.99, 75, 0.00, '/images/basketball.jpg', 15, true, 5, NOW(), NOW()),
('Tennis Racket', 'Professional tennis racket', 159.99, 20, 18.00, '/images/tennis-racket.jpg', 4, true, 5, NOW(), NOW()),

-- Home & Garden
('Garden Shovel', 'Durable stainless steel garden shovel', 34.99, 40, 0.00, '/images/shovel.jpg', 8, true, 6, NOW(), NOW()),
('LED Desk Lamp', 'Adjustable LED desk lamp', 69.99, 30, 15.00, '/images/desk-lamp.jpg', 6, true, 6, NOW(), NOW()),
('Plant Pot Set', 'Set of 3 ceramic plant pots', 24.99, 55, 8.00, '/images/plant-pots.jpg', 10, true, 6, NOW(), NOW()),

-- Health & Beauty
('Vitamin C Serum', 'Anti-aging vitamin C facial serum', 19.99, 80, 20.00, '/images/vitamin-c.jpg', 15, true, 7, NOW(), NOW()),
('Electric Toothbrush', 'Sonic electric toothbrush', 89.99, 25, 12.00, '/images/toothbrush.jpg', 5, true, 7, NOW(), NOW()),
('Moisturizer', 'Daily face moisturizer with SPF', 16.99, 70, 0.00, '/images/moisturizer.jpg', 12, true, 7, NOW(), NOW()),

-- Toys & Games
('LEGO City Set', 'Building blocks city construction set', 79.99, 35, 15.00, '/images/lego-city.jpg', 7, true, 8, NOW(), NOW()),
('Chess Set', 'Wooden chess set with carved pieces', 44.99, 20, 5.00, '/images/chess-set.jpg', 4, true, 8, NOW(), NOW()),
('Puzzle 1000 pieces', 'Scenic landscape jigsaw puzzle', 18.99, 45, 0.00, '/images/puzzle.jpg', 9, true, 8, NOW(), NOW())
ON CONFLICT DO NOTHING;

-- Create a default admin user (password will be 'admin123' when hashed)
-- Note: In a real application, you would hash this password properly
INSERT INTO users (username, password, email, role, first_name, last_name, phone_number, is_active, created_at, updated_at) VALUES
('admin', '$2a$10$X8yzEL9OhAKQGrJjSaLWAeG6DEO6n4pOsXs3Lj8HjKT9K8M2OKgGS', 'admin@supermarket.com', 'ADMIN', 'System', 'Administrator', '+1-555-0100', true, NOW(), NOW()),
('john_doe', '$2a$10$X8yzEL9OhAKQGrJjSaLWAeG6DEO6n4pOsXs3Lj8HjKT9K8M2OKgGS', 'john.doe@example.com', 'USER', 'John', 'Doe', '+1-555-0101', true, NOW(), NOW()),
('jane_smith', '$2a$10$X8yzEL9OhAKQGrJjSaLWAeG6DEO6n4pOsXs3Lj8HjKT9K8M2OKgGS', 'jane.smith@example.com', 'USER', 'Jane', 'Smith', '+1-555-0102', true, NOW(), NOW())
ON CONFLICT (username) DO NOTHING;

-- Create sample carts for users
INSERT INTO carts (user_id, created_at, updated_at) VALUES
(2, NOW(), NOW()),
(3, NOW(), NOW())
ON CONFLICT DO NOTHING;

-- Add some items to John Doe's cart
INSERT INTO cart_items (cart_id, product_id, quantity, created_at, updated_at) VALUES
(1, 1, 1, NOW(), NOW()),  -- iPhone 15 Pro
(1, 6, 3, NOW(), NOW()),  -- Organic Bananas
(1, 11, 1, NOW(), NOW())  -- Levi's Jeans
ON CONFLICT DO NOTHING;

-- Create some sample orders
INSERT INTO orders (user_id, total_amount, status, payment_status, order_date, delivery_address, phone_number, notes, created_at, updated_at) VALUES
(2, 1089.97, 'DELIVERED', 'PAID', NOW() - INTERVAL '7 days', '123 Main St, Anytown, USA 12345', '+1-555-0101', 'Please leave at front door', NOW() - INTERVAL '7 days', NOW() - INTERVAL '7 days'),
(3, 254.97, 'PROCESSING', 'PAID', NOW() - INTERVAL '2 days', '456 Oak Ave, Somewhere, USA 67890', '+1-555-0102', 'Ring doorbell', NOW() - INTERVAL '2 days', NOW() - INTERVAL '2 days'),
(2, 79.98, 'PENDING', 'PENDING', NOW() - INTERVAL '1 hour', '123 Main St, Anytown, USA 12345', '+1-555-0101', '', NOW() - INTERVAL '1 hour', NOW() - INTERVAL '1 hour')
ON CONFLICT DO NOTHING;

-- Add order items for the sample orders
INSERT INTO order_items (order_id, product_id, quantity, unit_price, created_at, updated_at) VALUES
-- Order 1 (John's completed order)
(1, 1, 1, 949.99, NOW() - INTERVAL '7 days', NOW() - INTERVAL '7 days'),  -- iPhone 15 Pro (with discount)
(1, 6, 5, 2.99, NOW() - INTERVAL '7 days', NOW() - INTERVAL '7 days'),    -- Bananas
(1, 7, 2, 4.49, NOW() - INTERVAL '7 days', NOW() - INTERVAL '7 days'),    -- Milk

-- Order 2 (Jane's processing order)
(2, 11, 1, 71.99, NOW() - INTERVAL '2 days', NOW() - INTERVAL '2 days'),  -- Levi's Jeans (with discount)
(2, 12, 1, 110.49, NOW() - INTERVAL '2 days', NOW() - INTERVAL '2 days'), -- Nike Sneakers (with discount)
(2, 13, 3, 24.99, NOW() - INTERVAL '2 days', NOW() - INTERVAL '2 days'),  -- T-Shirts

-- Order 3 (John's pending order)
(3, 20, 1, 39.99, NOW() - INTERVAL '1 hour', NOW() - INTERVAL '1 hour'),  -- Yoga Mat
(3, 23, 1, 39.99, NOW() - INTERVAL '1 hour', NOW() - INTERVAL '1 hour')   -- Garden Shovel
ON CONFLICT DO NOTHING;

-- Update product stock based on orders
UPDATE products SET stock = stock - 1 WHERE id = 1;  -- iPhone sold
UPDATE products SET stock = stock - 5 WHERE id = 6;  -- Bananas sold
UPDATE products SET stock = stock - 2 WHERE id = 7;  -- Milk sold
UPDATE products SET stock = stock - 1 WHERE id = 11; -- Jeans sold
UPDATE products SET stock = stock - 1 WHERE id = 12; -- Sneakers sold
UPDATE products SET stock = stock - 3 WHERE id = 13; -- T-shirts sold
UPDATE products SET stock = stock - 1 WHERE id = 20; -- Yoga mat sold
UPDATE products SET stock = stock - 1 WHERE id = 23; -- Shovel sold

-- Add indexes for better performance (these might already exist from JPA)
CREATE INDEX IF NOT EXISTS idx_products_name_search ON products USING gin(to_tsvector('english', name || ' ' || COALESCE(description, '')));
CREATE INDEX IF NOT EXISTS idx_products_price_range ON products(price) WHERE is_active = true;
CREATE INDEX IF NOT EXISTS idx_orders_date_range ON orders(order_date);
CREATE INDEX IF NOT EXISTS idx_orders_user_status ON orders(user_id, status);

-- Create a view for product analytics
CREATE OR REPLACE VIEW product_analytics AS
SELECT 
    p.id,
    p.name,
    c.name as category_name,
    p.price,
    p.stock,
    p.discount,
    p.is_active,
    COALESCE(SUM(oi.quantity), 0) as total_sold,
    COALESCE(SUM(oi.quantity * oi.unit_price), 0) as total_revenue,
    CASE 
        WHEN p.stock <= p.low_stock_threshold THEN 'LOW_STOCK'
        WHEN p.stock = 0 THEN 'OUT_OF_STOCK'
        ELSE 'IN_STOCK'
    END as stock_status
FROM products p
LEFT JOIN categories c ON p.category_id = c.id
LEFT JOIN order_items oi ON p.id = oi.product_id
LEFT JOIN orders o ON oi.order_id = o.id AND o.payment_status = 'PAID'
GROUP BY p.id, p.name, c.name, p.price, p.stock, p.discount, p.is_active, p.low_stock_threshold;

-- Create a view for daily sales summary
CREATE OR REPLACE VIEW daily_sales_summary AS
SELECT 
    DATE(o.order_date) as sale_date,
    COUNT(DISTINCT o.id) as total_orders,
    COUNT(DISTINCT o.user_id) as unique_customers,
    SUM(o.total_amount) as total_revenue,
    AVG(o.total_amount) as average_order_value,
    SUM(oi.quantity) as total_items_sold
FROM orders o
JOIN order_items oi ON o.id = oi.order_id
WHERE o.payment_status = 'PAID'
GROUP BY DATE(o.order_date)
ORDER BY sale_date DESC;

-- Create a view for category performance
CREATE OR REPLACE VIEW category_performance AS
SELECT 
    c.id,
    c.name as category_name,
    COUNT(p.id) as total_products,
    COUNT(CASE WHEN p.is_active THEN 1 END) as active_products,
    COALESCE(SUM(oi.quantity), 0) as total_sold,
    COALESCE(SUM(oi.quantity * oi.unit_price), 0) as total_revenue,
    AVG(p.price) as average_product_price
FROM categories c
LEFT JOIN products p ON c.id = p.category_id
LEFT JOIN order_items oi ON p.id = oi.product_id
LEFT JOIN orders o ON oi.order_id = o.id AND o.payment_status = 'PAID'
WHERE c.is_active = true
GROUP BY c.id, c.name
ORDER BY total_revenue DESC;

-- Insert some notification preferences (if we add this feature later)
-- This table doesn't exist yet, but shows future extensibility

-- Success message
DO $$
BEGIN
    RAISE NOTICE 'Sample data initialization completed successfully!';
    RAISE NOTICE 'Default admin credentials: username=admin, password=admin123';
    RAISE NOTICE 'Sample users: john_doe, jane_smith (password=admin123 for all)';
    RAISE NOTICE 'Database includes: % categories, % products, % users, % orders', 
        (SELECT COUNT(*) FROM categories),
        (SELECT COUNT(*) FROM products),
        (SELECT COUNT(*) FROM users),
        (SELECT COUNT(*) FROM orders);
END $$;