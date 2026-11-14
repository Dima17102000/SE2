
package pdp1.app;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import pdp1.model.Task;
import pdp1.service.TaskManager;
import pdp1.validation.TaskValidator;

public final class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private final TaskValidator validator;
    private final TaskManager manager;

    private Main(TaskValidator validator) {
        this.validator = Objects.requireNonNull(validator);
        this.manager = new TaskManager(validator);
    }

    public static void main(String[] args) {
        new Main(new TaskValidator()).run();
    }

    private void run() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in, StandardCharsets.UTF_8))) {

            while (true) {
                showMenu();

                try {
                    String option = readLine(
                            reader, "Choose an option: ").trim();

                    switch (option) {
                        case "1" -> addTask(reader);
                        case "2" -> updateTask(reader);
                        case "3" -> searchTasks(reader);
                        case "4" -> System.out.println(
                                manager.displayTasks());
                        case "5" -> {
                            System.out.println("Goodbye!");
                            return;
                        }
                        default -> System.out.println(
                                "Please choose a number from 1 to 5.");
                    }

                } catch (EOFException e) {
                    System.out.println("\nInput closed. Exiting.");
                    return;

                } catch (IllegalArgumentException
                        | DateTimeParseException
                        | NoSuchElementException e) {

                    System.err.println(
                            "Operation failed: " + e.getMessage());

                    LOGGER.log(
                            Level.WARNING,
                            "Task operation rejected ({0})",
                            e.getClass().getSimpleName());
                }
            }

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,
                    "Could not read console input", e);

            System.err.println(
                    "An input/output error occurred. Exiting.");
        }
    }

    private static void showMenu() {
        System.out.println(
                "\n--- PDP1 Task Management Application ---");
        System.out.println("1. Add task");
        System.out.println("2. Update task");
        System.out.println("3. Search tasks");
        System.out.println("4. Show all tasks");
        System.out.println("5. Exit");
    }

    private void addTask(BufferedReader reader) throws IOException {

        String name = validator.validateName(
                readLine(reader, "Task name: "));

        String description = validator.validateDescription(
                readLine(reader, "Description: "));

        LocalDate dueDate = validator.validateDueDate(
                LocalDate.parse(
                        readLine(reader, "Due date (YYYY-MM-DD): ").trim()));

        Task task = new Task(name, description, dueDate, validator);

        manager.addTask(task);

        System.out.println("Task added. ID: " + task.getId());
    }

    private void updateTask(BufferedReader reader) throws IOException {

        UUID id = UUID.fromString(
                readLine(reader, "Task ID (UUID): ").trim());

        Task current = manager.getTaskById(id);

        System.out.println("Current task: " + current);

        String name = validator.validateName(
                readLine(reader, "New name: "));

        String description = validator.validateDescription(
                readLine(reader, "New description: "));

        LocalDate dueDate = validator.validateDueDate(
                LocalDate.parse(
                        readLine(reader, "New due date (YYYY-MM-DD): ").trim()));

        boolean completed = parseCompleted(
                readLine(reader, "Completed (yes/no): "));

        manager.updateTask(
                id, name, description, dueDate, completed);

        System.out.println("Task updated.");
    }

    private void searchTasks(BufferedReader reader) throws IOException {

        String query = validator.validateSearchQuery(
                readLine(reader, "Search keyword: "));

        List<Task> results = manager.searchTasks(query);

        if (results.isEmpty()) {
            System.out.println("No matching tasks.");
        } else {
            for (Task task : results) {
                System.out.println(task);
            }
        }
    }

    private static boolean parseCompleted(String answer) {
        return switch (answer.trim().toLowerCase(Locale.ROOT)) {
            case "yes", "y", "true" -> true;
            case "no", "n", "false" -> false;
            default -> throw new IllegalArgumentException(
                    "Completed must be yes or no.");
        };
    }

    private static String readLine(
            BufferedReader reader,
            String prompt) throws IOException {

        System.out.print(prompt);

        String line = reader.readLine();

        if (line == null) {
            throw new EOFException("Console input ended");
        }

        return line;
    }
}
