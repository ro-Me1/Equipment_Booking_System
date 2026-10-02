\# Business Logic



\## 1. Purpose



Explain the purpose of the business logic layer in this project.



\* What is business logic?

\* Why is business logic separated from the user interface and database?

\* What role does Domain-Driven Design (DDD) play in this project?

\* What role does Test-Driven Development (TDD) play in developing the domain logic?



\---



\## 2. Business Logic Development Approach



\### 2.1 Domain-Driven Design



Explain how the domain model is identified and designed before implementing services.



\*\*Development flow:\*\*



```text

Requirements

&#x20;   ↓

Identify Domain Concepts

&#x20;   ↓

Define Domain Classes

&#x20;   ↓

Define Business Rules

&#x20;   ↓

Implement Domain Behavior

&#x20;   ↓

Test Domain Behavior

&#x20;   ↓

Create Application/Service Layer

```



\### 2.2 Test-Driven Development



Explain how TDD is used to verify the business rules.



\*\*Development cycle:\*\*



```text

RED → GREEN → REFACTOR

```



Briefly explain what each stage means and how it is applied in this project.



\---



\# 3. Domain Model



Provide an overview of the domain classes in the system.



| Domain Class   | Responsibility                                   |

| -------------- | ------------------------------------------------ |

| `User`         | Represents a system user                         |

| `Equipment`    | Represents equipment that can be booked/borrowed |

| `Booking`      | Represents an equipment reservation              |

| `BorrowRecord` | Represents an actual borrowing transaction       |



Add or remove classes as the project evolves.



\---



\# 4. Domain Classes



This section documents the business logic of each domain class.



\---



\## 4.1 User



\### Purpose



A person who can sign in: Administrator, Staff or Student. It answers "who is this and what may they do?"



\### Responsibility



This class is responsible for: identity, role, active flag and answering hasPermission(...).

However the class isn't responsible for hashing or veryfying passwords(auth service), deciding who may grant permissions(service, needs MANAGE\_USERS),



\### State / Attributes



List the important attributes and explain what each represents.



| Attribute  | Description 

| ---------- | ----------- 

| `userId`   |Unique, immutable identity 

| `username` |Profile data          

| `fullName` |Profile data         

| `role`     |Exactly one of Administrator/Staff/Student           



`isActive` -> Whether the account may act at all

`passwordHash` -> Server-only; never sent to the client.

`grantedpermission` -> Extras the admin gave beyond the role.

`revokedPermission` -> Defaults an admin took away

\### Business Rules



Document the rules that apply to a `User`.



1. An inactive user has no permissions.

2\. Effective permissions = role.defaultPermissions() + granted - revoked.

3\. Changing the role never discards grants or revocations.

4\. Every user has exactly one role.

5\. ADMINISTRATOR defaults include every permission.



\### Domain Behavior



Describe the operations that the object can perform.



getFullName(), effectivePermissions(), hasPermission(p), grant(p), revoke(p), changeRole(r), activate(), deactivate().



\### State Changes



Explain how the object's state can change.



```text

&#x20;  Active

&#x20;    ↓

&#x20;    ↓

&#x20;  Inactive

```

&#x20;Role can change to any role.

&#x20;grant(p): remove from revoked, add granted. revoke(p): remove from granted, add to revoked.



\### Validation / Restrictions



Document actions or states that should not be allowed.



* Null role or null permission; blank names, malformed emails.
* An inactive user performing any action
* Sending passwordHash to a client
* Service-level: a user changing their own roles or permissions, deactivating the last active admin, a grant with someone without MANAGE\_USERS.

\*

\*



\### Java Concepts Used



Document Java concepts that were important when implementing this class.

* EnumSet<Permission>: a set specialised for enums, stored internally as a bit mask. Very fast, memory-light, and iterates in declaration order.



&#x09;EnumSet<Permission> p = EnumSet.of(Permission.BOOK\_EQUIPMENT);

&#x20;       p.add(Permission.APPROVE\_BORROW);

&#x20;       p.contains(Permission.MANAGE\_USERS);   // false



