package pdp1.validation;

import java.time.LocalDate;

public final class TaskValidator {

    private TaskValidator() {
    }

    public static String validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Task name must not be null or blank.");
        }

        return name.trim();
    }

    public static String validateDescription(String description) {
        if (description == null) {
            throw new IllegalArgumentException(
                    "Task description must not be null.");
        }

        return description.trim();
    }

    public static LocalDate validateDueDate(LocalDate dueDate) {
        if (dueDate == null) {
            throw new IllegalArgumentException(
                    "Task due date must not be null.");
        }

        return dueDate;
    }
}
