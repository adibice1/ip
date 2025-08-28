public class Event extends Task {
    protected String from;
    protected String to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public static Event parseEventInput(String userInput) {
        if (!userInput.toLowerCase().startsWith("event")) {
            throw new IllegalArgumentException("Input must start with 'event'");
        }
        if (!userInput.contains("/from")) {
            throw new IllegalArgumentException("Missing '/from' in deadline command. Format: event <description> /from <time> /to <time>");
        }
        if (!userInput.contains("/to")) {
            throw new IllegalArgumentException("Missing '/to' in deadline command. Format: event <description> /from <time> /to <time>");
        }

        String input = userInput.replace("event", "").trim();
        String[] parts = input.split("/from");

        String description = parts[0].trim();
        String subInput = parts[1];
        String[] subParts = subInput.split("/to");

        String from = subParts[0].trim();
        String to = subParts[1].trim();

        if (description.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty. Format: event <description> /from <time> /to <time>");
        }
        if (from.isEmpty()) {
            throw new IllegalArgumentException("Time cannot be empty after /from. Format: event <description> /from <time> /to <time>");
        }
        if (to.isEmpty()) {
            throw new IllegalArgumentException("Time cannot be empty after /to. Format: event <description> /from <time> /to <time>");
        }

        return new Event(description, from, to);
    }

    @Override
    public String toFileFormat() {
        return "E | " + (isDone ? "1" : "0") + " | " + getDescription() + " | " + from + "|" + to;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