* Enum with behaviour: Role.defaultPermissions() uses a switch over the enum, so role defaults live in one place.
* Serializable + serialVersionUID: needed to travel over RMI. EnumSet is serializable.
* Defensive copy: return EnumSet.copyOf(set) so callers cannot mutate internal state.
* Getter naming: a field named isActive produces isIsActive(). Name the field active so the getter is isActive().



\### Testing the Business Rules



Describe the tests used to verify the domain logic.

* inactiveUser\_hasNoPermissions
* student\_canBook\_cannotApprove; staff\_canApprove; admin\_hasAllPermissions
* grant\_givesPermissionOutsideRole
* revoke\_removesDefaultPermission
* changeRole\_keepsGrantsAndRevokes
* reactivatedUser\_regainsPermissions
* getFullName\_joinsNames
* nullRole\_rejected
* effectivePermissions\_returnsCopy (mutating the result does not change the user)
* 

\### Design Decisions



Explain important decisions made while designing this class.



* Why defaults moved into Role: one source of truth, and no switch hidden in a setter.
* Why separate granted/revoked sets: the original setRole wiped the whole set, destroying admin grants. Keeping deltas gives flexible access that survives role changes.
* Avoided: a single flat permission set (overwritten on role change), and logic inside setRole (setters should not have side effects).
* Alternatives considered: roles only (rigid, cannot express exceptions); permissions only with no roles (tedious to administer); a DB role-permission table (more flexible, but heavier than needed now).
* 

\---



\## 4.2 Equipment



\### Purpose



\### Responsibility



\### State / Attributes



| Attribute     | Description |

| ------------- | ----------- |

| `equipmentId` |             |

| `name`        |             |

| `category`    |             |

| `status`      |             |

| `description` |             |



\### Business Rules



1\.

2\.

3\.



\### Domain Behavior



| Behavior | Description |

| -------- | ----------- |

|          |             |

|          |             |



\### State Changes



```text

AVAILABLE

&#x20;   ↓

BORROWED

&#x20;   ↓

AVAILABLE

```



Document any other valid state transitions.



\### Validation / Restrictions



\*

\*

\*



\### Java Concepts Used



\#### Concept: `enum`



\*\*What it does:\*\*



\*\*Why it was used:\*\*



```java

// Small example

```



\#### Concept: `EnumSet`



\*\*What it does:\*\*



\*\*Why it was used:\*\*



```java

// Small example

```



\### Testing the Business Rules



| Test | Business Rule Tested | Expected Result |

| ---- | -------------------- | --------------- |

|      |                      |                 |



\### Design Decisions



\*

\*

\*



\---



\## 4.3 Booking



\### Purpose



\### Responsibility



\### State / Attributes



| Attribute | Description |

| --------- | ----------- |

|           |             |

|           |             |

|           |             |



\### Business Rules



1\.

2\.

3\.



\### Domain Behavior



| Behavior | Description |

| -------- | ----------- |

|          |             |

|          |             |



\### State Changes



```text

Initial State

&#x20;    ↓

&#x20;    ↓

Next State

```



\### Validation / Restrictions



\*

\*

\*



\### Java Concepts Used



\#### Concept: `\_\_\_\_\_\_\_\_`



\*\*What it does:\*\*



\*\*Why it was used:\*\*



```java

// Small example

```



\### Testing the Business Rules



| Test | Business Rule Tested | Expected Result |

| ---- | -------------------- | --------------- |

|      |                      |                 |



\### Design Decisions



\*

\*

\*



\---



\## 4.4 BorrowRecord



\### Purpose



Explain that `BorrowRecord` represents one actual borrowing transaction between a user and an equipment item.



\### Responsibility



Explain what information and behavior belong to a borrowing transaction.



\### State / Attributes



| Attribute    | Description |

| ------------ | ----------- |

| `borrowId`   |             |

| `user`       |             |

| `equipment`  |             |

| `borrowedAt` |             |

| `returnedAt` |             |



\### Business Rules



1\. A borrowing record must reference a user.

