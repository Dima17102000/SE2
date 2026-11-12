package pdp1.validation;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class TaskValidatorTest {

    @Nested
    class NameValidationTests {

        @Test
        void validNameIsReturnedUnchanged() {
            String result = TaskValidator.validateName("Study Java");

            assertEquals("Study Java", result);
        }

        @Test
        void surroundingWhitespaceIsRemoved() {
            String result = TaskValidator.validateName(
                    "  Study Java  ");

            assertEquals("Study Java", result);
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = { "   ", "\t", "\n" })
        void invalidNamesThrowException(String name) {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> TaskValidator.validateName(name));
        }

        @Test
        void unicodeNameIsAccepted() {
            String name = "Подготовка к экзамену";

            assertEquals(
                    name,
                    TaskValidator.validateName(name));
        }

        @Test
        void longNonBlankNameIsAccepted() {
            String name = "A".repeat(1000);

            String result = TaskValidator.validateName(name);

            assertEquals(1000, result.length());
            assertEquals(name, result);
        }
    }

    @Nested
    class DescriptionValidationTests {

        @Test
        void validDescriptionIsReturnedUnchanged() {
            String description = "Prepare for Java exam";

            assertEquals(
                    description,
                    TaskValidator.validateDescription(description));
        }

        @NullSource
        @ParameterizedTest
        void nullDescriptionThrowsException(String description) {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> TaskValidator.validateDescription(description));
        }

        @Test
        void emptyDescriptionIsAccepted() {
            assertEquals(
                    "",
                    TaskValidator.validateDescription(""));
        }

        @Test
        void whitespaceOnlyDescriptionBecomesEmpty() {
            assertEquals(
                    "",
                    TaskValidator.validateDescription("   "));
        }

        @Test
        void surroundingWhitespaceIsRemoved() {
            String result = TaskValidator.validateDescription(
                    "  Prepare for exam  ");

            assertEquals("Prepare for exam", result);
        }

        @Test
        void multilineDescriptionIsPreserved() {
            String description = "First line\nSecond line";

            assertEquals(
                    description,
                    TaskValidator.validateDescription(description));
        }
    }

    @Nested
    class DueDateValidationTests {

        @Test
        void validDueDateIsReturned() {
            LocalDate date = LocalDate.of(2026, 12, 1);

            assertEquals(
                    date,
                    TaskValidator.validateDueDate(date));
        }

        @Test
        void nullDueDateThrowsException() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> TaskValidator.validateDueDate(null));
        }

        @Test
        void pastDueDateIsAccepted() {
            LocalDate date = LocalDate.of(2000, 1, 1);

            assertEquals(
                    date,
                    TaskValidator.validateDueDate(date));
        }

        @Test
        void futureDueDateIsAccepted() {
            LocalDate date = LocalDate.of(2099, 12, 31);

            assertEquals(
                    date,
                    TaskValidator.validateDueDate(date));
        }

        @Test
        void leapDayIsAccepted() {
            LocalDate date = LocalDate.of(2028, 2, 29);

            assertEquals(
                    date,
                    TaskValidator.validateDueDate(date));
        }
    }
}
