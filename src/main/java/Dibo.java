import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

public class Dibo {
    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        Scanner scanner = new Scanner(System.in);
        String horizontalLine = "===============================================";
        ArrayList<Task> todoList = new ArrayList<>();


        System.out.println(horizontalLine);
        System.out.println("Hello! I'm Dibo the Dragon\n");
        System.out.println("What can I do for you?\n");
        System.out.println(horizontalLine);

        while (true) {
            String userInput = scanner.nextLine();
            System.out.println(horizontalLine);

            if (userInput.equalsIgnoreCase("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(horizontalLine);
                break;
            }

            if (userInput.equalsIgnoreCase("list")) {
                for (int i = 0; i < todoList.size(); i++) {
                    if (!todoList.get(i).getDescription().equalsIgnoreCase("list") || !todoList.get(i).getDescription().contains("mark") || !todoList.get(i).getDescription().contains("unmark")) {
                        System.out.println((i + 1) + ". " + todoList.get(i));
                    }
                }
                System.out.println(horizontalLine);
            } else if (userInput.toLowerCase().startsWith("mark ")) {
                int taskNumber = Integer.parseInt(userInput.substring(5).trim());
                Task task = todoList.get(taskNumber - 1);
                task.markAsDone();
                System.out.println("Nice! I've marked this task as done:");
                System.out.println(task);
                System.out.println(horizontalLine);
            } else if (userInput.toLowerCase().startsWith("unmark ")) {
                int taskNumber = Integer.parseInt(userInput.substring(7).trim());
                Task task = todoList.get(taskNumber - 1);
                task.markAsUndone();
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println(task);
                System.out.println(horizontalLine);
            } else if (userInput.toLowerCase().startsWith("deadline ")) {
                Deadline deadline = Deadline.parseDeadlineInput(userInput);
                todoList.add(deadline);
                System.out.println("Got it. I've added this task:");
                System.out.println(deadline);
                System.out.println("Now you have " + todoList.size() + " tasks in the list.");
                System.out.println(horizontalLine);
            } else if (userInput.toLowerCase().startsWith("event ")) {
                Event event = Event.parseEventInput(userInput);
                todoList.add(event);
                System.out.println("Got it. I've added this task:");
                System.out.println(event);
                System.out.println("Now you have " + todoList.size() + " tasks in the list.");
                System.out.println(horizontalLine);
            } else if (userInput.toLowerCase().startsWith("todo ")) {
                Todo todo = Todo.parseTodoInput(userInput);
                todoList.add(todo);
                System.out.println("Got it. I've added this task:");
                System.out.println(todo);
                System.out.println("Now you have " + todoList.size() + " tasks in the list.");
                System.out.println(horizontalLine);
            }
            else {
                Task t = new Task(userInput);
                todoList.add(t);
                System.out.println("added: " + userInput);
                System.out.println(horizontalLine);
            }
        }
    }
}

