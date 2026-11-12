
# PDP1 - Task Management Application

**Course:** Software Engineering 2  
**Student:** Dmytro Kostariev  
**Student ID:** 11848552  
**Project:** Programming and Design Project 1 (PDP1)

## 1. Project Overview

This project contains the implementation and design of a Task Management Application developed for Software Engineering 2.

The submission consists of two tasks:

- **Task 1 - Implementation:** Java application for creating, updating, searching, and displaying tasks.
- **Task 2 - Design:** Three UML class diagrams illustrating the development of the application from single-user task management to collaborative team management.

## 2. Task 1 - Implementation

### Implemented Features

The application supports:

- Creating tasks with a name, description, due date, and completion status.
- Assigning unique UUID identifiers to tasks.
- Updating existing tasks using their UUID.
- Searching tasks by name or description (case-insensitive).
- Displaying all stored tasks.
- Validating user input and rejecting invalid values.
- Handling exceptions without terminating the application unexpectedly.
- Logging operations and errors using `java.util.logging`.

Tasks are stored in a `LinkedHashMap<UUID, Task>` to provide efficient lookup and preserve insertion order.

### Java Classes

- **`Main`** - Provides the interactive console interface.
- **`Task`** - Represents an individual task and its attributes.
- **`TaskManager`** - Manages task creation, retrieval, updates, searching, and display.
- **`TaskValidator`** - Validates task names, descriptions, and due dates.

### Requirements

- Java 21
- Gradle 8.8 (Gradle Wrapper included)
- JUnit 5

### Build and Run

Open a terminal in the `PDP 1 Submission - to be graded` directory.

**Build the project:**

```bash
./gradlew clean build
```

**Run the application:**

```bash
./gradlew --console=plain run
```

The application provides the following console menu:

1. Add task
2. Update task
3. Search tasks
4. Show all tasks
5. Exit

Dates must be entered in the format `YYYY-MM-DD`.

**Note:** Tasks are stored in memory and are not persisted after the application exits.

### Unit Testing

JUnit 5 is used to verify the correctness of the application.

The test suite contains 73 test executions:

| Test Class | Tests |
|---|---:|
| `TaskManagerTest` | 29 |
| `TaskTest` | 26 |
| `TaskValidatorTest` | 18 |
| **Total** | **73** |

The tests cover:

- Task creation and retrieval.
- Task updates and data consistency.
- Searching and collection behavior.
- Input validation and exception handling.
- Invalid values and boundary cases.

**Run all tests:**

```bash
./gradlew clean test
```

**JUnit HTML report:**

`build/reports/tests/test/index.html`

All 73 tests passed in the latest verified execution.

## 3. Task 2 - UML Design

The application architecture is designed in three stages using PlantUML.

### Stage 1 - Single-User Task Management

The first stage introduces the basic task management system.

**Classes:**

- `Task`
- `TaskManager`
- `User`
- `Category`
- `TaskValidator`

The design supports one user and the assignment of multiple categories to tasks.

### Stage 2 - Project-Based Task Management

The second stage extends the architecture with the `Project` class.

A user can own multiple tasks and projects. Each task may belong to one project or exist independently without a project association.

### Stage 3 - Collaborative Task Management

The third stage introduces multiple users, teams, and collaborative task management.

The architecture separates responsibilities into four managers:

- `TaskManager`
- `ProjectManager`
- `UserManager`
- `TeamManager`

The `ResourceOwner` interface provides a common abstraction for users and teams that own tasks and projects.

The design supports both personal and team resources.


### UML Diagrams

All UML diagrams are located in the `Design_Documentation/UML_Diagrams/` directory.

Each stage is provided in two formats:
- `.puml` - Editable PlantUML source code.
- `.svg` - Generated UML diagram for viewing.

| Stage | PlantUML | SVG |
|---|---|---|
| Stage 1 | [Stage1.puml](Design_Documentation/UML_Diagrams/Stage1.puml) | [Stage1.svg](Design_Documentation/UML_Diagrams/Stage1.svg) |
| Stage 2 | [Stage2.puml](Design_Documentation/UML_Diagrams/Stage2.puml) | [Stage2.svg](Design_Documentation/UML_Diagrams/Stage2.svg) |
| Stage 3 | [Stage3.puml](Design_Documentation/UML_Diagrams/Stage3.puml) | [Stage3.svg](Design_Documentation/UML_Diagrams/Stage3.svg) |


**Note:** The UML diagrams represent the proposed architecture. The Java implementation covers the core task management functionality from Task 1. User, category, project, and team management are design extensions and are not implemented in Java.

## 4. Design Principles

The application design considers the following principles:

- **Encapsulation** - Protecting object attributes through controlled access.
- **Single Responsibility Principle (SRP)** - Separating responsibilities between classes.
- **Open/Closed Principle (OCP)** - Supporting extensions through abstractions.
- **Separation of Concerns** - Separating application logic, validation, and user interaction.
- **High Cohesion and Low Coupling** - Keeping related functionality together while minimizing dependencies.
- **Defensive Programming** - Validating input and handling errors appropriately.


A detailed explanation is provided in [design-principles.md](Design_Documentation/design-principles.md).


## 5. Project Structure

- `src/main/java/pdp1/` - Java application source code.
- `src/test/java/pdp1/` - JUnit 5 tests.
- `Design_Documentation/UML_Diagrams/` - PlantUML source files and SVG diagrams.
- `Design_Documentation/design-principles.md` - Explanation of design decisions.
- `build.gradle` - Gradle build configuration.
- `settings.gradle` - Gradle project settings.
- `gradlew` and `gradle/wrapper/` - Gradle Wrapper.

## 6. Submission

The project is submitted via the university GitLab repository in the predefined directory `PDP 1 Submission - to be graded`.

The submission contains one Gradle Java project, its source code, unit tests, UML diagrams, and documentation.
