public class Event extends Task {
    protected String from;
    protected String to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public static Event parseEventInput(String userInput) {
        String input = userInput.replace("event", "").trim();
        String[] parts = input.split("/from");

        String description = parts[0].trim();
        String subInput = parts[1];
        String[] subParts = subInput.split("/to");

        String from = subParts[0].trim();
        String to = subParts[1].trim();
        return new Event(description, from, to);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
