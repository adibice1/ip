package dibo.task;

/**
 * Represents a Todo task without any date/time constraints.
 * Extends the Task class.
 */
public class Todo extends Task {

    /**
     * Constructs a new Todo task with the given description.
     *
     * @param description the description of the todo task
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Parses user input to create a Todo task.
     * Extracts the description from the input string.
     *
     * @param userInput the user input starting with "todo"
     * @return a new Todo task with the parsed description
     * @throws IllegalArgumentException if the description is empty
     */
    public static Todo parseTodoInput(String userInput) {
        String description = userInput.replace("todo", "").trim();

        if (description.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty. Format: todo <description>");
        }

        return new Todo(description);
    }

    /**
     * Returns a string representation of the Todo task.
     * Includes the task type indicator [T].
     *
     * @return a string representation of the todo task
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Converts the Todo task to file format for storage.
     * Format: "T | [status] | [description]"
     *
     * @return a string representation of the task in file format
     */
    @Override
    public String toFileFormat() {
        return "T | " + (isDone ? "1" : "0") + " | " + getDescription();
    }
}