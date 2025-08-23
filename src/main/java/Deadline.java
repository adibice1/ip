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
        String input = userInput.replace("deadline", "").trim();
        String[] parts = input.split("/by");

        String description = parts[0].trim();
        String by = parts[1].trim();
        return new Deadline(description, by);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}

