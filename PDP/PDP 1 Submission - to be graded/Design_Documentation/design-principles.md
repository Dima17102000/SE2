# PDP1 - Design Principles

## 1. Introduction

The Task Management Application is designed in three stages, gradually extending its functionality from basic single-user task management to project organization and collaborative team management.

The design focuses on the following software engineering principles:

- Encapsulation
- Single Responsibility Principle (SRP)
- Open/Closed Principle (OCP)
- Separation of Concerns (SoC)
- High Cohesion
- Low Coupling
- Defensive Programming
- Extensibility and Maintainability

The UML diagrams describe the proposed architecture, while the Java implementation covers the core task management functionality from Task 1.

## 2. Stage 1 - Single-User Task Management

Stage 1 introduces the fundamental classes of the application:

- `Task`
- `TaskManager`
- `User`
- `Category`
- `TaskValidator`

### 2.1. Encapsulation

The `Task` class encapsulates its attributes by declaring them as private:

- `id`
- `validator`
- `name`
- `description`
- `dueDate`
- `completed`

Task data is accessed through public getters and updated using appropriate setters. The validator is a private final dependency and is not exposed through a setter.

The task identifier is declared as `final` and cannot be changed after construction.

This protects the identity of a task and prevents uncontrolled modification of its internal state.

### 2.2. Single Responsibility Principle (SRP)

Each class has a specific responsibility:

| Class | Responsibility |
|---|---|
| `Main` | Handles console input, invokes validation, and coordinates task operations. |
| `Task` | Represents an individual task and its attributes. |
| `TaskManager` | Manages task creation, retrieval, updates, searching, and display. |
| `TaskValidator` | Validates task names, descriptions, due dates, and search queries. |
| `User` | Represents the application user. |
| `Category` | Represents task categories. |

The separation between `Task`, `TaskManager`, and `TaskValidator` reduces duplicated logic and improves maintainability.

### 2.3. Separation of Concerns

The Java implementation separates the application into packages:

- `pdp1.app` - Console interface and user interaction.
- `pdp1.model` - Task representation.
- `pdp1.service` - Task management operations.
- `pdp1.validation` - Input validation.

This separation makes the application easier to understand, test, and maintain.

Changes to the console interface do not necessarily require changes to the task model.

#### 2.3.1. Dependency Injection

The Java implementation uses constructor-based dependency injection for validation. `Main` creates one `TaskValidator` instance and passes it to `TaskManager` and newly created `Task` objects.

Both `Task` and `TaskManager` store the validator in a private final instance field. Validation is performed through instance methods rather than static utility calls.

An alternative validator can be introduced by subclassing `TaskValidator` and passing that implementation through the constructors. This reduces the need to modify task management classes when validation rules change.

The parameterless constructors in `Task` and `TaskManager` remain available for compatibility and create the default validator. Passing a validator explicitly enables the application to use the selected implementation consistently.

### 2.4. Defensive Programming

The application validates input before accepting or modifying task data.

Examples include:

- Rejecting null or blank task names.
- Rejecting null descriptions.
- Rejecting null due dates.
- Rejecting null or blank search queries.
- Rejecting invalid task identifiers.
- Preventing duplicate task registration.
- Handling attempts to retrieve nonexistent tasks.

Task names, descriptions, due dates, and search queries are validated immediately after being read from the console. The model and service layers also validate relevant values, so direct method calls cannot bypass the core checks.

Invalid input results in explicit exceptions.

The `TaskManager` validates all new values before updating an existing task, preventing partial updates when validation fails.

### 2.5. Task Categorization

The Stage 1 UML diagram models a many-to-many relationship between `Task` and `Category`.

Each task belongs to at least one category, while a category can be associated with multiple tasks.

This relationship allows tasks to be organized without introducing separate task subclasses for different categories.

Category management is part of the UML design and is not implemented in Task 1.

## 3. Stage 2 - Project-Based Task Management

Stage 2 extends Stage 1 by introducing the `Project` class.

A user can own multiple tasks and projects.

### 3.1. Separation of Responsibilities

The `Project` class represents project information, while `Task` continues to represent individual tasks.

This separation avoids mixing project-specific information with task-specific attributes.

The Stage 2 design also extends the management functionality with operations for project creation, retrieval, and task assignment.

### 3.2. Optional Project Association

The relationship between `Project` and `Task` is modeled with the following multiplicities:

- One project can contain zero or more tasks (`0..*`).
- One task can belong to zero or one project (`0..1`).

Therefore, tasks can exist independently of projects.

This satisfies the requirement that the user can manage standalone tasks as well as project-associated tasks.

### 3.3. Extensibility

The architecture extends the existing task management design without requiring a new type of task.

Tasks can be assigned to projects or removed from projects while retaining their identity.

The additional project management methods are proposed extensions and are not part of the current Java implementation.

## 4. Stage 3 - Collaborative Task Management

Stage 3 extends the application to support multiple users and teams.

The design introduces the following additional components:

- `Team`
- `ResourceOwner`
- `ProjectManager`
- `UserManager`
- `TeamManager`

### 4.1. Single Responsibility Principle (SRP)

Instead of assigning all responsibilities to one large manager, Stage 3 introduces four specialized managers:

