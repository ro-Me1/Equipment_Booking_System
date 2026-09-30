\# User Stories



\## Authentication



\### US-01 — User Login



\*\*As a\*\* registered user,



\*\*I want to\*\* log into the system using my username and password,



\*\*so that\*\* I can access functionality available to my role.



\### Acceptance Criteria



\* The user can enter a username.

\* The user can enter a password.

\* Valid credentials allow access.

\* Invalid credentials are rejected.

\* The user's role is identified after successful authentication.



\---



\## Equipment Management



\### US-02 — View Equipment



\*\*As a\*\* user,



\*\*I want to\*\* view equipment records,



\*\*so that\*\* I can know what equipment is available.



\### Acceptance Criteria



\* Equipment records are retrieved from the server.

\* Equipment information is displayed to the user.

\* Equipment status is visible.



\---



\### US-03 — Add Equipment



\*\*As an\*\* authorized user,



\*\*I want to\*\* add equipment,



\*\*so that\*\* the equipment inventory remains accurate.



\### Acceptance Criteria



\* The user can enter equipment information.

\* Required information is validated.

\* The equipment is stored in the database.

\* The new equipment can subsequently be retrieved.



\---



\### US-04 — Update Equipment



\*\*As an\*\* authorized user,



\*\*I want to\*\* update equipment information,



\*\*so that\*\* inaccurate information can be corrected.



\---



\### US-05 — Delete Equipment



\*\*As an\*\* authorized user,



\*\*I want to\*\* delete equipment records,



\*\*so that\*\* obsolete equipment can be removed from the system.



\---



\## Equipment Search



\### US-06 — Search Equipment



\*\*As a\*\* user,



\*\*I want to\*\* search or filter equipment,



\*\*so that\*\* I can quickly find equipment matching my needs.



\### Acceptance Criteria



\* The user can provide a search criterion.

\* The application processes the equipment collection.

\* Matching equipment is displayed.

\* Non-matching equipment is excluded from the displayed results.



\---



\## Borrowing



\### US-07 — Borrow Equipment



\*\*As a\*\* student,



\*\*I want to\*\* borrow available equipment,



\*\*so that\*\* I can use the equipment for my work.



\### Acceptance Criteria



\* The equipment must be available.

\* The user must have permission to borrow it.

\* A booking record is created.

\* The equipment status changes appropriately.



\---



\### US-08 — Return Equipment



\*\*As a\*\* borrower,



\*\*I want to\*\* return equipment,



\*\*so that\*\* the equipment becomes available for future use.



\---



\## Role-Based Access



\### US-09 — Restrict Access



\*\*As an\*\* administrator,



\*\*I want to\*\* assign roles to users,



\*\*so that\*\* users only have access to operations appropriate to their responsibilities.



\### Acceptance Criteria



\* Users have a defined role.

\* The system identifies the user's role after login.

\* Protected operations check authorization.

\* Unauthorized operations are rejected.



\---



\# Product Backlog



| ID    | User Story        | Priority | Status  |

| ----- | ----------------- | -------- | ------- |

| US-01 | User Login        | High     | Planned |

| US-02 | View Equipment    | High     | Planned |

| US-03 | Add Equipment     | High     | Planned |

| US-04 | Update Equipment  | High     | Planned |

| US-05 | Delete Equipment  | High     | Planned |

| US-06 | Search Equipment  | Medium   | Planned |

| US-07 | Borrow Equipment  | High     | Planned |

| US-08 | Return Equipment  | High     | Planned |

| US-09 | Role-Based Access | High     | Planned |



