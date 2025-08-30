import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Event extends Task {
    protected String from;
    protected String to;
    protected LocalDateTime fromDateTime;
    protected LocalDateTime toDateTime;

    public Event(String description, String from, String to, LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        super(description);
        this.from = from;
        this.to = to;
        this.fromDateTime = fromDateTime;
        this.toDateTime = toDateTime;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public LocalDateTime getFromDateTime() {
        return fromDateTime;
    }

    public LocalDateTime getToDateTime() {
        return toDateTime;
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

        LocalDateTime fromDateTime = parseDateTime(from);
        LocalDateTime toDateTime = parseDateTime(to);

        return new Event(description, from, to, fromDateTime, toDateTime);
    }

    private static LocalDateTime parseDateTime(String dateTimeString) {
        List<String> patterns = Arrays.asList(
                "yyyy-MM-dd HHmm",    // 2019-12-02 1800
                "dd/MM/yyyy HHmm",    // 02/12/2019 1800
                "MM/dd/yyyy HHmm",    // 12/02/2019 1800
                "dd-MM-yyyy HHmm",    // 02-12-2019 1800
                "MMM dd yyyy HHmm",   // Dec 02 2019 1800
                "dd MMM yyyy HHmm",   // 02 Dec 2019 1800
                "yyyy-MM-dd",         // 2019-12-02 (time defaults to 00:00)
                "dd/MM/yyyy",         // 02/12/2019
                "MM/dd/yyyy",         // 12/02/2019
                "dd-MM-yyyy",         // 02-12-2019
                "MMM dd yyyy",        // Dec 02 2019
                "dd MMM yyyy"         // 02 Dec 2019
        );
        for (String pattern : patterns) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

                if (pattern.contains("HHmm")) {
                    return LocalDateTime.parse(dateTimeString, formatter);
                } else {
                    LocalDate date = LocalDate.parse(dateTimeString, formatter);
                    return LocalDateTime.of(date, LocalTime.of(23, 59));
                }
            } catch (DateTimeParseException e) {
                continue;
            }
        }

        throw new DateTimeParseException("Unsupported date-time format: " + dateTimeString, dateTimeString, 0);
    }

    @Override
    public String toFileFormat() {
        String fromIsoFormat = fromDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String toIsoFormat = toDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        return "E | " + (isDone ? "1" : "0") + " | " + getDescription() + " | " + fromIsoFormat + "|" + toIsoFormat;
    }

    @Override
    public String toString() {
        String fromFormattedDate = fromDateTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma"));
        String toFormattedDate = toDateTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma"));
        return "[E]" + super.toString() + " (from: " + fromFormattedDate + " to: " + toFormattedDate + ")";
    }
}
