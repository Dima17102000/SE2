package pdp1.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import pdp1.model.Task;

class TaskManagerTest {

    private TaskManager manager;
    private Task task;

    private static final LocalDate DUE_DATE = LocalDate.of(2026, 12, 1);

    @BeforeEach
    void setUp() {
        manager = new TaskManager();
        task = new Task("Study Java", "Prepare for exam", DUE_DATE);
    }

    private static Task createTask(String name, String description) {
        return new Task(name, description, DUE_DATE);
    }

    private static void assertTaskState(
            Task task,
            String name,
            String description,
            LocalDate dueDate,
            boolean completed) {

        assertAll(
                () -> assertEquals(name, task.getName()),
                () -> assertEquals(description, task.getDescription()),
                () -> assertEquals(dueDate, task.getDueDate()),
                () -> assertEquals(completed, task.isCompleted()));
    }

    @Nested
    class AddAndRetrieveTests {

        @Test
        void addingMultipleTasksPreservesInsertionOrder() {
            Task second = createTask("Write tests", "JUnit tests");
            Task third = createTask("Review code", "Code review");

            manager.addTask(task);
            manager.addTask(second);
            manager.addTask(third);

            assertIterableEquals(
                    List.of(task, second, third),
                    manager.getAllTasks());
        }

        @Test
        void addingNullTaskDoesNotChangeManager() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> manager.addTask(null));

