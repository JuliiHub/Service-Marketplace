# Week-by-Week Task Table

# There are more specific descriptions below if you need (PLEASE BE MORE ACTIVE)

This is organised by week and member, so each row shows what everyone is doing at that time. M1 to M10 are placeholders for real names. Weeks 1 to 3 are planning, weeks 4 to 5 are the ER and schema draft, weeks 6 to 10 are build and report, and weeks 11 to 15 are presentation.

## Weeks 1–5: Requirements, ER diagram, D1 and D2
if you don't know what your roll is, go to README
| Week | M1 | M2 | M3 | M4 | M5 | M6 | M7 | M8 | M9 | M10 |
|---|---|---|---|---|---|---|---|---|---|---|
| 1 | Register group and topic; create repo; set up work sheet | Business rules; entity list | Relationship draft | ER discussion | ER discussion | ER discussion | ER discussion | ER discussion | ER discussion | ER discussion |
| 2 | **Registration due (end of week 2)** | Business rules; entity attributes | Cardinality and participation | ER discussion | ER discussion | ER discussion | ER discussion | ER discussion | ER discussion | ER discussion |
| 3 | **D1 proposal; confirm topic** | Weak entity, multivalued attribute, ISA | Self-relationship with role names | ER discussion | ER discussion | ER discussion | ER discussion | ER discussion | ER discussion | ER discussion; start compiling D2 |
| 4 | Weekly check-in | Design-decision paragraphs | Map ER to schema | Start FD list | – | – | – | – | – | Compile D2 |
| 5 | **Submit D2** (last chance to change topic) | Finish D2 text | Finish schema for D2 | FDs from the schema | Start CREATE TABLE script | – | – | – | – | Finalise D2 |

## Weeks 6–10: Build, test and write

| Week | M1 | M2 | M3 | M4 | M5 | M6 | M7 | M8 | M9 | M10 |
|---|---|---|---|---|---|---|---|---|---|---|
| 6 | Check-in | Review script against ER | Update ER and schema if needed | Minimal cover, candidate keys, normal forms | CREATE TABLE script | SQL queries 1–6 | SQL queries 7–12 | Connection layer with external config | Project build, UI layout, README skeleton | Constraint tests once the script exists |
| 7 | Check-in; **schema freeze (end of week 7)** | Review script against ER | Update ER and schema if needed | Decompositions with lossless proofs; hand final schema to M5 | Script and sample data | Queries 1–6; help with sample data | Queries 7–12; help with sample data | Browse and search forms | Project build, UI layout, README skeleton | Constraint tests |
| 8 | Check-in | – | – | – | Sample data, index, triggers; confirm CHECK is enforced | Finish queries 1–6; start relational algebra | Finish queries 7–12; start updates and transaction | Search form; start editing form | Master-detail and report screens | Constraint tests; screen testing |
| 9 | Write Introduction and Discussion | Write sections 2–3 | Write section 4 | Write sections 5–6 | Write section 7; support Java devs | Relational algebra; half of section 8; wire report screen | Updates and transaction; other half of section 8; wire search screens | Editing form, validation, readable errors; half of section 9 | Finish master-detail and report; half of section 9; topic queries | **Assemble full report draft**; sections 11–12; screen testing |
| 10 | Collect signatures; **submit D3, D5, D6** | Review report | Review report | Review report | Review report; support Java devs | Review report | Review report | Fix bugs; apostrophe test on every text field | Finish README; fresh-clone check; topic queries | AI-use appendix; final proofread |

## Weeks 11–15: Presentation

| Week | Everyone | M1 | M10 |
|---|---|---|---|
| 11–12 | Rehearse the 15-minute presentation; role-swap Q&A so each member can explain the whole design; prepare 1 to 2 questions for other groups | Schedule rehearsals | Final dry-run demo on a fresh clone |
| 13–15 | **Present and demo (D4)**; answer individual questions; ask questions at other groups' sessions (30% of the project mark) | – | – |

## Notes

- A dash means no assigned task that week. Members with dashes (for example M6 to M9 in weeks 4 to 5, or M2 to M4 in week 8) are free to help others, such as review the ER diagram or help test.
- The week numbers assume one lecture per week. The assignment fixes only the deadlines: registration by the end of week 2, D1 in week 3, D2 in week 5, D3, D5 and D6 in week 10, and D4 in weeks 13 to 15. Everything else is my suggested pacing.

# specific descriptions
# Task List per Member

Each list follows the roadmap phases and week numbers. M1 to M10 are placeholders for real names, and the splits between paired roles (M2/M3, M6/M7, M8/M9) are my suggestions, so the group can swap them.

## M1: Group leader
- **Weeks 1–2:** Register the group and topic on the Google Sheet (names, student IDs, phone numbers, topic). Create the GitHub repo from the skeleton. Set up the weekly work-allocation sheet.
- **Week 3:** Write D1 (1–3 pages) arguing for the topic's Objective sentence. Confirm the topic.
- **Weeks 3–10:** Run a short weekly check-in and update the work-allocation sheet every week. Set internal deadlines 3 to 4 days before each official one. Attach the group information to every deliverable.
- **Week 10:** Write the Introduction and Discussion sections of the report. Collect every member's signature for the work allocation. Submit D3, D5 and D6.
- **Weeks 11–12:** Schedule rehearsals and role-swap Q&A sessions.

