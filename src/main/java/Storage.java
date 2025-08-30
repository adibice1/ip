import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private static final String FILE_PATH = "./data/dibo.txt";
    private static final String DIRECTORY_PATH = "./data/";

    /**
     * Loads tasks from the file storage
     * @return ArrayList of tasks
     * @throws IOException if there's an error reading the file
     */
    public ArrayList<Task> loadTasks() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        Path path = Paths.get(FILE_PATH);

        // Check if file exists
        if (!Files.exists(path)) {
            // Create directory if it doesn't exist
            Files.createDirectories(Paths.get(DIRECTORY_PATH));
            return tasks; // Return empty list if file doesn't exist
        }

        List<String> lines = Files.readAllLines(path);

        for (String line : lines) {
            try {
                Task task = parseTask(line);
                if (task != null) {
                    tasks.add(task);
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Warning: Skipping corrupted line: " + line);
                System.out.println("Error: " + e.getMessage());
            }
        }

        return tasks;
    }

    /**
     * Saves tasks to the file storage
     * @param tasks ArrayList of tasks to save
     * @throws IOException if there's an error writing to the file
     */
    public static void saveTasks(ArrayList<Task> tasks) throws IOException {
        // Create directory if it doesn't exist
        Files.createDirectories(Paths.get(DIRECTORY_PATH));

        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            for (Task task : tasks) {
                writer.write(task.toFileFormat() + System.lineSeparator());
            }
        }
    }

    public static void saveTasks(TaskList tasks) throws IOException {
        saveTasks(tasks.getAllTasks());
    }

    /**
     * Parses a line from the file into a Task object
     * @param line The line to parse
     * @return Task object
     * @throws IllegalArgumentException if the line format is invalid
     */
    private static Task parseTask(String line) throws IllegalArgumentException {
        String[] parts = line.split(" \\| ");

        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid task format: " + line);
        }

        String type = parts[0].trim();
        String isDoneStr = parts[1].trim();
        String description = parts[2].trim();

        // Validate isDone field
        if (!isDoneStr.equals("0") && !isDoneStr.equals("1")) {
            throw new IllegalArgumentException("Invalid completion status: " + isDoneStr);
        }
        boolean isDone = isDoneStr.equals("1");

        try {
            switch (type) {
                case "T":
                    Todo todo = new Todo(description);
                    if (isDone) todo.markAsDone();
                    return todo;

                case "D":
                    if (parts.length < 4) {
                        throw new IllegalArgumentException("Deadline task missing time information: " + line);
                    }
                    String dateTimeStr = parts[3].trim();
                    try {
                        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                        Deadline deadline = new Deadline(description, dateTime, dateTimeStr);
                        if (isDone) deadline.markAsDone();
                        return deadline;
                    } catch (DateTimeParseException e) {
                        throw new IllegalArgumentException("Invalid date-time format in file: " + dateTimeStr);
                    }

                case "E":
                    if (parts.length < 4) {
                        throw new IllegalArgumentException("Event task missing time information: " + line);
                    }
                    String timeInfo = parts[3].trim();

                    // Only support the new format with pipe separator
                    if (!timeInfo.contains("|")) {
                        throw new IllegalArgumentException("Event task has invalid time format. Expected 'from|to': " + line);
                    }

                    String[] timeParts = timeInfo.split("\\|", 2);
                    String from = timeParts[0].trim();
                    String to = timeParts.length > 1 ? timeParts[1].trim() : from; // Use from as default if to is missing

                    try {
                        LocalDateTime fromDateTime = LocalDateTime.parse(from, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                        LocalDateTime toDateTime = LocalDateTime.parse(to, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                        Event event = new Event(description, from, to, fromDateTime, toDateTime);
                        if (isDone) event.markAsDone();
                        return event;
                    } catch (DateTimeParseException e) {
                        throw new IllegalArgumentException("Invalid date-time format in file: " + timeInfo);
                    }

                default:
                    throw new IllegalArgumentException("Unknown task type: " + type);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse task: " + e.getMessage(), e);
        }
    }
}