| Manager | Responsibility |
|---|---|
| `TaskManager` | Manages tasks. |
| `ProjectManager` | Manages projects and task assignments. |
| `UserManager` | Manages users. |
| `TeamManager` | Manages teams and memberships. |

Each manager focuses on a particular domain.

This improves cohesion and reduces the complexity of individual classes.

### 4.2. Open/Closed Principle (OCP)

The `ResourceOwner` interface introduces a common abstraction for entities that can own tasks and projects.

Both `User` and `Team` implement this interface.

Tasks and projects are associated with `ResourceOwner` instead of having separate ownership relationships for every concrete owner type.

This makes it possible to extend the ownership model with additional implementations without changing the basic UML ownership relationship.

Additional business rules may still require changes when new owner types are introduced.

### 4.3. Polymorphism

The `ResourceOwner` interface enables ownership to be expressed through a common type.

For example, a task can be associated with either a `User` or a `Team` through the same ownership abstraction.

This avoids introducing separate task classes for personal and team tasks.

### 4.4. High Cohesion

High cohesion means that a class focuses on closely related responsibilities.

In Stage 3:

- `TaskManager` focuses on tasks.
- `ProjectManager` focuses on projects.
- `UserManager` focuses on users.
- `TeamManager` focuses on teams.

This organization improves understandability and simplifies independent testing.

### 4.5. Low Coupling

Low coupling aims to minimize unnecessary dependencies between components.

The Stage 3 design separates major management responsibilities and explicitly represents dependencies between services.

For example:

- `ProjectManager` depends on `TaskManager` to access tasks.
- `TeamManager` depends on `UserManager` to access users.

These dependencies are limited to relevant interactions.

The design does not completely eliminate coupling, but it makes dependencies more explicit and easier to manage.

### 4.6. Ownership Constraints

Each task and project has exactly one `ResourceOwner`.

The owner can be either:

- An individual `User`.
- A `Team`.

This avoids ambiguous resource ownership.

Tasks may exist without belonging to a project.

If a task is assigned to a project, the task and project must have the same owner.

These rules are design constraints and must be enforced by the application when Stage 3 is implemented.

### 4.7. Team Membership

The relationship between `User` and `Team` supports multiple memberships:

- A user can belong to zero or more teams.
- A team must have at least one member.

Users can therefore manage personal tasks and projects without joining a team.

Team resources are intended to be accessible only to authorized team members.

Authorization checks are part of the proposed architecture and are not implemented in Task 1.

## 5. Implementation Quality

### 5.1. Data Structures

The Java implementation uses `LinkedHashMap<UUID, Task>`.

This provides:

- Efficient lookup by task identifier.
- Unique keys for stored tasks.
- Preservation of task insertion order.

The `getAllTasks()` method returns an unmodifiable snapshot of the collection.

However, the contained `Task` objects remain mutable.

### 5.2. Exception Handling

The application handles expected invalid operations using exceptions such as:

- `IllegalArgumentException`
- `NoSuchElementException`
- `DateTimeParseException`

The console interface catches expected exceptions and displays an error message without terminating unexpectedly.

### 5.3. Logging

The application uses `java.util.logging`.

Successful operations such as task creation and updates are logged.

The console application also logs warnings for rejected operations and severe errors for input/output failures.

### 5.4. Input Security

The current application uses a console interface and stores tasks in memory.

It does not execute SQL queries or render user-generated HTML.

Therefore, SQL injection and cross-site scripting are not directly applicable to the current implementation.

If database or web functionality is introduced, parameterized SQL queries and context-appropriate output encoding would be required.

### 5.5. Unit Testing

JUnit 5 is used to verify the implemented functionality.

Tests cover:

- Task creation and initialization.
- Task updates and identity preservation.
- Search behavior.
- Input validation.
- Exception handling.
- Collection behavior.
- Data consistency after invalid operations.
- Boundary cases.

The test suite contains 73 test executions. After the validation refactoring, run `./gradlew clean test` to confirm the current results.

## 6. Design Trade-offs

Some design decisions involve trade-offs.

### In-Memory Storage

The application stores tasks in memory.

This simplifies the implementation and avoids unnecessary database dependencies.

However, tasks are not persisted between application executions.

### Mutable Task Objects

The `Task` class allows controlled modification through setters.

This makes updating tasks straightforward, but references to mutable tasks can also be modified outside `TaskManager`.

A future version could introduce immutable task representations or defensive copying.

### Separate Management Services

Stage 3 introduces specialized managers to improve responsibility separation.

This creates more classes and requires coordination between services, but prevents one manager from handling unrelated functionality.

### UML Design vs. Java Implementation

The UML diagrams show the architectural evolution of the application.

Not all proposed classes and operations are implemented in Java.

Task 1 implements the core task management application, while Task 2 focuses on designing its possible extensions.

## 7. Conclusion

The three UML stages demonstrate the incremental evolution of the Task Management Application.

- **Stage 1** establishes basic task management and separates validation from application logic.
- **Stage 2** introduces project organization while allowing tasks to exist independently.
- **Stage 3** supports collaboration through specialized managers and the `ResourceOwner` abstraction.

The design emphasizes encapsulation, clear responsibilities, constructor-based dependency injection, extensibility, high cohesion, low coupling, and defensive programming.

These principles make the proposed architecture easier to understand, maintain, test, and extend.
