package dibo.command;
import dibo.storage.Storage;
import dibo.task.TaskList;
import dibo.ui.Ui;

/**
 * Base type for all user commands in Dibo. Subclasses implement specific actions.
 */
public abstract class Command {

    /**
     * Executes this command using the given task list, UI and storage.
     *
     * @param tasks   the task list to operate on
     * @param ui      the UI used to display messages
     * @param storage the storage used to persist changes
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage);

    /**
     * Indicates whether this command should terminate the application.
     *
     * @return true if the application should exit after this command, otherwise false
     */
    public boolean isExit() {
        return false;
    }
}