            assertTrue(manager.getAllTasks().isEmpty());
        }

        @Test
        void addingDuplicateTaskDoesNotCreateDuplicate() {
            manager.addTask(task);

            assertThrows(
                    IllegalArgumentException.class,
                    () -> manager.addTask(task));

            assertEquals(1, manager.getAllTasks().size());
            assertSame(task, manager.getTaskById(task.getId()));
        }

        @Test
        void retrievingTaskReturnsOriginalInstance() {
            manager.addTask(task);

            Task result = manager.getTaskById(task.getId());

            assertSame(task, result);
            assertEquals(task.getId(), result.getId());
        }

        @Test
        void retrievingUnknownTaskThrowsException() {
            manager.addTask(task);

            assertThrows(
                    NoSuchElementException.class,
                    () -> manager.getTaskById(UUID.randomUUID()));

            assertEquals(1, manager.getAllTasks().size());
        }

        @Test
        void retrievingTaskWithNullIdThrowsException() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> manager.getTaskById(null));
        }

        @Test
        void differentManagersMaintainIndependentTasks() {
            TaskManager anotherManager = new TaskManager();
            Task anotherTask = createTask("Other task", "Other description");

            manager.addTask(task);
            anotherManager.addTask(anotherTask);

            assertIterableEquals(
                    List.of(task), manager.getAllTasks());

            assertIterableEquals(
                    List.of(anotherTask), anotherManager.getAllTasks());

            assertThrows(
                    NoSuchElementException.class,
                    () -> manager.getTaskById(anotherTask.getId()));
        }
    }

    @Nested
    class UpdateTests {

        @Test
        void updatingTaskChangesAllFieldsButPreservesIdentity() {
            Task second = createTask("Second task", "Description");
            manager.addTask(task);
            manager.addTask(second);

            UUID originalId = task.getId();
            LocalDate newDate = LocalDate.of(2027, 1, 15);

            manager.updateTask(
                    originalId,
                    "Updated name",
                    "Updated description",
                    newDate,
                    true);

            assertEquals(originalId, task.getId());

            assertTaskState(
                    task,
                    "Updated name",
                    "Updated description",
                    newDate,
                    true);

            assertSame(task, manager.getTaskById(originalId));

            assertIterableEquals(
                    List.of(task, second),
                    manager.getAllTasks());
        }

        @Test
        void updatingTaskMultipleTimesKeepsLatestValues() {
            manager.addTask(task);

            manager.updateTask(
                    task.getId(),
                    "First update",
                    "First description",
                    LocalDate.of(2027, 2, 1),
                    true);

            manager.updateTask(
                    task.getId(),
                    "Second update",
                    "Second description",
                    LocalDate.of(2027, 3, 1),
                    false);

            assertTaskState(
                    task,
                    "Second update",
                    "Second description",
                    LocalDate.of(2027, 3, 1),
                    false);

            assertEquals(1, manager.getAllTasks().size());
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = { "   ", "\t" })
        void invalidNameDoesNotPartiallyUpdateTask(String invalidName) {
            manager.addTask(task);

            assertThrows(
                    IllegalArgumentException.class,
                    () -> manager.updateTask(
                            task.getId(),
                            invalidName,
                            "Changed description",
                            LocalDate.of(2027, 1, 1),
                            true));

            assertTaskState(
                    task,
                    "Study Java",
                    "Prepare for exam",
                    DUE_DATE,
                    false);
        }

        @Test
        void invalidDescriptionDoesNotPartiallyUpdateTask() {
            manager.addTask(task);

            assertThrows(
                    IllegalArgumentException.class,
                    () -> manager.updateTask(
                            task.getId(),
                            "Changed name",
                            null,
                            LocalDate.of(2027, 1, 1),
                            true));

            assertTaskState(
                    task,
                    "Study Java",
                    "Prepare for exam",
                    DUE_DATE,
                    false);
        }

        @Test
        void nullDueDateDoesNotPartiallyUpdateTask() {
            manager.addTask(task);

            assertThrows(
                    IllegalArgumentException.class,
                    () -> manager.updateTask(
                            task.getId(),
                            "Changed name",
                            "Changed description",
                            null,
                            true));

            assertTaskState(
                    task,
                    "Study Java",
                    "Prepare for exam",
                    DUE_DATE,
                    false);
        }

        @Test
        void updatingNonexistentTaskDoesNotAffectExistingTasks() {
            manager.addTask(task);

            assertThrows(
                    NoSuchElementException.class,
                    () -> manager.updateTask(
                            UUID.randomUUID(),
                            "New task",
                            "New description",
                            LocalDate.of(2027, 1, 1),
                            true));

            assertTaskState(
                    task,
                    "Study Java",
                    "Prepare for exam",
                    DUE_DATE,
                    false);

            assertEquals(1, manager.getAllTasks().size());
        }
    }

    @Nested
    class CollectionTests {

        @Test
        void getAllTasksReturnsUnmodifiableList() {
            manager.addTask(task);

            List<Task> tasks = manager.getAllTasks();

            assertThrows(
                    UnsupportedOperationException.class,
                    () -> tasks.clear());

            assertEquals(1, manager.getAllTasks().size());
        }

        @Test
        void getAllTasksReturnsSnapshotOfCollection() {
            manager.addTask(task);

            List<Task> snapshot = manager.getAllTasks();

            Task second = createTask("Second task", "Description");
            manager.addTask(second);

            assertEquals(1, snapshot.size());
            assertEquals(2, manager.getAllTasks().size());

            assertIterableEquals(List.of(task), snapshot);
        }
    }

    @Nested
    class SearchTests {

        @Test
        void searchIsCaseInsensitiveForNameAndDescription() {
            manager.addTask(task);

            assertEquals(1, manager.searchTasks("JAVA").size());
            assertEquals(1, manager.searchTasks("java").size());
            assertEquals(1, manager.searchTasks("eXaM").size());
        }

        @Test
        void searchReturnsAllMatchesInInsertionOrder() {
            Task first = createTask("Java basics", "Programming");
            Task second = createTask("Shopping", "Buy groceries");
            Task third = createTask("JUnit", "Java testing");

            manager.addTask(first);
            manager.addTask(second);
            manager.addTask(third);

            List<Task> results = manager.searchTasks("java");

            assertIterableEquals(List.of(first, third), results);
        }

        @Test
        void searchTrimsSurroundingWhitespace() {
            manager.addTask(task);

            List<Task> results = manager.searchTasks("  JAVA \t");

            assertEquals(1, results.size());
            assertSame(task, results.get(0));
        }

        @Test
        void searchWithNoMatchesReturnsEmptyList() {
            manager.addTask(task);

            assertTrue(manager.searchTasks("Python").isEmpty());
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = { "   ", "\t" })
        void invalidSearchQueryThrowsException(String query) {
            manager.addTask(task);

            assertThrows(
                    IllegalArgumentException.class,
                    () -> manager.searchTasks(query));

            assertEquals(1, manager.getAllTasks().size());
        }

        @Test
        void modifyingSearchResultsDoesNotChangeManager() {
            manager.addTask(task);

            List<Task> results = manager.searchTasks("Java");
            results.clear();

            assertTrue(results.isEmpty());
            assertEquals(1, manager.getAllTasks().size());
            assertSame(task, manager.getTaskById(task.getId()));
        }
    }

    @Nested
    class DisplayTests {

        @Test
        void displayingEmptyManagerReturnsInformativeMessage() {
            assertEquals(
                    "No tasks available.",
                    manager.displayTasks());
        }

        @Test
        void displayingMultipleTasksPreservesOrder() {
            Task second = createTask("Write tests", "JUnit tests");

            manager.addTask(task);
            manager.addTask(second);

            String output = manager.displayTasks();

            assertAll(
                    () -> assertTrue(output.contains("Study Java")),
                    () -> assertTrue(output.contains("Write tests")),
                    () -> assertTrue(
                            output.indexOf("Study Java") < output.indexOf("Write tests")),
                    () -> assertEquals(2, output.lines().count()));
        }
    }
}