## M2: Data modeller (entities)
- **Weeks 1–3:** Lead the business-rules writing. Draft the entity sets and their attributes.
- **Weeks 3–5:** Model the weak entity, the multivalued attribute (phone numbers) and the ISA hierarchy if the group keeps it. Write the paragraph explaining every non-obvious design decision. Contribute to D2.
- **Weeks 5–7:** Review the schema script against the ER diagram with M5.
- **Week 9:** Write report sections 2 (Business rules) and 3 (ER diagram) with M3.

## M3: Data modeller (relationships and mapping)
- **Weeks 1–3:** Draft the relationships with cardinality and participation on every one. Add the self-relationship with role names on both edges.
- **Weeks 3–5:** Map the diagram to the relational schema, noting which mapping rule produced each table. Contribute to D2.
- **Weeks 5–7:** Update the diagram and schema when normalization changes anything.
- **Week 9:** Write report section 4 (Relational schema) and help with sections 2 and 3.

## M4: Normalization lead
- **Weeks 4–5:** Start the functional-dependency list from the D2 schema.
- **Weeks 5–7:** Derive the minimal cover and every candidate key with justification. Determine the normal form of each relation. Decompose where needed and prove each decomposition is lossless. If any relation stays in 3NF rather than BCNF, state which dependency would become unenforceable and why.
- **Week 7:** Hand the final schema to M5 before the schema freeze.
- **Week 9:** Write report sections 5 (Functional dependencies) and 6 (Normalization).

## M5: Database developer
- **Weeks 5–7:** Write the CREATE TABLE script in foreign-key order with PK, FK (ON DELETE and ON UPDATE), NOT NULL, UNIQUE, CHECK and DEFAULT. Make it drop and recreate the database so it runs twice in a row.
- **Weeks 6–8:** Write at least 15 realistic rows of sample data per table, coordinating with M6 and M7 so every query returns rows.
- **Week 8:** Create at least one index and note which query it helps. Implement the rules that need triggers, if any. Check that the chosen system actually enforces CHECK.
- **Weeks 8–10:** Support the Java developers on schema questions and stored SQL.
- **Week 9:** Write report section 7 (Database implementation).

## M6: Query developer (queries 1–6, relational algebra)
- **Weeks 6–8:** Write SQL for queries 1 to 6, each preceded by its English question. Include the multi-table join, the outer join and the GROUP BY with HAVING.
- **Weeks 8–9:** Write four queries in relational algebra.
- **Weeks 7–8:** Help M5 with sample data so the results are non-empty.
- **Weeks 9–10:** Help M8 and M9 wire the report screen to its queries.
- **Week 9:** Write half of report section 8 (Queries), with each query's result on the sample data.

## M7: Query developer (queries 7–12, updates and transaction)
- **Weeks 6–8:** Write SQL for queries 7 to 12, including the nested query with IN or EXISTS, the NOT EXISTS ("for all") query, the set operation and the view.
- **Weeks 8–9:** Write the INSERT, UPDATE and DELETE statements for each main table, plus the two-table operation with COMMIT and ROLLBACK.
- **Weeks 7–8:** Help M5 with sample data.
- **Weeks 9–10:** Help M8 and M9 wire the search and topic-query screens.
- **Week 9:** Write the other half of report section 8, and the transaction part of section 7 with M5.

## M8: Java developer (foundation, browse, search, editing)
- **Weeks 6–7:** Build the connection layer that reads settings from an external config file, with try-with-resources everywhere. Agree a shared error-message utility with M9.
- **Weeks 7–8:** Build the browse form (grid with row count) and the search form using PreparedStatement parameters.
- **Weeks 8–9:** Build the editing form (insert, update, delete with confirmation). Validate input (required fields, numeric ranges, date order) and turn constraint violations into readable messages.
- **Week 9:** Write the first half of report section 9 (screenshots and the SQL behind each screen).
- **Week 10:** Fix bugs from M10's testing. Run the apostrophe test on every text field as a final check.
  
## M9: Java developer (master-detail, report, topic queries)
- **Weeks 6–7:** Set up the project build, the shared UI layout and the README skeleton with M8.
- **Weeks 8–9:** Build the master-detail form (for example, a booking and its payment) and the report screen using an aggregate query.
- **Week 9:** Write the second half of report section 9.
- **Weeks 9–10:** Make at least 4 of the topic's 5 queries reachable from the interface, working with M6 and M7.
- **Week 10:** Finish the README (JDK version, JDBC driver, connection settings, build and run steps) and check that a fresh clone builds and runs.

## M10: Tester and writer
- **Weeks 3–5:** Compile D2 from M2, M3 and M4's contributions.
- **Weeks 6–8:** Run the constraint tests once the script exists: duplicate key, missing foreign key, CHECK violation. Report any accepted violation to M5 immediately.
- **Weeks 8–10:** Test every screen against the requirements: live insert, update and delete visible in the database, plain-language errors, and no data held in arrays or text files.
- **Week 9:** Assemble the full report draft (25+ pages in the required 12-section order), harmonise the writing, and write section 11 (Work allocation) and section 12 (References).
- **Week 10:** Write the AI-use appendix with every member's input. Final proofread.
- **Weeks 11–12:** Run the final dry-run demo on a fresh clone.

## Tasks for everyone
- **Weeks 1–3:** Take part in the ER modelling discussion. The assignment requires every member to attend.
- **Week 4 onward:** Log your work in the work-allocation sheet weekly.
- **Weeks 11–12:** Practise explaining the whole design, not just your part, since each member is questioned individually.
- **Weeks 13–15:** Prepare and ask 1 to 2 questions for other groups' presentations (30% of the project mark).