2\. A borrowing record must reference equipment.

3\. A borrowing record must have a borrowing time.

4\. A new borrowing record represents an active borrowing.

5\. An active borrowing has not yet been returned.

6\. A returned borrowing records the return time.

7\. An already returned borrowing cannot be returned again.



Add or modify these rules according to the actual requirements of the project.



\### Domain Behavior



| Behavior           | Description |

| ------------------ | ----------- |

| `isReturned()`     |             |

| `markAsReturned()` |             |

|                    |             |



\### State Changes



```text

ACTIVE BORROWING

&#x20;      │

&#x20;      │ return equipment

&#x20;      ↓

RETURNED

```



Explain the conditions required for each state transition.



\### Validation / Restrictions



\*

\*

\*



\### Java Concepts Used



\#### Concept: `LocalDateTime`



\*\*What it does:\*\*



\*\*Why it was used:\*\*



```java

// Small relevant example

```



\#### Concept: `Optional` / `null`



\*\*What it does:\*\*



\*\*Why it was used:\*\*



```java

// Small relevant example

```



Add other Java concepts as they appear during implementation.



\### Testing the Business Rules



| Test | Business Rule Tested | Expected Result |

| ---- | -------------------- | --------------- |

|      |                      |                 |

|      |                      |                 |

|      |                      |                 |



\### Design Decisions



\*

\*

\*



\---



\# 5. Domain Rules Summary



Summarize the important business rules across the entire domain.



| Domain    | Rule |

| --------- | ---- |

| User      |      |

| Equipment |      |

| Booking   |      |

| Borrowing |      |



This section should provide a quick reference without requiring someone to read every class.



\---



\# 6. Domain Relationships



Explain how the domain objects interact.



```text

User

&#x20;│

&#x20;├────────── Booking ────────── Equipment

&#x20;│

&#x20;└────────── BorrowRecord ───── Equipment

```



Explain the relationships in plain language.



\### User → Booking



Describe the relationship.



\### Booking → Equipment



Describe the relationship.



\### User → BorrowRecord



Describe the relationship.



\### BorrowRecord → Equipment



Describe the relationship.



\---



\# 7. Java Concepts Learned



Use this section as a personal reference for Java concepts encountered while implementing the domain.



| Java Concept       | Where Used | Why Used |

| ------------------ | ---------- | -------- |

| `enum`             |            |          |

| `EnumSet`          |            |          |

| `ArrayList`        |            |          |

| Generics           |            |          |

| `LocalDateTime`    |            |          |

| `Optional`         |            |          |

| Interfaces         |            |          |

| Exception handling |            |          |



Only add concepts that you actually encounter.



\---



\# 8. TDD Test Summary



Summarize the tests created for the domain logic.



| Domain Class   | Test | Rule Verified | Result |

| -------------- | ---- | ------------- | ------ |

| `User`         |      |               |        |

| `Equipment`    |      |               |        |

| `Booking`      |      |               |        |

| `BorrowRecord` |      |               |        |



\---



\# 9. Lessons Learned



Document what you learned while implementing the business logic.



\### Domain-Driven Design



\*

\*

\*



\### Test-Driven Development



\*

\*

\*



\### Object-Oriented Design



\*

\*

\*



\### Java



\*

\*

\*



\---



\# 10. Design Decisions and Future Improvements



Record decisions that may be useful when developing future versions or other projects.



\### Decision 1



\*\*Problem:\*\*



\*\*Decision:\*\*



\*\*Reason:\*\*



\*\*Alternative considered:\*\*



\### Decision 2



\*\*Problem:\*\*



\*\*Decision:\*\*



\*\*Reason:\*\*



\*\*Alternative considered:\*\*



\---



\# 11. References for Future Projects



Record reusable principles discovered during this project.



For example:



\* How to identify domain objects

\* How to identify business rules

\* When to put behavior inside a domain class

\* When to create a service

\* How to use TDD for business rules

\* When to use `enum`

\* When to use `EnumSet`

\* How to handle object state transitions

\* How to keep business logic independent of the database



