package dibo.storage;

import dibo.task.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class StorageTest {

    @TempDir
    Path tempDir;

    @Test
    public void testLoadTasks_fileDoesNotExist() throws IOException {
        Storage storage = new Storage();
        ArrayList<Task> tasks = storage.loadTasks();

        assertTrue(tasks.isEmpty());
    }

    @Test
    public void testSaveAndLoadTasks_todo() throws IOException {
        // Create test data directory
        Path dataDir = Path.of("./data");
        if (!Files.exists(dataDir)) {
            Files.createDirectories(dataDir);
        }

        ArrayList<Task> tasksToSave = new ArrayList<>();
        tasksToSave.add(new Todo("Read book"));
        Todo doneTodo = new Todo("Write essay");
        doneTodo.markAsDone();
        tasksToSave.add(doneTodo);

        // Save tasks
        Storage.saveTasks(tasksToSave);

        // Load tasks
        Storage storage = new Storage();
        ArrayList<Task> loadedTasks = storage.loadTasks();

        assertEquals(2, loadedTasks.size());

        // Verify first todo
        Task todo1 = loadedTasks.get(0);
        assertTrue(todo1 instanceof Todo);
        assertEquals("Read book", todo1.getDescription());
        assertEquals(" ", todo1.getStatusIcon()); // Not done

        // Verify second todo
        Task todo2 = loadedTasks.get(1);
        assertTrue(todo2 instanceof Todo);
        assertEquals("Write essay", todo2.getDescription());
        assertEquals("X", todo2.getStatusIcon()); // Done

        // Clean up
        Files.deleteIfExists(Path.of("./data/dibo.txt"));
    }

    @Test
    public void testSaveAndLoadTasks_deadline() throws IOException {
        // Create test data directory
        Path dataDir = Path.of("./data");
        if (!Files.exists(dataDir)) {
            Files.createDirectories(dataDir);
        }

        ArrayList<Task> tasksToSave = new ArrayList<>();
        LocalDateTime dateTime1 = LocalDateTime.of(2023, 12, 25, 18, 0);
        Deadline deadline1 = new Deadline("Submit report", dateTime1, "2023-12-25 1800");
        tasksToSave.add(deadline1);

        LocalDateTime dateTime2 = LocalDateTime.of(2023, 12, 31, 23, 59);
        Deadline deadline2 = new Deadline("Finish project", dateTime2, "2023-12-31 2359");
        deadline2.markAsDone();
        tasksToSave.add(deadline2);

        // Save tasks
        Storage.saveTasks(tasksToSave);

        // Load tasks
        Storage storage = new Storage();
        ArrayList<Task> loadedTasks = storage.loadTasks();

        assertEquals(2, loadedTasks.size());

        // Verify first deadline
        Task deadline1Loaded = loadedTasks.get(0);
        assertTrue(deadline1Loaded instanceof Deadline);
        assertEquals("Submit report", deadline1Loaded.getDescription());
        assertEquals(" ", deadline1Loaded.getStatusIcon()); // Not done
        assertEquals(dateTime1, ((Deadline) deadline1Loaded).getDateTime());

        // Verify second deadline
        Task deadline2Loaded = loadedTasks.get(1);
        assertTrue(deadline2Loaded instanceof Deadline);
        assertEquals("Finish project", deadline2Loaded.getDescription());
        assertEquals("X", deadline2Loaded.getStatusIcon()); // Done
        assertEquals(dateTime2, ((Deadline) deadline2Loaded).getDateTime());

        // Clean up
        Files.deleteIfExists(Path.of("./data/dibo.txt"));
    }

    @Test
    public void testSaveAndLoadTasks_event() throws IOException {
        // Create test data directory
        Path dataDir = Path.of("./data");
        if (!Files.exists(dataDir)) {
            Files.createDirectories(dataDir);
        }

        ArrayList<Task> tasksToSave = new ArrayList<>();
        LocalDateTime from1 = LocalDateTime.of(2023, 12, 25, 10, 0);
        LocalDateTime to1 = LocalDateTime.of(2023, 12, 25, 12, 0);
        Event event1 = new Event("Team meeting", "2023-12-25 1000", "2023-12-25 1200", from1, to1);
        tasksToSave.add(event1);

        LocalDateTime from2 = LocalDateTime.of(2023, 12, 26, 9, 0);
        LocalDateTime to2 = LocalDateTime.of(2023, 12, 26, 17, 0);
        Event event2 = new Event("Conference", "2023-12-26 0900", "2023-12-26 1700", from2, to2);
        event2.markAsDone();
        tasksToSave.add(event2);

        // Save tasks
        Storage.saveTasks(tasksToSave);

        // Load tasks
        Storage storage = new Storage();
        ArrayList<Task> loadedTasks = storage.loadTasks();

        assertEquals(2, loadedTasks.size());

        // Verify first event
        Task event1Loaded = loadedTasks.get(0);
        assertTrue(event1Loaded instanceof Event);
        assertEquals("Team meeting", event1Loaded.getDescription());
        assertEquals(" ", event1Loaded.getStatusIcon()); // Not done
        assertEquals(from1, ((Event) event1Loaded).getFromDateTime());
        assertEquals(to1, ((Event) event1Loaded).getToDateTime());

        // Verify second event
        Task event2Loaded = loadedTasks.get(1);
        assertTrue(event2Loaded instanceof Event);
        assertEquals("Conference", event2Loaded.getDescription());
        assertEquals("X", event2Loaded.getStatusIcon()); // Done
        assertEquals(from2, ((Event) event2Loaded).getFromDateTime());
        assertEquals(to2, ((Event) event2Loaded).getToDateTime());

        // Clean up
        Files.deleteIfExists(Path.of("./data/dibo.txt"));
    }

    @Test
    public void testSaveAndLoadTasks_mixedTypes() throws IOException {
        // Create test data directory
        Path dataDir = Path.of("./data");
        if (!Files.exists(dataDir)) {
            Files.createDirectories(dataDir);
        }

        ArrayList<Task> tasksToSave = new ArrayList<>();
        tasksToSave.add(new Todo("Read book"));

        LocalDateTime dateTime = LocalDateTime.of(2023, 12, 25, 18, 0);
        tasksToSave.add(new Deadline("Submit report", dateTime, "2023-12-25 1800"));

        LocalDateTime from = LocalDateTime.of(2023, 12, 25, 10, 0);
        LocalDateTime to = LocalDateTime.of(2023, 12, 25, 12, 0);
        tasksToSave.add(new Event("Team meeting", "2023-12-25 1000", "2023-12-25 1200", from, to));

        // Save tasks
        Storage.saveTasks(tasksToSave);

        // Load tasks
        Storage storage = new Storage();
        ArrayList<Task> loadedTasks = storage.loadTasks();

        assertEquals(3, loadedTasks.size());
        assertTrue(loadedTasks.get(0) instanceof Todo);
        assertTrue(loadedTasks.get(1) instanceof Deadline);
        assertTrue(loadedTasks.get(2) instanceof Event);

        // Verify all tasks are not done
        for (Task task : loadedTasks) {
            assertEquals(" ", task.getStatusIcon());
        }

        // Clean up
        Files.deleteIfExists(Path.of("./data/dibo.txt"));
    }

    @Test
    public void testSaveTasks_taskList() throws IOException {
        // Create test data directory
        Path dataDir = Path.of("./data");
        if (!Files.exists(dataDir)) {
            Files.createDirectories(dataDir);
        }

        TaskList taskList = new TaskList();
        taskList.add(new Todo("Read book"));

        LocalDateTime dateTime = LocalDateTime.of(2023, 12, 25, 18, 0);
        taskList.add(new Deadline("Submit report", dateTime, "2023-12-25 1800"));

        Storage.saveTasks(taskList);

        // Verify file was created and has content
        Path filePath = Path.of("./data/dibo.txt");
        assertTrue(Files.exists(filePath));

        String fileContent = Files.readString(filePath);
        assertTrue(fileContent.contains("Read book"));
        assertTrue(fileContent.contains("Submit report"));

        // Clean up
        Files.deleteIfExists(filePath);
    }

    @Test
    public void testSaveTasks_emptyList() throws IOException {
        // Create test data directory
        Path dataDir = Path.of("./data");
        if (!Files.exists(dataDir)) {
            Files.createDirectories(dataDir);
        }

        ArrayList<Task> emptyTasks = new ArrayList<>();
        Storage.saveTasks(emptyTasks);

        // Verify file was created but empty
        Path filePath = Path.of("./data/dibo.txt");
        assertTrue(Files.exists(filePath));

        String fileContent = Files.readString(filePath);
        assertEquals("", fileContent);

        // Clean up
        Files.deleteIfExists(filePath);
    }

    @Test
    public void testLoadTasks_corruptedLine() throws IOException {
        // Create test data directory
        Path dataDir = Path.of("./data");
        if (!Files.exists(dataDir)) {
            Files.createDirectories(dataDir);
        }

        String fileContent = "T | 0 | Valid todo\n" +
                "Invalid line format\n" +
                "T | 1 | Another valid todo\n";

        Path filePath = Path.of("./data/dibo.txt");
        Files.write(filePath, fileContent.getBytes());

        Storage storage = new Storage();
        ArrayList<Task> tasks = storage.loadTasks();

        // Should only load the valid lines
        assertEquals(2, tasks.size());
        assertEquals("Valid todo", tasks.get(0).getDescription());
        assertEquals("Another valid todo", tasks.get(1).getDescription());
        assertEquals("X", tasks.get(1).getStatusIcon()); // Done (1 means done)

        // Clean up
        Files.deleteIfExists(filePath);
    }

    // Test for parseTask method using reflection
    @Test
    public void testParseTaskUsingReflection() throws Exception {
        Method parseTaskMethod = Storage.class.getDeclaredMethod("parseTask", String.class);
        parseTaskMethod.setAccessible(true);

        // Test valid todo
        Task todo = (Task) parseTaskMethod.invoke(null, "T | 0 | Read book");
        assertTrue(todo instanceof Todo);
        assertEquals("Read book", todo.getDescription());
        assertEquals(" ", todo.getStatusIcon()); // Not done

        // Test valid deadline
        Task deadline = (Task) parseTaskMethod.invoke(null, "D | 0 | Submit report | 2023-12-25T18:00");
        assertTrue(deadline instanceof Deadline);
        assertEquals("Submit report", deadline.getDescription());
        assertEquals(" ", deadline.getStatusIcon()); // Not done

        // Test valid event
        Task event = (Task) parseTaskMethod.invoke(null, "E | 0 | Team meeting | 2023-12-25T10:00|2023-12-25T12:00");
        assertTrue(event instanceof Event);
        assertEquals("Team meeting", event.getDescription());
        assertEquals(" ", event.getStatusIcon()); // Not done

        // Test done task
        Task doneTodo = (Task) parseTaskMethod.invoke(null, "T | 1 | Done task");
        assertTrue(doneTodo instanceof Todo);
        assertEquals("Done task", doneTodo.getDescription());
        assertEquals("X", doneTodo.getStatusIcon()); // Done

        // Test invalid format
        Exception exception = assertThrows(Exception.class, () -> {
            parseTaskMethod.invoke(null, "Invalid format");
        });
        assertTrue(exception.getCause() instanceof IllegalArgumentException);
    }

    @Test
    public void testDirectoryCreation() throws IOException {
        // Delete data directory if it exists
        Path dataDir = Path.of("./data");
        if (Files.exists(dataDir)) {
            Files.delete(dataDir);
        }

        // Try to load tasks - should create directory and return empty list
        Storage storage = new Storage();
        ArrayList<Task> tasks = storage.loadTasks();

        assertTrue(tasks.isEmpty());
        assertTrue(Files.exists(dataDir));

        // Clean up
        Files.deleteIfExists(dataDir);
    }

    @Test
    public void testTaskToStringFormat() throws IOException {
        // Create test data directory
        Path dataDir = Path.of("./data");
        if (!Files.exists(dataDir)) {
            Files.createDirectories(dataDir);
        }

        ArrayList<Task> tasksToSave = new ArrayList<>();

        // Test todo
        Todo todo = new Todo("Read book");
        todo.markAsDone();
        tasksToSave.add(todo);

        // Test deadline
        LocalDateTime dateTime = LocalDateTime.of(2023, 12, 25, 18, 0);
        Deadline deadline = new Deadline("Submit report", dateTime, "2023-12-25 1800");
        tasksToSave.add(deadline);

        // Test event
        LocalDateTime from = LocalDateTime.of(2023, 12, 25, 10, 0);
        LocalDateTime to = LocalDateTime.of(2023, 12, 25, 12, 0);
        Event event = new Event("Team meeting", "2023-12-25 1000", "2023-12-25 1200", from, to);
        event.markAsDone();
        tasksToSave.add(event);

        // Save tasks
        Storage.saveTasks(tasksToSave);

        // Load tasks
        Storage storage = new Storage();
        ArrayList<Task> loadedTasks = storage.loadTasks();

        // Verify toString formats
        assertEquals("[T][X] Read book", loadedTasks.get(0).toString());
        assertTrue(loadedTasks.get(1).toString().contains("[D][ ] Submit report"));
        assertTrue(loadedTasks.get(2).toString().contains("[E][X] Team meeting"));

        // Clean up
        Files.deleteIfExists(Path.of("./data/dibo.txt"));
    }
}