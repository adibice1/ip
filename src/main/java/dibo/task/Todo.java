package dibo.task;

public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    public static Todo parseTodoInput(String userInput) {
        String description = userInput.replace("todo", "").trim();

        if (description.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty. Format: todo <description>");
        }

        return new Todo(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String toFileFormat() {
        return "T | " + (isDone ? "1" : "0") + " | " + getDescription();
    }
}
