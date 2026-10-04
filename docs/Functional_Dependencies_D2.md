Functional Dependencies of the D2 Relational Schema

1. MERCHANT

merchant_id $\to$ {email, display_name, verification_status, registered_at}

email $\to$ {merchant_id, display_name, verification_status, registered_at}

2. INDIVIDUAL_MERCHANT

merchant_id $\to$ {first_name, last_name, date_of_birth, national_id}

national_id $\to$ {merchant_id, first_name, last_name, date_of_birth}

3. BUSINESS_MERCHANT

merchant_id $\to$ {legal_name, business_registration_no, tax_number, contact_person}

business_registration_no $\to$ {merchant_id, legal_name, tax_number, contact_person}

tax_number $\to$ {merchant_id, legal_name, business_registration_no, contact_person}

4. CUSTOMER

customer_id $\to$ {email, full_name, password_hash, registered_at}

email $\to$ {customer_id, full_name, password_hash, registered_at}

5. CUSTOMER_ADDRESS

{customer_id, address_no} $\to$ {address_label, street, city, postal_code, is_default}

6. CUSTOMER_PHONE

{customer_id, phone_number} $\to$ {is_primary}

7. CATEGORY

category_id $\to$ {parent_category_id, category_name, description, is_active}

category_name $\to$ {category_id, parent_category_id, description, is_active}

8. SERVICE

service_id $\to$ {merchant_id, category_id, service_name, description, list_price, duration, is_offered, created_at}

9. BOOKING

booking_id $\to$ {customer_id, service_id, booking_datetime, charged_price, status, customer_note, created_at}

10. BOOKING_STATUS_HISTORY

{booking_id, seq_no} $\to$ {status, changed_at, change_reason}

11. PAYMENT

payment_id $\to$ {booking_id, amount_paid, payment_method, payment_date, platform_commission, payment_status, transaction_reference}

transaction_reference $\to$ {payment_id, booking_id, amount_paid, payment_method, payment_date, platform_commission, payment_status}

booking_id $\to$ {payment_id, amount_paid, payment_method, payment_date, platform_commission, payment_status, transaction_reference}

12. REVIEW

review_id $\to$ {booking_id, customer_id, score, comment, review_date}

booking_id $\to$ {review_id, customer_id, score, comment, review_date}

13. FAVOURITE

{customer_id, service_id} $\to$ {saved_at}
