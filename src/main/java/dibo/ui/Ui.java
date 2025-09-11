package dibo.ui;

import dibo.task.Task;
import dibo.task.TaskList;

import java.util.List;
import java.util.Scanner;

public class Ui {
    private Scanner scanner;
    private static final String horizontalLine = "=================================";
    private String output;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        this.output = horizontalLine +
                System.lineSeparator() +
                "Hello! I'm Dibo the Dragon" +
                System.lineSeparator() +
                "What can I do for you?" +
                System.lineSeparator() +
                horizontalLine;
    }

    public void showGoodbye() {
        this.output = "Bye. Hope to see you again soon!" + System.lineSeparator();
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showLine() {
        this.output = horizontalLine;
    }

    public void showError(String message){
        this.output = "X " + message;
    }

    public void showMessage(String message) {
        this.output = message;
    }

    public void showTaskAdded(Task task, int totalTasks) {
        this.output = horizontalLine +
                System.lineSeparator() +
                "Got it. I've added this task:" +
                System.lineSeparator() +
                task +
                System.lineSeparator() +
                "Now you have " + totalTasks + " tasks in the list." +
                System.lineSeparator() +
                horizontalLine;
    }

    public void showTaskRemoved(Task task, int totalTasks) {
        this.output = horizontalLine +
                System.lineSeparator() +
                "Noted. I've removed this task:" +
                System.lineSeparator() +
                task +
                System.lineSeparator() +
                "Now you have " + totalTasks + " tasks in the list." +
                System.lineSeparator() +
                horizontalLine;
    }

    public void showTaskList(TaskList tasks) {
        if (tasks.isEmpty()) {
            this.output = "Your task list is empty!" + System.lineSeparator();
        } else {
            this.output = "Here are the tasks in your list: " + System.lineSeparator();
            for (int i = 0; i < tasks.size(); i++) {
                this.output = output + (i + 1) + ". " + tasks.get(i).toString() + System.lineSeparator();
            }
        }
    }

    public void showSearchResults(List<Task> matchingTasks, String searchTerm) {
        this.output = "Here are the matching tasks in your list:" + System.lineSeparator();
        for (int i = 0; i < matchingTasks.size(); i++) {
            this.output = output + (i + 1) + ". " + matchingTasks.get(i).toString() + System.lineSeparator();
        }
    }

    public String returnOutput() {
        return output;
    }
}
