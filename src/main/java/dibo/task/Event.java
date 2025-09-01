package dibo.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

/**
 * Represents an Event task with start and end date/time.
 * Extends the Task class.
 */
public class Event extends Task {
    protected String from;
    protected String to;
    protected LocalDateTime fromDateTime;
    protected LocalDateTime toDateTime;

    /**
     * Constructs a new Event task with description, start/end times, and parsed date/time objects.
     *
     * @param description the description of the event
     * @param from the original start time string from user input
     * @param to the original end time string from user input
     * @param fromDateTime the parsed start date and time
     * @param toDateTime the parsed end date and time
     */
    public Event(String description, String from, String to, LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        super(description);
        this.from = from;
        this.to = to;
        this.fromDateTime = fromDateTime;
        this.toDateTime = toDateTime;
    }

    /**
     * Returns the original start time string from user input.
     *
     * @return the start time string
     */
    public String getFrom() {
        return from;
    }

    /**
     * Returns the original end time string from user input.
     *
     * @return the end time string
     */
    public String getTo() {
        return to;
    }

    /**
     * Returns the parsed start date and time.
     *
     * @return the start date and time as LocalDateTime
     */
    public LocalDateTime getFromDateTime() {
        return fromDateTime;
    }

    /**
     * Returns the parsed end date and time.
     *
     * @return the end date and time as LocalDateTime
     */
    public LocalDateTime getToDateTime() {
        return toDateTime;
    }

    /**
     * Parses user input to create an Event task.
     * Extracts description, start time, and end time from the input string.
     *
     * @param userInput the user input starting with "event" and containing "/from" and "/to"
     * @return a new Event task with parsed description and times
     * @throws IllegalArgumentException if the input format is invalid
     * @throws DateTimeParseException if the date/time format is unsupported
     */
    public static Event parseEventInput(String userInput) {
        if (!userInput.toLowerCase().startsWith("event")) {
            throw new IllegalArgumentException("Input must start with 'event'");
        }
        if (!userInput.contains("/from")) {
            throw new IllegalArgumentException("Missing '/from' in deadline dibo.command. Format: event <description> /from <time> /to <time>");
        }
        if (!userInput.contains("/to")) {
            throw new IllegalArgumentException("Missing '/to' in deadline dibo.command. Format: event <description> /from <time> /to <time>");
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

    /**
     * Parses a date-time string into LocalDateTime using multiple supported formats.
     *
     * @param dateTimeString the date-time string to parse
     * @return the parsed LocalDateTime object
     * @throws DateTimeParseException if no supported format matches the input string
     */
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

    /**
     * Converts the Event task to file format for storage.
     * Format: "E | [status] | [description] | [ISO from date-time]|[ISO to date-time]"
     *
     * @return a string representation of the task in file format
     */
    @Override
    public String toFileFormat() {
        String fromIsoFormat = fromDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String toIsoFormat = toDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        return "E | " + (isDone ? "1" : "0") + " | " + getDescription() + " | " + fromIsoFormat + "|" + toIsoFormat;
    }

    /**
     * Returns a string representation of the Event task.
     * Includes the task type indicator [E] and formatted start/end times.
     *
     * @return a string representation of the event task
     */
    @Override
    public String toString() {
        String fromFormattedDate = fromDateTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma"));
        String toFormattedDate = toDateTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma"));
        return "[E]" + super.toString() + " (from: " + fromFormattedDate + " to: " + toFormattedDate + ")";
    }
}