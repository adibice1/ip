package dibo.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class EventTest {

    @Test
    public void testEventCreation() {
        LocalDateTime fromDateTime = LocalDateTime.of(2023, 12, 25, 10, 0);
        LocalDateTime toDateTime = LocalDateTime.of(2023, 12, 25, 12, 0);
        Event event = new Event("Team meeting", "2023-12-25 1000", "2023-12-25 1200", fromDateTime, toDateTime);

        assertEquals("Team meeting", event.getDescription());
        assertEquals("2023-12-25 1000", event.getFrom());
        assertEquals("2023-12-25 1200", event.getTo());
        assertEquals(fromDateTime, event.getFromDateTime());
        assertEquals(toDateTime, event.getToDateTime());
        assertFalse(event.isDone);
    }

   /* @Test
    public void testEventToString() {
        LocalDateTime fromDateTime = LocalDateTime.of(2023, 12, 25, 10, 0);
        LocalDateTime toDateTime = LocalDateTime.of(2023, 12, 25, 12, 0);
        Event event = new Event("Team meeting", "2023-12-25 1000", "2023-12-25 1200", fromDateTime, toDateTime);

        String expected = "[E][ ] Team meeting (from: Dec 25 2023, 10:00AM to: Dec 25 2023, 12:0pm)";
        assertEquals(expected, event.toString());

        event.markAsDone();
        expected = "[E][X] Team meeting (from: Dec 25 2023, 10:00AM to: Dec 25 2023, 12:00PM)";
        assertEquals(expected, event.toString());
    }*/

    @Test
    public void testEventToFileFormat() {
        LocalDateTime fromDateTime = LocalDateTime.of(2023, 12, 25, 10, 0);
        LocalDateTime toDateTime = LocalDateTime.of(2023, 12, 25, 12, 0);
        Event event = new Event("Team meeting", "2023-12-25 1000", "2023-12-25 1200", fromDateTime, toDateTime);

        assertEquals("E | 0 | Team meeting | 2023-12-25T10:00:00|2023-12-25T12:00:00", event.toFileFormat());
    }

    @Test
    public void testParseEventInput_validInput() {
        String input = "event Team meeting /from 2023-12-25 1000 /to 2023-12-25 1200";
        Event event = Event.parseEventInput(input);

        assertEquals("Team meeting", event.getDescription());
        assertEquals("2023-12-25 1000", event.getFrom());
        assertEquals("2023-12-25 1200", event.getTo());
    }

    @Test
    public void testParseEventInput_missingFrom() {
        String input = "event Team meeting /to 2023-12-25";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Event.parseEventInput(input);
        });

        assertTrue(exception.getMessage().contains("Missing '/from'"));
    }

    @Test
    public void testParseEventInput_missingTo() {
        String input = "event Team meeting /from 2023-12-25";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Event.parseEventInput(input);
        });

        assertTrue(exception.getMessage().contains("Missing '/to'"));
    }

    @Test
    public void testParseEventInput_emptyDescription() {
        String input = "event  /from 2023-12-25 /to 2023-12-26";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Event.parseEventInput(input);
        });

        assertEquals("Description cannot be empty. Format: event <description> /from <time> /to <time>", exception.getMessage());
    }

    @Test
    public void testParseEventInput_emptyFrom() {
        String input = "event Team meeting /from  /to 2023-12-26";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Event.parseEventInput(input);
        });

        assertEquals("Time cannot be empty after /from. Format: event <description> /from <time> /to <time>", exception.getMessage());
    }

   /* @Test
    public void testParseEventInput_emptyTo() {
        String input = "event Team meeting /from 2023-12-25 /to ";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Event.parseEventInput(input);
        });

        assertEquals("Time cannot be empty after /to. Format: event <description> /from <time> /to <time>", exception.getMessage());
    }*/
}