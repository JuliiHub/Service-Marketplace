# Project Roadmap

The fixed dates come from the assignment: group and topic registered by the end of week 2, D1 and topic confirmation in week 3, D2 in week 5, D3 with D5 and D6 in week 10, and the presentation (D4) in weeks 13 to 15. Week numbers assume the course runs one lecture per week, which the assignment implies but doesn't state. Owners use the earlier M1 to M10 roles.

### Tools and Programming Languages
The implementation will use JDK (fill in LTS version), Swing, JDBC, and SQL (MySQL dialect) with MySQL 8.0 or later, MySQL Connector/J, a build tool such as Maven, and a diagramming tool such as draw.io. The relational choices follow established database-modelling and normalisation principles [2], while official documentation supports the selected MySQL and Java platform [3], [4].

### References
[1] J.-C. Rochet and J. Tirole, "Platform Competition in Two-Sided Markets," Journal of the European Economic Association, vol. 1, no. 4, pp. 990–1029, 2003, doi: 10.1162/154247603322493212.
[2] A. Silberschatz, H. F. Korth, and S. Sudarshan, Database System Concepts, 7th ed. New York, NY, USA: McGraw-Hill, 2020, ISBN 978-0-07-802215-9.
[3] Oracle, MySQL Reference Manual. [Online]. Available: https://dev.mysql.com/doc/refman/8.0/en/ [Accessed: (fill in date)].
[4] Oracle, Java Platform, Standard Edition API Specification. [Online]. Available: https://docs.oracle.com/en/java/javase/ [Accessed: (fill in date)].



## Roadmap by phase

| Phase | Weeks | Goal | Main tasks | Exit check | Owners |
|---|---|---|---|---|---|
| 0. Setup | 1–2 | Group and topic registered, tooling ready | Register the group and topic on the Google Sheet (names, student IDs, phone numbers, topic); create the GitHub repo from the skeleton; set up the work-allocation sheet; agree roles | Registration confirmed before the end of week 2 | M1 |
| 1. Requirements and ER draft | 1–3 | Shared understanding of the domain | Whole-group discussion (mandatory for every member); write numbered business rules; decide which additions to keep; first ER draft | Business rules agreed; ER draft exists | Everyone; M2, M3 lead |
| 2. D1 proposal | 3 | Topic confirmed | 1–3 page proposal arguing for the topic's Objective sentence | **D1 submitted in week 3** | M1 writes, M2 and M3 contribute |
| 3. ER and schema draft | 3–5 | Diagram meets every minimum requirement | Finish the ER diagram (at least 8 entities and 8 relationships, weak entity, M:N with attribute, ISA or multivalued or ternary, self-relationship, cardinality and participation everywhere); map to a relational schema; start listing functional dependencies | **D2 submitted in week 5** (3–5 pages). This is also the last chance to change topic | M2, M3; M4 starts FDs; M10 compiles |
| 4. Normalization and database build | 5–8 | Working database | Minimal cover, candidate keys, 3NF or BCNF with lossless proofs; CREATE TABLE script with all constraints; at least 15 rows of sample data per table; index; constraint tests (duplicate key, missing foreign key, CHECK violation) | Script runs twice in a row on an empty server; **schema frozen at the end of week 7** | M4, M5; M6 and M7 help with sample data |
| 5. Queries | 6–9 | Queries return non-empty results | 12 SQL queries, each with its English question; 4 in relational algebra; the view; INSERT, UPDATE and DELETE for each main table; one two-table transaction with COMMIT and ROLLBACK | Every query returns rows on the sample data | M6, M7 |
| 6. Java application | 6–10 | Every screen runs real SQL | Connection layer with external config (weeks 6–7); browse and search forms (7–8); master-detail, editing, report and topic-query screens (8–9); validation and readable constraint errors; integration and testing (9–10); README | Fresh clone builds and runs; apostrophe test passes; live insert, update and delete visible in the database | M8, M9; M5, M6, M7 help once free |
| 7. Final report and submission | 8–10 | Complete 25+ page report | Write sections in parallel (see the earlier section-owner table); full draft by the end of week 9; group review; AI-use appendix; signed work allocation | **D3, D5 and D6 submitted in week 10** | M10 leads, M1 submits |
| 8. Presentation preparation | 11–12 | Every member can defend the whole design | Slides for 15 minutes; full demo dry run on a fresh clone; role-swap Q&A so each member explains another's part; prepare 1 to 2 questions for other groups | Every member answers questions on any part without help | Everyone; M1 schedules |
| 9. Presentation and discussion | 13–15 | Present and take part | 15-minute presentation and live demo; individual questioning; ask questions at the other groups' sessions (30% of the project mark) | Done | Everyone |

## Internal deadlines (set earlier than the official ones)

| Internal deadline | Reason |
|---|---|
| End of week 2 | Registration is fixed after that, and a late group is placed randomly by the instructor |
| Day 3 or 4 before D1 and D2 | Leaves a day for review and fixing the group-information copy that must accompany every deliverable |
| End of week 7 | Schema freeze, so the Java developers aren't building on a moving target |
| End of week 9 | Full report draft and working application, leaving week 10 for testing and submission only |

## Points to watch

- **Normalization versus schema freeze.** Lecture 8 covers normalization, but the app needs a stable schema by week 7. Start FDs in week 4 so any decomposition is found before the freeze.
- **Java work starts before the database is final.** M8 and M9 can begin the connection layer and generic forms against schema v1 in week 6, then adapt after the freeze.
- **Weeks 11 and 12 are your buffer.** There are no deliverables in them, so use them for slippage from week 10 and for rehearsal, not for new features.
- **Deadlines are fixed.** Late submission is accepted only for a documented reason agreed with the instructor before the due date. Another assignment or exam isn't a valid reason.
- **Extra features cost time.** If the group falls behind at the D2 checkpoint in week 5, drop the additions first.
