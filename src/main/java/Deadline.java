public class Deadline extends Task {

    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    public String getBy() {
        return this.by;
    }

    public static Deadline parseDeadlineInput(String userInput) {
        if (!userInput.toLowerCase().startsWith("deadline")) {
            throw new IllegalArgumentException("Input must start with 'deadline'");
        }
        if (!userInput.contains("/by")) {
            throw new IllegalArgumentException("Missing '/by' in deadline command. Format: deadline <description> /by <time>");
        }

        String input = userInput.replace("deadline", "").trim();
        String[] parts = input.split("/by");

        String description = parts[0].trim();
        String by = parts[1].trim();

        if (description.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty. Format: deadline <description> /by <time>");
        }
        if (by.isEmpty()) {
            throw new IllegalArgumentException("Time cannot be empty after /by. Format: deadline <description> /by <time>");
        }

        return new Deadline(description, by);
    }

    @Override
    public String toFileFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " + getDescription() + " | " + by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}

