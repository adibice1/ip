package dibo.command;
import dibo.storage.Storage;
import dibo.task.Task;
import dibo.task.TaskList;
import dibo.ui.Ui;

/**
 * Represents a command that deletes a task by its index.
 */
public class DeleteCommand extends Command {
    private int index;

    /**
     * Creates a new DeleteCommand.
     *
     * @param index index parameter.
     */
    public DeleteCommand(int index) {
        this.index = index;
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
        try {
            tasks.validateIndex(index);
            Task removedTask = tasks.remove(index);
            ui.showTaskRemoved(removedTask, tasks.size());
            storage.saveTasks(tasks);
        } catch (Exception e) {
            ui.showError(e.getMessage());
        }
    }
}

