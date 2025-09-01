package dibo.ui;

import dibo.task.Task;
import dibo.task.TaskList;

import java.util.List;
import java.util.Scanner;

public class Ui {
    private Scanner scanner;
    private static final String horizontalLine = "===============================================";

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        System.out.println(horizontalLine);
        System.out.println("Hello! I'm Dibo the Dragon");
        System.out.println("What can I do for you?");
        System.out.println(horizontalLine);
    }

    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
        // System.out.println(horizontalLine);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showLine() {
        System.out.println(horizontalLine);
    }

    public void showError(String message){
        System.out.println("❌ " + message);
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showTaskAdded(Task task, int totalTasks) {
        System.out.println("Got it. I've added this task:");
        System.out.println(task);
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
    }

    public void showTaskRemoved(Task task, int totalTasks) {
        System.out.println("Noted. I've removed this task:");
        System.out.println(task);
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
    }

    public void showTaskList(TaskList tasks) {
        if (tasks.isEmpty()) {
            System.out.println("Your task list is empty!");
        } else {
            System.out.println("Here are the tasks in your list: ");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
    }

    public void showSearchResults(List<Task> matchingTasks, String searchTerm) {
        System.out.println("Here are the matching tasks in your list:");
        for (int i = 0; i < matchingTasks.size(); i++) {
            System.out.println((i + 1) + "." + matchingTasks.get(i));
        }
    }
}
