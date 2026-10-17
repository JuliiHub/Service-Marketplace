-- Query deliverables for the Service Marketplace database.
-- Dialect: MySQL 8.0-compatible SQL.

-- Q1. Which offered services cost no more than 400,000 VND?
SELECT service_id, title, list_price, duration
FROM service
WHERE is_offered = TRUE
  AND list_price <= 400000
ORDER BY list_price, title;

-- Q2. Which merchants provide each offered service, and in which category?
SELECT s.service_id,
       s.title,
       m.display_name AS merchant_name,
       c.category_name,
       s.list_price
FROM service AS s
JOIN merchant AS m ON m.merchant_id = s.merchant_id
JOIN category AS c ON c.category_id = s.category_id
WHERE s.is_offered = TRUE
ORDER BY c.category_name, s.title;

-- Q3. Which customers have made at least one booking?
SELECT DISTINCT c.customer_id,
       c.first_name,
       c.last_name,
       c.email
FROM customer AS c
JOIN booking AS b ON b.customer_id = c.customer_id
ORDER BY c.customer_id;

-- Q4. What is the total booking revenue for each service?
SELECT s.service_id,
       s.title,
       COUNT(b.booking_id) AS booking_count,
       COALESCE(SUM(b.charged_price), 0) AS total_revenue
FROM service AS s
LEFT JOIN booking AS b ON b.service_id = s.service_id
GROUP BY s.service_id, s.title
ORDER BY total_revenue DESC, s.title;

-- Q5. Which services have an average review score of at least 4?
SELECT s.service_id,
       s.title,
       ROUND(AVG(r.score), 2) AS average_score,
       COUNT(r.review_id) AS review_count
FROM service AS s
JOIN booking AS b ON b.service_id = s.service_id
JOIN review AS r ON r.booking_id = b.booking_id
GROUP BY s.service_id, s.title
HAVING AVG(r.score) >= 4
ORDER BY average_score DESC, review_count DESC;

-- Q6. What are the top three merchants by completed booking revenue?
SELECT m.merchant_id,
       m.display_name,
       COUNT(b.booking_id) AS completed_bookings,
       SUM(b.charged_price) AS completed_revenue
FROM merchant AS m
JOIN service AS s ON s.merchant_id = m.merchant_id
JOIN booking AS b ON b.service_id = s.service_id
WHERE b.status = 'COMPLETED'
GROUP BY m.merchant_id, m.display_name
ORDER BY completed_revenue DESC
LIMIT 3;

-- Q7. Which categories contain more than one offered service?
SELECT c.category_id,
       c.category_name,
       COUNT(s.service_id) AS offered_service_count
FROM category AS c
JOIN service AS s ON s.category_id = c.category_id
WHERE s.is_offered = TRUE
GROUP BY c.category_id, c.category_name
HAVING COUNT(s.service_id) > 1
ORDER BY offered_service_count DESC, c.category_name;

-- Q8. Which customers have never saved a service?
SELECT c.customer_id,
       c.first_name,
       c.last_name,
       c.email
FROM customer AS c
WHERE NOT EXISTS (
    SELECT 1
    FROM customer_saved_service AS css
    WHERE css.customer_id = c.customer_id
)
ORDER BY c.customer_id;

-- Q9. What is the monthly payment total and commission for completed payments?
SELECT DATE_FORMAT(payment_date, '%Y-%m-01') AS payment_month,
       SUM(amount_paid) AS total_paid,
       SUM(platform_commission) AS total_commission
FROM payment
WHERE payment_status = 'COMPLETED'
GROUP BY DATE_FORMAT(payment_date, '%Y-%m-01')
ORDER BY payment_month;

-- Q10. Which services are more expensive than the average offered-service price?
SELECT service_id, title, list_price
FROM service
WHERE is_offered = TRUE
  AND list_price > (
      SELECT AVG(list_price)
      FROM service
      WHERE is_offered = TRUE
  )
ORDER BY list_price DESC;

-- Q11. Which merchants have no currently offered services?
SELECT m.merchant_id,
       m.display_name
FROM merchant AS m
WHERE NOT EXISTS (
    SELECT 1
    FROM service AS s
    WHERE s.merchant_id = m.merchant_id
      AND s.is_offered = TRUE
)
ORDER BY m.merchant_id;

-- Q12. What is each customer's booking count and total amount, including customers with none?
SELECT c.customer_id,
       c.first_name,
       c.last_name,
       COUNT(b.booking_id) AS booking_count,
       COALESCE(SUM(b.charged_price), 0) AS total_charged
FROM customer AS c
LEFT JOIN booking AS b ON b.customer_id = c.customer_id
GROUP BY c.customer_id, c.first_name, c.last_name
ORDER BY total_charged DESC, c.customer_id;

-- View: reusable catalogue of currently offered services.
CREATE OR REPLACE VIEW v_offered_service_catalog AS
SELECT s.service_id,
       s.title,
       s.description,
       s.list_price,
       s.duration,
       m.merchant_id,
       m.display_name AS merchant_name,
       c.category_id,
       c.category_name
FROM service AS s
JOIN merchant AS m ON m.merchant_id = s.merchant_id
JOIN category AS c ON c.category_id = s.category_id
WHERE s.is_offered = TRUE
  AND m.verification_status = 'VERIFIED'
  AND c.is_active = TRUE;

-- Example use of the view:
-- SELECT * FROM v_offered_service_catalog ORDER BY category_name, title;
