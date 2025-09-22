package dibo.command;
import dibo.storage.Storage;
import dibo.task.TaskList;
import dibo.ui.Ui;

/**
 * Represents a command that displays the current list of tasks.
 */
public class ListCommand extends Command {

    /**
     * Executes this command using the given task list, UI and storage.
     *
     * @param tasks   the task list to operate on
     * @param ui      the UI used to display messages
     * @param storage the storage used to persist changes
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showTaskList(tasks);
    }
}

