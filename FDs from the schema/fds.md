MERCHANT
merchant_id → email, display_name, verification_status, registered_at
email → merchant_id, display_name, verification_status, registered_at

INDIVIDUAL_MERCHANT
merchant_id → first_name, last_name, date_of_birth, national_id
national_id → merchant_id, first_name, last_name, date_of_birth

BUSINESS_MERCHANT
merchant_id → legal_name, business_registration_no, tax_number, contact_person
business_registration_no → merchant_id, legal_name, tax_number, contact_person
tax_number → merchant_id, legal_name, business_registration_no, contact_person

CUSTOMER
customer_id → email, first_name, last_name, password_hash, registered_at
email → customer_id, first_name, last_name, password_hash, registered_at

CUSTOMER_ADDRESS
(customer_id, address_no) → address_label, street, city, postal_code

CUSTOMER_PHONE
(customer_id, phone_number) → is_primary

CATEGORY
category_id → parent_category_id, category_name, is_active
category_name → category_id, parent_category_id, is_active

SERVICE
service_id → merchant_id, category_id, title, description, list_price, duration, is_offered, created_at

BOOKING
booking_id → customer_id, service_id, booking_datetime, charged_price, status, customer_note, created_at

BOOKING_STATUS_HISTORY
(booking_id, seq_no) → status, changed_time, change_reason

PAYMENT
payment_id → booking_id, amount_paid, payment_method, payment_date, platform_commission, payment_status, transaction_reference
transaction_reference → payment_id, booking_id, amount_paid, payment_method, payment_date, platform_commission, payment_status
booking_id → payment_id, amount_paid, payment_method, payment_date, platform_commission, payment_status, transaction_reference

REVIEW
review_id → booking_id, score, comment, review_date
booking_id → review_id, score, comment, review_date

FAVOURITE
(customer_id, service_id) → saved_at
