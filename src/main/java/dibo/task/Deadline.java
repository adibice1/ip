package dibo.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a Deadline task with a specific due date and time.
 * Extends the Task class.
 */
public class Deadline extends Task {

    protected LocalDateTime dateTime;
    protected String by;

    /**
     * Constructs a new Deadline task with description, due date/time, and original input string.
     *
     * @param description the description of the deadline task
     * @param dateTime the due date and time as LocalDateTime
     * @param by the original due date string from user input
     */
    public Deadline(String description, LocalDateTime dateTime, String by) {
        super(description);
        this.dateTime = dateTime;
        this.by = by;
    }

    /**
     * Returns the original due date string from user input.
     *
     * @return the due date string
     */
    public String getBy() {
        return this.by;
    }

    /**
     * Returns the parsed due date and time.
     *
     * @return the due date and time as LocalDateTime
     */
    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    /**
     * Parses user input to create a Deadline task.
     * Extracts description and due date from the input string.
     *
     * @param userInput the user input starting with "deadline" and containing "/by"
     * @return a new Deadline task with parsed description and due date
     * @throws IllegalArgumentException if the input format is invalid
     * @throws DateTimeParseException if the date/time format is unsupported
     */
    public static Deadline parseDeadlineInput(String userInput) {
        if (!userInput.toLowerCase().startsWith("deadline")) {
            throw new IllegalArgumentException("Input must start with 'deadline'");
        }
        if (!userInput.contains("/by")) {
            throw new IllegalArgumentException("Missing '/by' in deadline dibo.command. Format: deadline <description> /by <time>");
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

        LocalDateTime dateTime = parseDateTime(by);
        return new Deadline(description, dateTime, by);
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
     * Converts the Deadline task to file format for storage.
     * Format: "D | [status] | [description] | [ISO date-time]"
     *
     * @return a string representation of the task in file format
     */
    @Override
    public String toFileFormat() {
        String isoFormat = dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + isoFormat;
    }

    /**
     * Returns a string representation of the Deadline task.
     * Includes the task type indicator [D] and formatted due date.
     *
     * @return a string representation of the deadline task
     */
    @Override
    public String toString() {
        String formattedDate = dateTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma"));
        return "[D]" + super.toString() + " (by: " + formattedDate + ")";
    }
}