package dibo.command;
import dibo.storage.Storage;
import dibo.task.TaskList;
import dibo.ui.Ui;

/**
 * Represents a command that saves tasks and exits the application.
 */
public class ExitCommand extends Command {

    /**
     * Executes this command using the given task list, UI and storage.
     *
     * @param tasks   the task list to operate on
     * @param ui      the UI used to display messages
     * @param storage the storage used to persist changes
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            storage.saveTasks(tasks);
            ui.showGoodbye();
        } catch (Exception e) {
            ui.showError("Failed to save tasks: " + e.getMessage());
        }
    }

    /**
     * Indicates whether this command should terminate the application.
     *
     * @return true if the application should exit after this command, otherwise false
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
