# Key Features of the Service Marketplace System (with Proposed Additions)

The base features come from the assignment. The additions (marked **[+]**) are my suggestions, not requirements, and I've included all eight since you didn't pick specific ones. Cut this down to the 2 to 4 the group can actually deliver.

## 1. Business features

| Area | Base features (from the assignment) | Proposed additions [+] |
|---|---|---|
| Merchants | Profile, category, verification status | **[+] Individual or business merchants** as ISA subtypes with disjointness and completeness constraints |
| Services | Title, description, price, duration, currently-offered flag | **[+] Merchant availability**: working time slots, with no two overlapping bookings |
| Customers | Account, one or more addresses, one or more phone numbers | **[+] Favourites**: saved services with the date saved (M:N with its own attribute) |
| Bookings | Customer books a service for a date and time; status: requested, confirmed, completed, cancelled | **[+] Status history**: weak entity recording each status change with a timestamp<br>**[+] Cancellation**: reason and who cancelled |
| Actual price | Booking stores the price actually charged, which may differ from the list price | **[+] Vouchers**: a discount applied to a booking, explaining the price difference |
| Payments and commission | Amount, method, date, share retained by the platform | **[+] Commission rate per category** with effective dates, stored on each payment as the rate applied<br>**[+] Refunds**: refund records linked to a payment<br>**[+] Merchant payouts**: what each merchant receives (payment minus commission) per period |
| Reviews | Score and comment, only for completed bookings | None |

## 2. Reporting features

Base (the topic's 5 queries):

1. The services of one merchant with their average review score.
2. Total commission earned by the platform per month.
3. Customers who have booked services from more than three different merchants.
4. Merchants with no booking at all in the last 60 days.
5. The category in which the average booking value is highest.

Possible additions if the group takes the matching features:

- **[+]** Total payout per merchant per period (payouts).
- **[+]** Refund amount per month (refunds).
- **[+]** Voucher usage and total discount given (vouchers).
- **[+]** Most-favourited services (favourites).

## 3. Java application features

Base (minimum requirements):

- Database connection using settings held outside the code.
- **Browse** form with row count.
- **Search** form using a parameterised query.
- **Master-detail** form (for example, a booking and its payment).
- **Editing** form with insert, update, and delete, and confirmation before a delete.
- **Report** screen showing an aggregate query result.
- At least 4 of the topic's 5 queries reachable from the interface.

Additions [+] are optional and don't need their own screens, but anything shown in the demo must run real SQL.

## 4. Required technical features at the database level

- Constraints: PK, FK (with ON DELETE/UPDATE), NOT NULL, UNIQUE, CHECK, DEFAULT.
- At least one transaction changing two tables together (COMMIT/ROLLBACK). Base example: confirming a payment and recording the commission. **[+]** With refunds, cancelling a booking and recording the refund in one transaction is a second natural example.
- At least one index and one view.
- `PreparedStatement` everywhere, constraint violations explained in plain language, and input validated before it reaches the database.

## 5. Which additions cover which assignment requirements

| Addition | Requirement it helps satisfy |
|---|---|
| Status history | Weak entity set with identifying relationship and partial key |
| Favourites | Many-to-many relationship with its own attribute |
| Individual or business merchants | ISA hierarchy (the customer phone numbers already give you a multivalued attribute) |
| Refunds, payouts | Extra transactions and two-sided reporting |
| Availability | Overlap rule needing a trigger or special check (hardest; expect close questioning at the demo) |

Whichever additions the group keeps, add them to the business rules before drawing the ER diagram. I can also put this list into a Markdown file for the `docs/` folder of your repo if you'd like.
