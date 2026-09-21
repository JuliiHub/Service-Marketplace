# Work Allocation for a 10-Person Group: Service Marketplace Topic


## 1. Roles

| Member | Role | Main tasks |
|---|---|---|
| M1 | Group leader | Schedule, deadlines, submissions, contact with the instructor, weekly updates to the work-allocation Google Sheet, attaching group information to every deliverable |
| M2, M3 | Data modellers | Business rules, ER diagram, mapping to the relational schema (both draw it together and both defend it) |
| M4 | Normalization lead | Functional dependencies, minimal cover, candidate keys, 3NF/BCNF normalization, lossless-decomposition proofs |
| M5 | Database developer | CREATE TABLE script (PK, FK, NOT NULL, UNIQUE, CHECK, DEFAULT, ON DELETE/UPDATE), at least 15 rows of sample data per table, index, triggers if needed |
| M6, M7 | Query developers | 12 SQL queries (each preceded by its English question), 4 of them also in relational algebra, the view, the INSERT/UPDATE/DELETE statements and the transaction |
| M8, M9 | Java developers | JDBC layer, forms (browse, search, master–detail, editing, report), validation, constraint-error handling |
| M10 | Tester and writer | Constraint testing, application testing, integrating the final report, appendix declaring AI use |

## 2. Timeline

| Phase | Main work | Who |
|---|---|---|
| Before end of week 2 | Register the group and topic on the Google Sheet | M1 |
| Weeks 1–3 | Whole-group ER and business-rules discussion (the assignment requires every member to take part) | Everyone |
| Week 3 | D1 – project proposal (1–3 pages), arguing for the topic's Objective sentence | M1 writes, M2–M3 contribute domain content |
| Weeks 3–5 | D2 – ER diagram and draft schema (3–5 pages) | M2, M3; M4 starts listing functional dependencies; M10 compiles |
| Weeks 4–7 | Functional dependencies and normalization | M4, cross-checked with M2–M3 |
| Weeks 5–8 | Table-creation script and sample data | M5, with M6–M7 helping on sample data since they know what data the queries need |
| Weeks 6–9 | 12 queries and relational algebra | M6, M7 |
| Weeks 6–10 | Java application | M8, M9; M5 helps after finishing the script; M6–M7 help with the screens that run the topic's queries |
| Weeks 8–10 | Constraint and application testing, compiling the report (25+ pages), README, D5, D6 | M10 leads, everyone reviews |
| Week 10 | Submit D3, D5, D6 | M1 |
| Weeks 11–13 | Rehearse the 15-minute presentation, practise the demo and cross-questioning | Everyone |

## 3. Who Writes Which Report Section

| Report section | Owner |
|---|---|
| 1. Introduction, 10. Discussion | M1 (M2–M3 give input) |
| 2. Business rules, 3. ER diagram | M2, M3 |
| 4. Relational schema | M2, M3 |
| 5. Functional dependencies, 6. Normalization | M4 |
| 7. Database implementation | M5 |
| 8. Queries | M6, M7 |
| 9. The application | M8, M9 (with a screenshot of every form and the SQL behind each screen) |
| 11. Work allocation, 12. References, AI-use appendix, overall integration and writing quality | M10 (M1 collects every member's signature) |

## 4. Avoiding Lost Individual Marks

- Grades are individual: members of the same group can receive different marks, and at the presentation each member is questioned individually. Answering "that was someone else's section" costs marks.
- Hold a "role swap" session every few weeks: one person explains another's part (for example, M8 explains a functional dependency, M4 explains which SQL statement a button runs).
- Log the work in the Google Sheet every week, because the instructor uses that record when assessing individual contribution.
- The other 30% of the project mark is individual participation in discussions at the three final presentation sessions, so each member should prepare one or two questions for other groups.
- Any section produced with substantial AI help must be declared in an appendix (what was generated and how it was verified), and you must still be able to explain it.

## 5. Points Specific to Service Marketplace for M2–M3 to Settle Early

The assignment requires at least 8 entity sets, 8 relationships, a weak entity set, an M:N relationship with its own attribute, one of (ISA hierarchy / multivalued attribute / ternary relationship), and a recursive relationship with role names on both edges. Some suggestions based on the topic's "Data to model" section (the group decides):

- Multivalued attribute: a customer's phone numbers. Addresses could be a weak entity of Customer (one way to satisfy the weak-entity requirement).
- Recursive relationship: the topic doesn't list one, so you could add sub-categories under Category (parent / sub-category) and name the roles.
- A booking must store the price actually charged, which may differ from the current list price.
- A review can exist only for a booking with status "completed". This is hard to show in an ER diagram, so write it as a business rule and enforce it with a trigger or application-level check, with a justification.
- The platform's commission should be separated from the payment amount so both sides can be reported.


If you like, I can draft an initial list of 15–20 business rules for Service Marketplace, a first-draft list of entities and relationships, or a weekly work-allocation table to paste into your Google Sheet.
