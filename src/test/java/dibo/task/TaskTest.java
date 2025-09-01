package dibo.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    // Test using a concrete subclass (Todo)
    @Test
    public void testTaskCreation() {
        Todo task = new Todo("Test task");
        assertEquals("Test task", task.getDescription());
        assertFalse(task.isDone);
    }

    @Test
    public void testMarkAsDone() {
        Todo task = new Todo("Test task");
        task.markAsDone();
        assertTrue(task.isDone);
    }

    @Test
    public void testMarkAsUndone() {
        Todo task = new Todo("Test task");
        task.markAsDone();
        task.markAsUndone();
        assertFalse(task.isDone);
    }

    @Test
    public void testGetStatusIcon() {
        Todo task = new Todo("Test task");
        assertEquals(" ", task.getStatusIcon());

        task.markAsDone();
        assertEquals("X", task.getStatusIcon());
    }

    @Test
    public void testToString() {
        Todo task = new Todo("Test task");
        assertEquals("[T][ ] Test task", task.toString());

        task.markAsDone();
        assertEquals("[T][X] Test task", task.toString());
    }
}