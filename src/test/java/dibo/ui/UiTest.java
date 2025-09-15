package dibo.ui;

import dibo.task.Task;
import dibo.task.TaskList;
import dibo.task.Todo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

public class UiTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    private Ui ui;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        ui = new Ui();
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    /*@Test
    public void testShowWelcome() {
        ui.showWelcome();
        String expected = "===============================================\n" +
                "Hello! I'm Dibo the Dragon\n" +
                "What can I do for you?\n" +
                "===============================================\n";
        assertEquals(expected, outContent.toString());
    }*/

    /*@Test
    public void testShowGoodbye() {
        ui.showGoodbye();
        String expected = "Bye. Hope to see you again soon!\n";
        assertEquals(expected, outContent.toString());
    }*/

    @Test
    public void testReadCommand() {
        String testInput = "test command\n";
        System.setIn(new ByteArrayInputStream(testInput.getBytes()));
        Ui uiWithInput = new Ui();

        String command = uiWithInput.readCommand();
        assertEquals("test command", command);
    }

    /*@Test
    public void testShowLine() {
        ui.showLine();
        String expected = "===============================================\n";
        assertEquals(expected, outContent.toString());
    }*/

    /*@Test
    public void testShowError() {
        ui.showError("Test error message");
        String expected = "X Test error message\n";
        assertEquals(expected, outContent.toString());
    }*/

    /*@Test
    public void testShowMessage() {
        ui.showMessage("Test message");
        String expected = "Test message\n";
        assertEquals(expected, outContent.toString());
    }*/

    /*@Test
    public void testShowTaskAdded() {
        Task task = new Todo("Read book");
        ui.showTaskAdded(task, 5);

        String expected = "Got it. I've added this dibo.task:\n" +
                "[T][ ] Read book\n" +
                "Now you have 5 tasks in the list.\n";
        assertEquals(expected, outContent.toString());
    }*/

    /*@Test
    public void testShowTaskRemoved() {
        Task task = new Todo("Read book");
        ui.showTaskRemoved(task, 3);

        String expected = "Noted. I've removed this dibo.task:\n" +
                "[T][ ] Read book\n" +
                "Now you have 3 tasks in the list.\n";
        assertEquals(expected, outContent.toString());
    }*/

    /*@Test
    public void testShowTaskList_emptyList() {
        TaskList tasks = new TaskList();
        ui.showTaskList(tasks);

        String expected = "Your dibo.task list is empty!\n";
        assertEquals(expected, outContent.toString());
    }*/

    /*@Test
    public void testShowTaskList_withTasks() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("Read book"));
        tasks.add(new Todo("Write essay"));

        ui.showTaskList(tasks);

        String expected = "Here are the tasks in your list: \n" +
                "1. [T][ ] Read book\n" +
                "2. [T][ ] Write essay\n";
        assertEquals(expected, outContent.toString());
    }*/

    /*@Test
    public void testShowTaskList_withCompletedTask() {
        TaskList tasks = new TaskList();
        Task todo = new Todo("Read book");
        todo.markAsDone();
        tasks.add(todo);

        ui.showTaskList(tasks);

        String expected = "Here are the tasks in your list: \n" +
                "1. [T][X] Read book\n";
        assertEquals(expected, outContent.toString());
    }*/

    /*@Test
    public void testMultipleOperations() {
        // Test that the output stream accumulates multiple operations correctly
        ui.showMessage("First message");
        ui.showError("Error occurred");
        ui.showLine();

        String expected = "First message\n" +
                "X Error occurred\n" +
                "===============================================\n";
        assertEquals(expected, outContent.toString());
    }*/

    @Test
    public void testUiConstructor() {
        assertNotNull(ui);
        // The scanner is private, but we can test that the object is created successfully
    }
}
