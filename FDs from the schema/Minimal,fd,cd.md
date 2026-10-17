1. FUNCTIONAL DEPENDENCIES (FDs)

1.1. MERCHANT
merchant_id → email, display_name, registered_at, verification_status
email → merchant_id

1.2. INDIVIDUAL_MERCHANT
merchant_id → first_name, last_name, national_id, date_of_birth
national_id → merchant_id

1.3. BUSINESS_MERCHANT
merchant_id → legal_name, business_registration_no, tax_number, contact_person
business_registration_no → merchant_id

1.4. CUSTOMER
customer_id → email, password_hash, first_name, last_name, registered_at
email → customer_id

1.5. CATEGORY
category_id → category_name, is_active, parent_id

1.6. SERVICE
service_id → merchant_id, category_id, title, description, list_price, duration, is_offered, created_at

1.7. BOOKING
booking_id → customer_id, service_id, payment_id, booking_datetime, status, charged_price, customer_note, created_at

1.8. PAYMENT
payment_id → amount_paid, payment_method, payment_status, platform_commission, payment_date

1.9. REVIEW
review_id → booking_id, score, comment, created_at
booking_id → review_id

1.10. CUSTOMER_SAVED_SERVICE
(customer_id, service_id) → saved_at


2. MINIMAL COVER

2.1. MERCHANT
merchant_id → email
merchant_id → display_name
merchant_id → registered_at
merchant_id → verification_status
email → merchant_id

2.2. INDIVIDUAL_MERCHANT
merchant_id → first_name
merchant_id → last_name
merchant_id → national_id
merchant_id → date_of_birth
national_id → merchant_id

2.3. BUSINESS_MERCHANT
merchant_id → legal_name
merchant_id → business_registration_no
merchant_id → tax_number
merchant_id → contact_person
business_registration_no → merchant_id

2.4. CUSTOMER
customer_id → email
customer_id → password_hash
customer_id → first_name
customer_id → last_name
customer_id → registered_at
email → customer_id

2.5. CATEGORY
category_id → category_name
category_id → is_active
category_id → parent_id

2.6. SERVICE
service_id → merchant_id
service_id → category_id
service_id → title
service_id → description
service_id → list_price
service_id → duration
service_id → is_offered
service_id → created_at

2.7. BOOKING
booking_id → customer_id
booking_id → service_id
booking_id → payment_id
booking_id → booking_datetime
booking_id → status
booking_id → charged_price
booking_id → customer_note
booking_id → created_at

2.8. PAYMENT
payment_id → amount_paid
payment_id → payment_method
payment_id → payment_status
payment_id → platform_commission
payment_id → payment_date

2.9. REVIEW
review_id → booking_id
review_id → score
review_id → comment
review_id → created_at
booking_id → review_id

2.10. CUSTOMER_SAVED_SERVICE
(customer_id, service_id) → saved_at


3. CANDIDATE KEYS

MERCHANT: merchant_id, email
INDIVIDUAL_MERCHANT: merchant_id, national_id
BUSINESS_MERCHANT: merchant_id, business_registration_no
CUSTOMER: customer_id, email
CATEGORY: category_id
SERVICE: service_id
BOOKING: booking_id
PAYMENT: payment_id
REVIEW: review_id, booking_id
CUSTOMER_SAVED_SERVICE: (customer_id, service_id)


4. NORMAL FORMS

Relation                  1NF    2NF    3NF    BCNF
MERCHANT                  Yes    Yes    Yes    Yes
INDIVIDUAL_MERCHANT       Yes    Yes    Yes    Yes
BUSINESS_MERCHANT         Yes    Yes    Yes    Yes
CUSTOMER                  Yes    Yes    Yes    Yes
CATEGORY                  Yes    Yes    Yes    Yes
SERVICE                   Yes    Yes    Yes    Yes
BOOKING                   Yes    Yes    Yes    Yes
PAYMENT                   Yes    Yes    Yes    Yes
REVIEW                    Yes    Yes    Yes    Yes
CUSTOMER_SAVED_SERVICE    Yes    Yes    Yes    Yes


5. EXPLANATION OF NORMAL FORMS

1NF:
All attributes contain single values, and there are no repeating groups. Therefore, all relations satisfy 1NF.

2NF:
All relations satisfy 1NF. Relations with single-attribute candidate keys have no partial dependencies. In CUSTOMER_SAVED_SERVICE, saved_at depends on the complete composite key (customer_id, service_id). Therefore, all relations satisfy 2NF.

3NF:
The identified functional dependencies do not introduce transitive dependencies that violate 3NF. Therefore, all relations satisfy 3NF.

BCNF:
For the identified functional dependencies, every determinant of a non-trivial functional dependency is a superkey. Therefore, all relations satisfy BCNF.


6. CONCLUSION

Based on the provided SQL schema and identified functional dependencies, all ten relations satisfy BCNF.

Candidate keys are identified using the primary key and UNIQUE constraints. The minimal cover uses single-attribute right-hand sides and removes redundant dependencies.

