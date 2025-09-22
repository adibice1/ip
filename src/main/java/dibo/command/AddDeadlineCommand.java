package dibo.command;
import dibo.storage.Storage;
import dibo.task.Deadline;
import dibo.task.Task;
import dibo.task.TaskList;
import dibo.ui.Ui;

/**
 * Represents a command that adds a Deadline task parsed from user input.
 */
public class AddDeadlineCommand extends Command {
    private String userInput;

    /**
     * Creates a new AddDeadlineCommand.
     *
     * @param userInput userInput parameter.
     */
    public AddDeadlineCommand(String userInput) {
        this.userInput = userInput;
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
            Deadline deadline = Deadline.parseDeadlineInput(userInput);
            tasks.add(deadline);
            ui.showTaskAdded(deadline, tasks.size());
            storage.saveTasks(tasks);
        } catch (Exception e) {
            ui.showError(e.getMessage());
        }
    }
}
