package dibo.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

public class Deadline extends Task {

    protected LocalDateTime dateTime;
    protected String by;

    public Deadline(String description, LocalDateTime dateTime, String by) {
        super(description);
        this.dateTime = dateTime;
        this.by = by;
    }

    public String getBy() {
        return this.by;
    }

    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

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
        String isoFormat = dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + isoFormat;
    }

    @Override
    public String toString() {
        String formattedDate = dateTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma"));
        return "[D]" + super.toString() + " (by: " + formattedDate + ")";
    }
}

