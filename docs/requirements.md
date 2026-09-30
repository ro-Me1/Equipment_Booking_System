\# System Requirements



\## 1. Functional Requirements



\### FR-01 — User Authentication



The system shall allow registered users to log in using valid credentials.



\### FR-02 — Role Identification



The system shall identify the role associated with an authenticated user.



\### FR-03 — User Management



Administrators shall be able to create, update, deactivate, and manage user accounts.



\### FR-04 — Equipment Creation



Authorized users shall be able to add new equipment records.



\### FR-05 — Equipment Retrieval



The system shall allow authorized users to retrieve and view equipment records.



\### FR-06 — Equipment Update



Authorized users shall be able to update equipment information.



\### FR-07 — Equipment Deletion



Authorized users shall be able to delete equipment records where permitted.



\### FR-08 — Equipment Search



Users shall be able to search and filter equipment based on relevant attributes such as name, category, or status.



\### FR-09 — Equipment Availability



The system shall display whether equipment is available, borrowed, or otherwise unavailable.



\### FR-10 — Equipment Borrowing



Authorized users shall be able to borrow available equipment.



\### FR-11 — Equipment Return



Users shall be able to return equipment that they have borrowed.



\### FR-12 — Booking Records



The system shall maintain records of equipment borrowing and returning activities.



\### FR-13 — Role-Based Authorization



The system shall restrict operations according to the authenticated user's role.



\### FR-14 — Database Persistence



The system shall store user, equipment, and booking information in a PostgreSQL database.



\### FR-15 — Data Processing



The system shall convert database records into Java objects and allow the application to process those objects using Java Collections.



\---



\# 2. Non-Functional Requirements



\## NFR-01 — Security



The system shall restrict access to protected operations according to user roles.



\## NFR-02 — Maintainability



The system shall separate the GUI, business/service logic, database access, and shared model components.



\## NFR-03 — Reliability



The system shall handle database and network errors without unexpectedly terminating the application.



\## NFR-04 — Performance



The system shall use appropriate database queries and avoid unnecessary database operations.



\## NFR-05 — Data Integrity



The database shall use appropriate primary keys, foreign keys, constraints, and validation rules.



\## NFR-06 — Usability



The graphical interface shall provide clear controls and feedback to users.



\## NFR-07 — Scalability



The architecture should allow additional users, equipment, roles, and system functionality to be added without requiring a complete redesign.



\## NFR-08 — Version Control



The project shall use Git and GitHub to maintain a history of development changes.



\## NFR-09 — Documentation



Important design decisions, requirements, testing activities, and development progress shall be documented.



