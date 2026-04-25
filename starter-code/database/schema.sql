-- Schema and seed for cv-jasper-reports workshop

CREATE TABLE IF NOT EXISTS categories (
    id        SERIAL PRIMARY KEY,
    name      VARCHAR(100) NOT NULL,
    description TEXT
);

CREATE TABLE IF NOT EXISTS products (
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description TEXT,
    price       DECIMAL(10, 2) NOT NULL CHECK (price > 0),
    stock       INT NOT NULL DEFAULT 0 CHECK (stock >= 0),
    category_id INT REFERENCES categories (id),
    created_at  TIMESTAMP DEFAULT NOW()
);

-- Seed: categories
INSERT INTO categories (name, description) VALUES
    ('Electrónica',  'Dispositivos y componentes electrónicos'),
    ('Oficina',      'Insumos y equipos de oficina'),
    ('Herramientas', 'Herramientas manuales y eléctricas');

-- Seed: products
INSERT INTO products (name, description, price, stock, category_id) VALUES
    ('Laptop Pro 15',       'Laptop de alto rendimiento',  1299.99, 12, 1),
    ('Auriculares BT',      'Auriculares inalámbricos',      89.50,  0, 1),
    ('Monitor 27"',         'Monitor 4K IPS',               449.00,  8, 1),
    ('Teclado Mecánico',    'Switches Cherry MX Red',        120.00, 25, 1),
    ('Silla Ergonómica',    'Silla de oficina con lumbar',   350.00,  5, 2),
    ('Escritorio Ajust.',   'Escritorio regulable en altura',480.00,  3, 2),
    ('Resma de Papel A4',   '500 hojas, 75 g/m²',              8.50, 200, 2),
    ('Taladro 18V',         'Taladro percutor inalámbrico', 175.00,  15, 3),
    ('Juego de Destornill.','Set de 12 destornilladores',    35.00,   30, 3),
    ('Sierra Circular',     'Sierra 7¼" 1800 W',            220.00,   7, 3);
