package dibo.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TodoTest {

    @Test
    public void testTodoCreation() {
        Todo todo = new Todo("Read book");
        assertEquals("Read book", todo.getDescription());
        assertFalse(todo.isDone);
        assertEquals("[T][ ] Read book", todo.toString());
    }

    @Test
    public void testTodoMarkAsDone() {
        Todo todo = new Todo("Read book");
        todo.markAsDone();
        assertTrue(todo.isDone);
        assertEquals("[T][X] Read book", todo.toString());
    }

    @Test
    public void testTodoMarkAsUndone() {
        Todo todo = new Todo("Read book");
        todo.markAsDone();
        todo.markAsUndone();
        assertFalse(todo.isDone);
        assertEquals("[T][ ] Read book", todo.toString());
    }

    @Test
    public void testTodoToFileFormat() {
        Todo todo = new Todo("Read book");
        assertEquals("T | 0 | Read book", todo.toFileFormat());

        todo.markAsDone();
        assertEquals("T | 1 | Read book", todo.toFileFormat());
    }

    @Test
    public void testParseTodoInput_validInput() {
        String input = "todo Read book";
        Todo todo = Todo.parseTodoInput(input);

        assertEquals("Read book", todo.getDescription());
        assertFalse(todo.isDone);
    }

    @Test
    public void testParseTodoInput_emptyDescription() {
        String input = "todo ";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Todo.parseTodoInput(input);
        });

        assertEquals("Description cannot be empty. Format: todo <description>", exception.getMessage());
    }

    @Test
    public void testParseTodoInput_onlyCommand() {
        String input = "todo";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Todo.parseTodoInput(input);
        });

        assertEquals("Description cannot be empty. Format: todo <description>", exception.getMessage());
    }
}