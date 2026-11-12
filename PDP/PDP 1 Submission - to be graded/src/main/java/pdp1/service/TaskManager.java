
package pdp1.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.logging.Logger;

import pdp1.model.Task;
import pdp1.validation.TaskValidator;

public class TaskManager {

    private static final Logger LOGGER = Logger.getLogger(TaskManager.class.getName());

    private final Map<UUID, Task> tasks = new LinkedHashMap<>();

    public void addTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task must not be null.");
        }

        if (tasks.containsKey(task.getId())) {
            throw new IllegalArgumentException("Task already exists.");
        }

        tasks.put(task.getId(), task);
        LOGGER.info("Task added: " + task.getId());
    }

    public Task getTaskById(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("Task ID must not be null.");
        }

        Task task = tasks.get(id);

        if (task == null) {
            throw new NoSuchElementException("Task not found: " + id);
        }

        return task;
    }

    public void updateTask(
            UUID id,
            String name,
            String description,
            LocalDate dueDate,
            boolean completed) {

        Task task = getTaskById(id);

        String validName = TaskValidator.validateName(name);
        String validDescription = TaskValidator.validateDescription(description);
        LocalDate validDueDate = TaskValidator.validateDueDate(dueDate);

        task.setName(validName);
        task.setDescription(validDescription);
        task.setDueDate(validDueDate);
        task.setCompleted(completed);

        LOGGER.info("Task updated: " + id);
    }

    public List<Task> searchTasks(String query) {
        if (query == null || query.isBlank()) {
            throw new IllegalArgumentException(
                    "Search query must not be null or blank.");
        }

        String keyword = query.trim().toLowerCase(Locale.ROOT);
        List<Task> results = new ArrayList<>();

        for (Task task : tasks.values()) {
            String name = task.getName().toLowerCase(Locale.ROOT);
            String description = task.getDescription().toLowerCase(Locale.ROOT);

            if (name.contains(keyword) || description.contains(keyword)) {
                results.add(task);
            }
        }

        return results;
    }

    public List<Task> getAllTasks() {
        return List.copyOf(tasks.values());
    }

    public String displayTasks() {
        if (tasks.isEmpty()) {
            return "No tasks available.";
        }

        StringBuilder result = new StringBuilder();

        for (Task task : tasks.values()) {
            result.append(task).append(System.lineSeparator());
        }

        return result.toString();
    }
}
