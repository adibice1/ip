package dibo.command;

import dibo.storage.Storage;
import dibo.task.TaskList;
import dibo.ui.Ui;

/**
 * Represents a command that surfaces a parsing error to the user.
 */
public class InvalidCommand extends Command {
    private String errorMessage;

    /**
     * Creates a new InvalidCommand.
     *
     * @param errorMessage errorMessage parameter.
     */
    public InvalidCommand(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Executes this command using the given task list, UI and storage.
     *
     * @param tasks   the task list to operate on
     * @param ui      the UI used to display messages
     * @param storage the storage used to persist changes
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showError(errorMessage);
    }
}
