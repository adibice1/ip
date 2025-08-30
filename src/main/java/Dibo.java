import com.sun.source.util.TaskListener;

import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Dibo {
    private static final String horizontalLine = "===============================================";
    private static ArrayList<Task> todoList;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Load tasks on startup with proper error handling
        try {
            todoList = Storage.loadTasks();
            System.out.println("Tasks loaded successfully from file!");
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
            System.out.println("Starting with an empty task list.");
            todoList = new ArrayList<>();
        }

        System.out.println(horizontalLine);
        System.out.println("Hello! I'm Dibo the Dragon");
        System.out.println("What can I do for you?");
        System.out.println(horizontalLine);

        while (true) {
            String userInput = scanner.nextLine();
            System.out.println(horizontalLine);

            if (userInput.equalsIgnoreCase("bye")) {
                // Save tasks before exiting
                try {
                    Storage.saveTasks(todoList);
                    System.out.println("Tasks saved successfully!");
                } catch (IOException e) {
                    System.out.println("Error saving tasks: " + e.getMessage());
                }
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(horizontalLine);
                break;
            }

            try {
                boolean shouldSave = false;

                if (userInput.equalsIgnoreCase("list")) {
                    handleListCommand(todoList, horizontalLine);
                } else if (userInput.toLowerCase().startsWith("mark")) {
                    handleMarkCommand(userInput, todoList, horizontalLine);
                    shouldSave = true;
                } else if (userInput.toLowerCase().startsWith("unmark")) {
                    handleUnmarkCommand(userInput, todoList, horizontalLine);
                    shouldSave = true;
                } else if (userInput.toLowerCase().startsWith("deadline")) {
                    handleDeadlineCommand(userInput, todoList, horizontalLine);
                    shouldSave = true;
                } else if (userInput.toLowerCase().startsWith("event")) {
                    handleEventCommand(userInput, todoList, horizontalLine);
                    shouldSave = true;
                } else if (userInput.toLowerCase().startsWith("todo")) {
                    handleTodoCommand(userInput, todoList, horizontalLine);
                    shouldSave = true;
                } else if (userInput.toLowerCase().contains("meow")) {
                    System.out.println("meowmeowmeow");
                    System.out.println(horizontalLine);
                } else if (userInput.toLowerCase().startsWith("delete")) {
                    handleDeleteCommand(userInput, todoList, horizontalLine);
                    shouldSave = true;
                } else if (userInput.equalsIgnoreCase("save")) {
                    // Manual save command
                    Storage.saveTasks(todoList);
                    System.out.println("Tasks saved manually!");
                    System.out.println(horizontalLine);
                } else if (userInput.toLowerCase().startsWith("find date")) {
                    handleFindByDateCommand(userInput, todoList, horizontalLine);
                }
                else {
                    // For any unrecognized commands
                    System.out.println("OOPS!!! I'm sorry, but I don't know what that means :-(");
                    System.out.println(horizontalLine);
                }

                if (shouldSave) {
                    try {
                        Storage.saveTasks(todoList);
                    } catch (IOException e) {
                        System.out.println("Warning: Could not save tasks: " + e.getMessage());
                    }
                }

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.println(horizontalLine);
            } catch (Exception e) {
                System.out.println("OOPS!!! Something went wrong: " + e.getMessage());
                System.out.println(horizontalLine);
            }
        }
    }

    private static void handleListCommand(ArrayList<Task> todoList, String horizontalLine) {
        if (todoList.isEmpty()) {
            System.out.println("Your task list is empty!");
        } else {
            for (int i = 0; i < todoList.size(); i++) {
                System.out.println((i + 1) + ". " + todoList.get(i));
            }
        }
        System.out.println(horizontalLine);
    }

    private static void handleMarkCommand(String userInput, ArrayList<Task> todoList, String horizontalLine) {
        String numberStr = userInput.substring(4).trim();
        if (numberStr.isEmpty()) {
            throw new IllegalArgumentException("Please specify a task number to mark.");
        }

        int taskNumber = Integer.parseInt(numberStr);
        validateTaskNumber(taskNumber, todoList);

        Task task = todoList.get(taskNumber - 1);
        task.markAsDone();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(task);
        System.out.println(horizontalLine);
    }

    private static void handleUnmarkCommand(String userInput, ArrayList<Task> todoList, String horizontalLine) {
        String numberStr = userInput.substring(7).trim();
        if (numberStr.isEmpty()) {
            throw new IllegalArgumentException("Please specify a task number to unmark.");
        }

        int taskNumber = Integer.parseInt(numberStr);
        validateTaskNumber(taskNumber, todoList);

        Task task = todoList.get(taskNumber - 1);
        task.markAsUndone();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(task);
        System.out.println(horizontalLine);
    }

    private static void handleDeadlineCommand(String userInput, ArrayList<Task> todoList, String horizontalLine) {
        Deadline deadline = Deadline.parseDeadlineInput(userInput);
        todoList.add(deadline);
        System.out.println("Got it. I've added this task:");
        System.out.println(deadline);
        System.out.println("Now you have " + todoList.size() + " tasks in the list.");
        System.out.println(horizontalLine);
    }

    private static void handleEventCommand(String userInput, ArrayList<Task> todoList, String horizontalLine) {
        Event event = Event.parseEventInput(userInput);
        todoList.add(event);
        System.out.println("Got it. I've added this task:");
        System.out.println(event);
        System.out.println("Now you have " + todoList.size() + " tasks in the list.");
        System.out.println(horizontalLine);
    }

    private static void handleTodoCommand(String userInput, ArrayList<Task> todoList, String horizontalLine) {
        Todo todo = Todo.parseTodoInput(userInput);
        todoList.add(todo);
        System.out.println("Got it. I've added this task:");
        System.out.println(todo);
        System.out.println("Now you have " + todoList.size() + " tasks in the list.");
        System.out.println(horizontalLine);
    }

    private static void validateTaskNumber(int taskNumber, ArrayList<Task> todoList) {
        if (taskNumber < 1 || taskNumber > todoList.size()) {
            throw new IllegalArgumentException("Invalid task number. Please choose between 1 and " + todoList.size());
        }
    }

    private static void handleDeleteCommand(String userInput, ArrayList<Task> todoList, String horizontalLine) {
        String numberStr = userInput.substring(7).trim();
        if (numberStr.isEmpty()) {
            throw new IllegalArgumentException("Please specify a task number to unmark.");
        }

        int taskNumber = Integer.parseInt(numberStr);
        validateTaskNumber(taskNumber, todoList);

        Task task = todoList.get(taskNumber - 1);
        todoList.remove(taskNumber - 1);
        System.out.println("Noted. I've removed this task:");
        System.out.println(task);
        System.out.println("Now you have " + todoList.size() + " tasks in the list.");
        System.out.println(horizontalLine);
    }

    private static void handleFindByDateCommand(String userInput, ArrayList<Task> todoList, String horizontalLine) {
        String dateStr = userInput.substring(9).trim();
        if (dateStr.isEmpty()) {
            throw new IllegalArgumentException("Please specify a date to search for.");
        }

        try {
            LocalDate searchDate = parseDate(dateStr);
            List<Task> matchingTasks = new ArrayList<>();

            for (Task task : todoList) {
                if (task instanceof Deadline) {
                    Deadline deadline = (Deadline) task;
                    if (deadline.getDateTime().toLocalDate().equals(searchDate)) {
                        matchingTasks.add(task);
                    }
                } else if (task instanceof Event) {
                    Event event = (Event) task;
                }
            }

            if (matchingTasks.isEmpty()) {
                System.out.println("No tasks found for " +
                        searchDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy")));
            } else {
                System.out.println("Tasks on " +
                        searchDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ":");
                for (int i = 0; i < matchingTasks.size(); i++) {
                    System.out.println((i + 1) + ". " + matchingTasks.get(i));
                }
            }
            System.out.println(horizontalLine);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use formats like: 2019-12-02, 02/12/2019, Dec 02 2019");
        }
    }

    private static LocalDate parseDate(String dateString) {
        List<String> patterns = Arrays.asList(
                "yyyy-MM-dd", "dd/MM/yyyy", "MM/dd/yyyy", "dd-MM-yyyy",
                "MMM dd yyyy", "dd MMM yyyy"
        );
        for (String pattern : patterns) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
                return LocalDate.parse(dateString, formatter);
            } catch (DateTimeParseException e) {
                continue;
            }
        }
        throw new DateTimeParseException("Unsupported date format", dateString, 0);
    }
}
