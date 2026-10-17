# Relational-algebra translations

The following four SQL queries are also expressed in extended relational algebra.
`σ` is selection, `π` is projection, `⋈` is join, `γ` is grouping/aggregation,
and `←` assigns an intermediate relation.

## Q1 — Offered services costing no more than 400,000 VND

**English question:** Which offered services cost no more than 400,000 VND?

```text
Q1 ← π service_id, title, list_price, duration
     (σ is_offered = TRUE ∧ list_price ≤ 400000 (SERVICE))
```

## Q2 — Services with merchant and category

**English question:** Which merchants provide each offered service, and in which category?

```text
Q2 ← π service_id, title, display_name, category_name, list_price
     (σ is_offered = TRUE
       (SERVICE ⋈ SERVICE.merchant_id = MERCHANT.merchant_id MERCHANT
               ⋈ SERVICE.category_id = CATEGORY.category_id CATEGORY))
```

## Q5 — Highly rated services

**English question:** Which services have an average review score of at least 4?

```text
R ← SERVICE ⋈ SERVICE.service_id = BOOKING.service_id BOOKING
     ⋈ BOOKING.booking_id = REVIEW.booking_id REVIEW

Q5 ← π service_id, title, average_score, review_count
     (σ average_score ≥ 4
       (γ service_id, title;
          AVG(score) → average_score,
          COUNT(review_id) → review_count (R)))
```

## Q8 — Customers who have never saved a service

**English question:** Which customers have never saved a service?

```text
Q8 ← π customer_id, first_name, last_name, email (CUSTOMER)
      − π customer_id, first_name, last_name, email
        (CUSTOMER ⋈ CUSTOMER.customer_id = CUSTOMER_SAVED_SERVICE.customer_id
                   CUSTOMER_SAVED_SERVICE)
```
