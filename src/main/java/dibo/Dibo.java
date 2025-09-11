package dibo;
import dibo.command.Command;
import dibo.storage.Storage;
import dibo.task.TaskList;
import dibo.ui.Ui;
import dibo.parser.Parser;

public class Dibo {
    private Ui ui;
    private Storage storage;
    private TaskList tasks;
    private Parser parser;

    public Dibo() {
        this.ui = new Ui();
        this.storage = new Storage();
        this.parser = new Parser();
        this.tasks = loadTasks();
    }

    private TaskList loadTasks() {
        try {
            return new TaskList(storage.loadTasks());
        } catch (Exception e) {
            ui.showError("Failed to load tasks: " + e.getMessage());
            return new TaskList();
        }
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        try {

            Command command = parser.parse(input);
            command.execute(tasks, ui, storage);
            boolean isExit = command.isExit();

            return ui.returnOutput();
        } catch (Exception e) {
            return "Something went wrong: " + e.getMessage();
        }
    }


    /*public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();

                Command command = Parser.parse(fullCommand);
                command.execute(tasks, ui, storage);
                isExit = command.isExit();
            } catch (Exception e) {
                ui.showError("Something went wrong: " + e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    public static void main(String[] args) {
        new Dibo().run();
    }*/
}
