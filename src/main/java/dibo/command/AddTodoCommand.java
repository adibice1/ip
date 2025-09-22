package dibo.command;
import dibo.storage.Storage;
import dibo.task.TaskList;
import dibo.task.Todo;
import dibo.ui.Ui;

/**
 * Represents a command that adds a ToDo task to the task list.
 */
public class AddTodoCommand extends Command {
    private String description;

    /**
     * Creates a new AddTodoCommand with the specified description.
     *
     * @param description The description of the ToDo task.
     */
    public AddTodoCommand(String description) {
        this.description = description;
    }

    /**
     * Executes the command by adding a ToDo task to the given TaskList.
     *
     * @param tasks   The TaskList to add the task to.
     * @param ui      The Ui instance for displaying messages.
     * @param storage The Storage instance for saving tasks.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            Todo todo = Todo.parseTodoInput(description);
            tasks.add(todo);
            ui.showTaskAdded(todo, tasks.size());
            storage.saveTasks(tasks);
        } catch (Exception e) {
            ui.showError(e.getMessage());
        }
    }
}
