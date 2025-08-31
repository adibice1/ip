package dibo.command;
import dibo.storage.Storage;
import dibo.task.TaskList;
import dibo.ui.Ui;

public class MarkCommand extends Command {
    private int index;
    private boolean isMark;

    public MarkCommand(int index, boolean isMark) {
        this.index = index;
        this.isMark = isMark;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            tasks.validateIndex(index);
            if (isMark) {
                tasks.markAsDone(index);
                ui.showMessage("Nice! I've marked this dibo.task as done:");
            } else {
                tasks.markAsUndone(index);
                ui.showMessage("OK, I've marked this dibo.task as not done yet:");
            }
            ui.showMessage(tasks.get(index).toString());
            storage.saveTasks(tasks);
        } catch (Exception e) {
            ui.showError(e.getMessage());
        }
    }
}
