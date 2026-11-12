package pdp1.model;

import java.time.LocalDate;
import java.util.UUID;

import pdp1.validation.TaskValidator;

public class Task {

    private final UUID id;
    private String name;
    private String description;
    private LocalDate dueDate;
    private boolean completed;

    public Task(String name, String description, LocalDate dueDate) {
        this.id = UUID.randomUUID();
        setName(name);
        setDescription(description);
        setDueDate(dueDate);
        this.completed = false;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = TaskValidator.validateName(name);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = TaskValidator.validateDescription(description);
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = TaskValidator.validateDueDate(dueDate);
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", completed=" + completed +
                '}';
    }
}
