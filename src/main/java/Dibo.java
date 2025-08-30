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


    public void run() {
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
    }
}
