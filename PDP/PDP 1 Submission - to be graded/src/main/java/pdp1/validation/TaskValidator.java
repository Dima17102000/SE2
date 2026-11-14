package pdp1.validation;

import java.time.LocalDate;

public class TaskValidator {

    public String validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Task name must not be null or blank.");
        }

        return name.trim();
    }

    public String validateDescription(String description) {
        if (description == null) {
            throw new IllegalArgumentException(
                    "Task description must not be null.");
        }

        return description.trim();
    }

    public LocalDate validateDueDate(LocalDate dueDate) {
        if (dueDate == null) {
            throw new IllegalArgumentException(
                    "Task due date must not be null.");
        }

        return dueDate;
    }

    public String validateSearchQuery(String query) {
        if (query == null || query.isBlank()) {
            throw new IllegalArgumentException(
                    "Search query must not be null or blank.");
        }

        return query.trim();
    }
}
