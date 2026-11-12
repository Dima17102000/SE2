package pdp1.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class TaskTest {

    private static final LocalDate DUE_DATE = LocalDate.of(2026, 12, 1);

    private Task createTask() {
        return new Task("Study Java", "Prepare for exam", DUE_DATE);
    }

    @Nested
    class ConstructorTests {

        @Test
        void constructorInitializesAllFieldsCorrectly() {
            Task task = createTask();

            assertAll(
                    () -> assertNotNull(task.getId()),
                    () -> assertEquals("Study Java", task.getName()),
                    () -> assertEquals(
                            "Prepare for exam",
                            task.getDescription()),
                    () -> assertEquals(DUE_DATE, task.getDueDate()),
                    () -> assertFalse(task.isCompleted()));
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = { "   ", "\t", "\n" })
        void constructorRejectsInvalidNames(String name) {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> new Task(name, "Description", DUE_DATE));
        }

        @Test
        void constructorRejectsNullDescription() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> new Task("Valid name", null, DUE_DATE));
        }

        @Test
        void constructorRejectsNullDueDate() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> new Task("Valid name", "Description", null));
        }

        @Test
        void constructorTrimsNameAndDescription() {
            Task task = new Task(
                    "  Study Java  ",
                    "  Prepare for exam  ",
                    DUE_DATE);

            assertEquals("Study Java", task.getName());
            assertEquals("Prepare for exam", task.getDescription());
        }

        @Test
        void constructorAcceptsEmptyDescription() {
            Task task = new Task("Study Java", "", DUE_DATE);

            assertEquals("", task.getDescription());
        }

        @Test
        void constructorAcceptsPastDueDate() {
            LocalDate pastDate = LocalDate.of(2020, 1, 1);

            Task task = new Task(
                    "Old task",
                    "Description",
                    pastDate);

            assertEquals(pastDate, task.getDueDate());
        }

        @Test
        void creatingManyTasksProducesUniqueIds() {
            Set<UUID> ids = new HashSet<>();

            for (int i = 0; i < 100; i++) {
                Task task = new Task(
                        "Task " + i,
                        "Description " + i,
                        DUE_DATE);

                assertTrue(ids.add(task.getId()));
            }

            assertEquals(100, ids.size());
        }
    }

    @Nested
    class SetterTests {

        @Test
        void settersUpdateAllMutableFields() {
            Task task = createTask();

            LocalDate newDate = LocalDate.of(2027, 2, 15);

            task.setName("Updated task");
            task.setDescription("Updated description");
            task.setDueDate(newDate);
            task.setCompleted(true);

            assertAll(
                    () -> assertEquals(
                            "Updated task", task.getName()),
                    () -> assertEquals(
                            "Updated description",
                            task.getDescription()),
                    () -> assertEquals(newDate, task.getDueDate()),
                    () -> assertTrue(task.isCompleted()));
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = { "   ", "\t" })
        void invalidNameDoesNotChangeExistingName(String name) {
            Task task = createTask();

            assertThrows(
                    IllegalArgumentException.class,
                    () -> task.setName(name));

            assertEquals("Study Java", task.getName());
        }

        @Test
        void nullDescriptionDoesNotChangeExistingDescription() {
            Task task = createTask();

            assertThrows(
                    IllegalArgumentException.class,
                    () -> task.setDescription(null));

            assertEquals(
                    "Prepare for exam",
                    task.getDescription());
        }

        @Test
        void nullDueDateDoesNotChangeExistingDate() {
            Task task = createTask();

            assertThrows(
                    IllegalArgumentException.class,
                    () -> task.setDueDate(null));

            assertEquals(DUE_DATE, task.getDueDate());
        }

        @Test
        void settersTrimStringValues() {
            Task task = createTask();

            task.setName("  New name  ");
            task.setDescription("  New description  ");

            assertAll(
                    () -> assertEquals("New name", task.getName()),
                    () -> assertEquals(
                            "New description",
                            task.getDescription()));
        }

        @Test
        void completionStatusCanBeChangedRepeatedly() {
            Task task = createTask();

            assertFalse(task.isCompleted());

            task.setCompleted(true);
            assertTrue(task.isCompleted());

            task.setCompleted(false);
            assertFalse(task.isCompleted());
        }

        @Test
        void updatingFieldsDoesNotChangeTaskId() {
            Task task = createTask();
            UUID originalId = task.getId();

            task.setName("Updated name");
            task.setDescription("Updated description");
            task.setDueDate(LocalDate.of(2027, 1, 1));
            task.setCompleted(true);

            assertEquals(originalId, task.getId());
        }
    }

    @Nested
    class RepresentationTests {

        @Test
        void toStringContainsAllImportantFields() {
            Task task = createTask();

            String result = task.toString();

            assertAll(
                    () -> assertTrue(
                            result.contains(task.getId().toString())),
                    () -> assertTrue(
                            result.contains("Study Java")),
                    () -> assertTrue(
                            result.contains("Prepare for exam")),
                    () -> assertTrue(
                            result.contains("2026-12-01")),
                    () -> assertTrue(
                            result.contains("completed=false")));
        }

        @Test
        void toStringReflectsUpdatedState() {
            Task task = createTask();

            task.setName("Finished task");
            task.setCompleted(true);

            String result = task.toString();

            assertAll(
                    () -> assertTrue(
                            result.contains("Finished task")),
                    () -> assertTrue(
                            result.contains("completed=true")));
        }
    }
}
