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
