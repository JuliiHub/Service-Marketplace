# Week-by-Week Task Table

This is organised by week and member, so each row shows what everyone is doing at that time. M1 to M10 are placeholders for real names. Weeks 1 to 3 are planning, weeks 4 to 5 are the ER and schema draft, weeks 6 to 10 are build and report, and weeks 11 to 15 are presentation.

## Weeks 1–5: Requirements, ER diagram, D1 and D2

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
