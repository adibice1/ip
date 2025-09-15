package dibo.storage;

import dibo.task.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
     * Parses a line from the file into a dibo.task.Task object.
     * Applies SLAP: high-level flow here; details in small helpers.
     *
     * @param line The line to parse
     * @return dibo.task.Task object
     * @throws IllegalArgumentException if the line format is invalid
     */
    public static Task parseTask(String line) throws IllegalArgumentException {
        try {
            String[] parts = splitLine(line);                 // validates min length
            String type = parts[0].trim();
            boolean isDone = parseIsDone(parts[1].trim());    // validates & converts
            String description = parts[2].trim();

            Task task = switch (type) {
                case "T" -> buildTodo(description);
                case "D" -> buildDeadline(parts, description);
                case "E" -> buildEvent(parts, description);
                default -> throw new IllegalArgumentException("Unknown task type: " + type);
            };

            if (isDone) {
                task.markAsDone();
            }
            return task;

        } catch (IllegalArgumentException e) {
            // preserve original message; add uniform context
            throw new IllegalArgumentException("Failed to parse task: " + e.getMessage(), e);
        }
    }

    private static String[] splitLine(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid task format: " + line);
        }
        return parts;
    }

    private static boolean parseIsDone(String isDoneStr) {
        if (!"0".equals(isDoneStr) && !"1".equals(isDoneStr)) {
            throw new IllegalArgumentException("Invalid completion status: " + isDoneStr);
        }
        return "1".equals(isDoneStr);
    }

    private static Todo buildTodo(String description) {
        return new Todo(description);
    }

    private static Deadline buildDeadline(String[] parts, String description) {
        if (parts.length < 4) {
            throw new IllegalArgumentException("Deadline task missing time information");
        }
        String dateTimeStr = parts[3].trim();
        LocalDateTime dateTime = parseIsoDateTime(dateTimeStr, "Invalid date-time format in file: " + dateTimeStr);
        return new Deadline(description, dateTime, dateTimeStr);
    }

    private static Event buildEvent(String[] parts, String description) {
        if (parts.length < 4) {
            throw new IllegalArgumentException("Event task missing time information");
        }
        String timeInfo = parts[3].trim();

        // Only support the new format with pipe separator: "from|to"
        if (!timeInfo.contains("|")) {
            throw new IllegalArgumentException("Event task has invalid time format. Expected 'from|to': " + timeInfo);
        }

        String[] timeParts = timeInfo.split("\\|", 2);
        String fromRaw = timeParts[0].trim();
        String toRaw   = (timeParts.length > 1 ? timeParts[1] : fromRaw).trim(); // default to 'from' if missing

        LocalDateTime fromDateTime = parseIsoDateTime(fromRaw, "Invalid date-time format in file: " + fromRaw);
        LocalDateTime toDateTime   = parseIsoDateTime(toRaw,   "Invalid date-time format in file: " + toRaw);

        // Keep your constructor parameter order exactly as used in your codebase:
        // Event(description, from, to, fromDateTime, toDateTime)
        return new Event(description, fromRaw, toRaw, fromDateTime, toDateTime);
    }

    private static LocalDateTime parseIsoDateTime(String value, String errorMessage) {
        try {
            return LocalDateTime.parse(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

}