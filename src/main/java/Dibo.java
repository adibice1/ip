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
        ArrayList<String> todoList = new ArrayList<>();


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
            todoList.add(userInput);
            if (userInput.equalsIgnoreCase("list")) {
                for (int i = 0; i < todoList.size(); i++) {
                    if (!todoList.get(i).equalsIgnoreCase("list")) {
                        System.out.println((i + 1) + ". " + todoList.get(i));
                    }
                }
                System.out.println(horizontalLine);
            } else {
                System.out.println("added: " + userInput);
                System.out.println(horizontalLine);
            }
        }
    }
}
