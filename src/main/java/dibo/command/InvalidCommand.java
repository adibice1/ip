package dibo.command;

import dibo.storage.Storage;
import dibo.task.TaskList;
import dibo.ui.Ui;

public class InvalidCommand extends Command {
    private String errorMessage;

    public InvalidCommand(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showError(errorMessage);
    }
}
