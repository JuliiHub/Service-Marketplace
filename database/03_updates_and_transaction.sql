-- Mutation and transaction deliverables.
-- Dialect: MySQL 8.0-compatible SQL.

-- INSERT: add a new offered service for an existing verified merchant.
INSERT INTO service (
    service_id, merchant_id, category_id, title, description,
    list_price, duration, is_offered
) VALUES (
    216, 101, 5, 'Chăm sóc da cổ chuyên sâu',
    'Liệu trình chăm sóc và cấp ẩm vùng cổ',
    280000.00, 45, TRUE
);

-- UPDATE: change the price of the newly added service.
UPDATE service
SET list_price = 300000.00
WHERE service_id = 216;

-- DELETE: remove a customer's saved-service entry.
DELETE FROM customer_saved_service
WHERE customer_id = 301
  AND service_id = 201;

-- Transaction: create a booking and its payment atomically.
-- If either statement fails, the booking and payment are both rolled back.
BEGIN;

INSERT INTO payment (
    payment_id, amount_paid, payment_method, payment_status,
    platform_commission, payment_date
) VALUES (
    499, 300000.00, 'MOMO', 'COMPLETED',
    30000.00, CURRENT_TIMESTAMP
);

INSERT INTO booking (
    booking_id, customer_id, service_id, payment_id,
    booking_datetime, status, charged_price, customer_note
) VALUES (
    599, 301, 216, 499,
    DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 2 DAY),
    'CONFIRMED', 300000.00, 'Please confirm the appointment time.'
);

COMMIT;

-- To cancel this transaction instead of committing it, use:
-- ROLLBACK;
