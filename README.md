\# Equipment Booking System



\## 1. Project Overview



The Equipment Booking System is a client-server application designed to manage institutional equipment and its borrowing activities.



The system allows authorized users to authenticate, view equipment, manage equipment records according to their roles, and record equipment borrowing and returning activities.



The system is being developed using Java, JavaFX, Java RMI, JDBC, and PostgreSQL.



\---



\## 2. Product Goal



Develop a secure client-server equipment booking system that allows authorized users to manage, view, borrow, and return equipment while maintaining accurate records in a PostgreSQL database.



\---



\## 3. Main Objectives



The project aims to:



\* Apply object-oriented programming principles in Java.

\* Practice Generics and Java Collections.

\* Retrieve database records and represent them as Java objects.

\* Process database data using typed collections such as `List<Equipment>`.

\* Implement CRUD operations using JDBC.

\* Implement client-server communication using Java RMI.

\* Implement authentication and role-based access control.

\* Apply software engineering practices throughout development.

\* Use Git and GitHub for version control.

\* Document the development process.



\---



\## 4. Technologies



\* Java

\* JavaFX

\* Java RMI

\* JDBC

\* PostgreSQL

\* Git

\* GitHub

\* Apache NetBeans



\---



\## 5. System Architecture



The system will follow a client-server architecture consisting of:



\* Client application

\* Server application

\* Shared models and RMI interfaces

\* Database access layer

\* PostgreSQL database



The general data flow is:



```text

JavaFX Client

&#x20;     ↓

RMI Service

&#x20;     ↓

Server

&#x20;     ↓

DAO / Database Layer

&#x20;     ↓

JDBC

&#x20;     ↓

PostgreSQL

```



Database records will be converted into Java objects and returned to the application using typed collections such as:



```java

List<Equipment>

```



\---



\## 6. User Roles



The initial roles are:



\### Administrator



Has the highest level of access and can manage users, equipment, and bookings.



\### Staff



Has intermediate privileges and can perform authorized equipment and booking operations.



\### Student



Has limited privileges focused primarily on viewing available equipment and managing their own bookings.



\---



\## 7. Development Methodology



The project will use a combination of Agile/Scrum and Extreme Programming practices.



Development will be organized into small iterations based on user stories.



Each feature will generally follow this process:



```text

Requirement

&#x20;   ↓

User Story

&#x20;   ↓

Design

&#x20;   ↓

Implementation

&#x20;   ↓

Testing

&#x20;   ↓

Documentation

&#x20;   ↓

Git Commit

```



\---



\## 8. Project Documentation



Additional documentation is available in the `docs` directory.



\* `requirements.md` — Functional and non-functional requirements

\* `user-stories.md` — User stories and acceptance criteria

\* `architecture.md` — System architecture

\* `database-design.md` — Database design and relationships

\* `development-log.md` — Development progress and learning record

\* `testing.md` — Testing strategy and test cases



\---



\## 9. Project Status



The project is currently in the initial planning and architecture stage.



The existing project structure and Git repository have been established. Development of the improved system will proceed incrementally.



\---



\## 10. Future Improvements



Potential future improvements include:



\* Advanced equipment searching and filtering

\* Equipment availability tracking

\* Booking history

\* Notifications

\* Reporting

\* Improved authentication security

\* Audit logging

\* Additional user roles

\* Deployment to a production environment



