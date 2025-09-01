package dibo.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class TaskListTest {

    @Test
    public void testTaskListCreation() {
        TaskList taskList = new TaskList();
        assertEquals(0, taskList.size());
        assertTrue(taskList.isEmpty());
    }

    @Test
    public void testTaskListCreationWithTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Todo("Task 1"));
        tasks.add(new Todo("Task 2"));

        TaskList taskList = new TaskList(tasks);
        assertEquals(2, taskList.size());
        assertFalse(taskList.isEmpty());
    }

    @Test
    public void testAddTask() {
        TaskList taskList = new TaskList();
        Todo todo = new Todo("Read book");

        taskList.add(todo);
        assertEquals(1, taskList.size());
        assertEquals(todo, taskList.get(0));
    }

    @Test
    public void testRemoveTask() {
        TaskList taskList = new TaskList();
        Todo todo1 = new Todo("Read book");
        Todo todo2 = new Todo("Write essay");

        taskList.add(todo1);
        taskList.add(todo2);

        Task removed = taskList.remove(0);
        assertEquals(1, taskList.size());
        assertEquals(todo1, removed);
        assertEquals(todo2, taskList.get(0));
    }

    @Test
    public void testMarkAsDone() {
        TaskList taskList = new TaskList();
        Todo todo = new Todo("Read book");
        taskList.add(todo);

        taskList.markAsDone(0);
        assertTrue(todo.isDone);
    }

    @Test
    public void testMarkAsUndone() {
        TaskList taskList = new TaskList();
        Todo todo = new Todo("Read book");
        todo.markAsDone();
        taskList.add(todo);

        taskList.markAsUndone(0);
        assertFalse(todo.isDone);
    }

    @Test
    public void testGetAllTasks() {
        TaskList taskList = new TaskList();
        Todo todo1 = new Todo("Read book");
        Todo todo2 = new Todo("Write essay");

        taskList.add(todo1);
        taskList.add(todo2);

        ArrayList<Task> tasks = taskList.getAllTasks();
        assertEquals(2, tasks.size());
        assertEquals(todo1, tasks.get(0));
        assertEquals(todo2, tasks.get(1));
    }

    @Test
    public void testValidateIndex_valid() {
        TaskList taskList = new TaskList();
        taskList.add(new Todo("Task 1"));

        assertDoesNotThrow(() -> taskList.validateIndex(0));
    }

    @Test
    public void testValidateIndex_invalid() {
        TaskList taskList = new TaskList();
        taskList.add(new Todo("Task 1"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            taskList.validateIndex(1);
        });

        assertEquals("Invalid task number. Please choose between 1 and 1", exception.getMessage());
    }

    @Test
    public void testValidateIndex_negative() {
        TaskList taskList = new TaskList();
        taskList.add(new Todo("Task 1"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            taskList.validateIndex(-1);
        });

        assertEquals("Invalid task number. Please choose between 1 and 1", exception.getMessage());
    }
}