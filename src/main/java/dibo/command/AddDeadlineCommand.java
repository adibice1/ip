package dibo.command;
import dibo.storage.Storage;
import dibo.task.Deadline;
import dibo.task.Task;
import dibo.task.TaskList;
import dibo.ui.Ui;

public class AddDeadlineCommand extends Command {
    private String userInput;

    public AddDeadlineCommand(String userInput) {
        this.userInput = userInput;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            Deadline deadline = Deadline.parseDeadlineInput(userInput);
            tasks.add(deadline);
            ui.showTaskAdded(deadline, tasks.size());
            storage.saveTasks(tasks);
        } catch (Exception e) {
            ui.showError(e.getMessage());
        }
    }
}
