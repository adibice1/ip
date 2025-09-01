package dibo.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class DeadlineTest {

    @Test
    public void testDeadlineCreation() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 12, 25, 18, 0);
        Deadline deadline = new Deadline("Submit report", dateTime, "2023-12-25 1800");

        assertEquals("Submit report", deadline.getDescription());
        assertEquals("2023-12-25 1800", deadline.getBy());
        assertEquals(dateTime, deadline.getDateTime());
        assertFalse(deadline.isDone);
    }

    @Test
    public void testDeadlineToString() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 12, 25, 18, 0);
        Deadline deadline = new Deadline("Submit report", dateTime, "2023-12-25 1800");

        assertEquals("[D][ ] Submit report (by: Dec 25 2023, 6:00pm)", deadline.toString());

        deadline.markAsDone();
        assertEquals("[D][X] Submit report (by: Dec 25 2023, 6:00PM)", deadline.toString());
    }

    @Test
    public void testDeadlineToFileFormat() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 12, 25, 18, 0);
        Deadline deadline = new Deadline("Submit report", dateTime, "2023-12-25 1800");

        assertEquals("D | 0 | Submit report | 2023-12-25T18:00:00", deadline.toFileFormat());
    }

    @Test
    public void testParseDeadlineInput_validInput() {
        String input = "deadline Submit report /by 2023-12-25 1800";
        Deadline deadline = Deadline.parseDeadlineInput(input);

        assertEquals("Submit report", deadline.getDescription());
        assertEquals("2023-12-25 1800", deadline.getBy());
        assertEquals(LocalDateTime.of(2023, 12, 25, 18, 0), deadline.getDateTime());
    }

    @Test
    public void testParseDeadlineInput_missingBy() {
        String input = "deadline Submit report";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Deadline.parseDeadlineInput(input);
        });

        assertTrue(exception.getMessage().contains("Missing '/by'"));
    }

    @Test
    public void testParseDeadlineInput_emptyDescription() {
        String input = "deadline  /by 2023-12-25";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Deadline.parseDeadlineInput(input);
        });

        assertEquals("Description cannot be empty. Format: deadline <description> /by <time>", exception.getMessage());
    }

    @Test
    public void testParseDeadlineInput_emptyTime() {
        String input = "deadline Submit report /by ";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Deadline.parseDeadlineInput(input);
        });

        assertEquals("Time cannot be empty after /by. Format: deadline <description> /by <time>", exception.getMessage());
    }

    @Test
    public void testParseDateTime_validFormats() {
        // Test various date formats
        LocalDateTime result1 = Deadline.parseDateTime("2023-12-25 1800");
        assertEquals(LocalDateTime.of(2023, 12, 25, 18, 0), result1);

        LocalDateTime result2 = Deadline.parseDateTime("25/12/2023 1800");
        assertEquals(LocalDateTime.of(2023, 12, 25, 18, 0), result2);

        LocalDateTime result3 = Deadline.parseDateTime("2023-12-25");
        assertEquals(LocalDateTime.of(2023, 12, 25, 23, 59), result3);
    }

    @Test
    public void testParseDateTime_invalidFormat() {
        assertThrows(DateTimeParseException.class, () -> {
            Deadline.parseDateTime("invalid-date-format");
        });
    }
}