package dibo.command;
import dibo.storage.Storage;
import dibo.task.TaskList;
import dibo.ui.Ui;

/**
 * Represents a command that marks or unmarks a task as done.
 */
public class MarkCommand extends Command {
    private int index;
    private boolean isMark;

    /**
     * Creates a new MarkCommand.
     *
     * @param index index parameter.
     * @param isMark isMark parameter.
     */
    public MarkCommand(int index, boolean isMark) {
        this.index = index;
        this.isMark = isMark;
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
            StringBuilder sb = new StringBuilder();
            if (isMark) {
                tasks.markAsDone(index);
                sb.append("Nice! I've marked this task as done:").append(System.lineSeparator());
            } else {
                tasks.markAsUndone(index);
                sb.append("OK, I've marked this task as not done yet:").append(System.lineSeparator());
            }
            sb.append(tasks.get(index).toString());

            ui.showMessage(sb.toString());   // single call
            storage.saveTasks(tasks);
        } catch (Exception e) {
            ui.showError(e.getMessage());
        }
    }
}
