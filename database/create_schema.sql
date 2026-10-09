
CREATE TABLE category (
    category_id INT PRIMARY KEY,
    category_name VARCHAR(255) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    parent_id INT NULL,
    FOREIGN KEY (parent_id) REFERENCES category(category_id) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE merchant (
    merchant_id INT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    display_name VARCHAR(255) NOT NULL,
    registered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    verification_status VARCHAR(50) DEFAULT 'PENDING',
    CONSTRAINT chk_merchant_status CHECK (verification_status IN ('PENDING', 'VERIFIED', 'REJECTED'))
);

CREATE TABLE individual_merchant (
    merchant_id INT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    national_id VARCHAR(50) NOT NULL UNIQUE,
    date_of_birth DATE,
    FOREIGN KEY (merchant_id) REFERENCES merchant(merchant_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE business_merchant (
    merchant_id INT PRIMARY KEY,
    legal_name VARCHAR(255) NOT NULL,
    business_registration_no VARCHAR(100) NOT NULL UNIQUE,
    tax_number VARCHAR(100),
    contact_person VARCHAR(255),
    FOREIGN KEY (merchant_id) REFERENCES merchant(merchant_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE service (
    service_id INT PRIMARY KEY,
    merchant_id INT NOT NULL,
    category_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    list_price DECIMAL(12, 2) NOT NULL,
    duration INT NOT NULL, 
    is_offered BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_service_price CHECK (list_price >= 0),
    CONSTRAINT chk_service_duration CHECK (duration > 0),
    FOREIGN KEY (merchant_id) REFERENCES merchant(merchant_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (category_id) REFERENCES category(category_id) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE customer (
    customer_id INT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    registered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE payment (
    payment_id INT PRIMARY KEY,
    amount_paid DECIMAL(12, 2) NOT NULL,
    payment_method VARCHAR(50) NOT NULL,
    payment_status VARCHAR(50) DEFAULT 'PENDING',
    platform_commission DECIMAL(12, 2) NOT NULL DEFAULT 0.00,
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_payment_amount CHECK (amount_paid >= 0),
    CONSTRAINT chk_payment_status CHECK (payment_status IN ('PENDING', 'COMPLETED', 'FAILED', 'REFUNDED'))
);

CREATE TABLE booking (
    booking_id INT PRIMARY KEY,
    customer_id INT NOT NULL,
    service_id INT NOT NULL,
    payment_id INT NULL UNIQUE,
    booking_datetime TIMESTAMP NOT NULL,
    status VARCHAR(50) DEFAULT 'REQUESTED',
    charged_price DECIMAL(12, 2) NOT NULL,
    customer_note TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_booking_status CHECK (status IN ('REQUESTED', 'CONFIRMED', 'COMPLETED', 'CANCELLED')),
    CONSTRAINT chk_charged_price CHECK (charged_price >= 0),
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (service_id) REFERENCES service(service_id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (payment_id) REFERENCES payment(payment_id) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE review (
    review_id INT PRIMARY KEY,
    booking_id INT NOT NULL UNIQUE,
    score INT NOT NULL,
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_review_score CHECK (score BETWEEN 1 AND 5),
    FOREIGN KEY (booking_id) REFERENCES booking(booking_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE customer_saved_service (
    customer_id INT NOT NULL,
    service_id INT NOT NULL,
    saved_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (customer_id, service_id),
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (service_id) REFERENCES service(service_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE INDEX idx_booking_datetime_status ON booking(booking_datetime, status);
CREATE INDEX idx_service_category_price ON service(category_id, list_price);
