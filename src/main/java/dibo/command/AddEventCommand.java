package dibo.command;

import dibo.storage.Storage;
import dibo.task.Event;
import dibo.task.TaskList;
import dibo.ui.Ui;

/**
 * Represents a command that adds an Event task parsed from user input.
 */
public class AddEventCommand extends Command {
    private String userInput;

    /**
     * Creates a new AddEventCommand.
     *
     * @param userInput userInput parameter.
     */
    public AddEventCommand(String userInput) {
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
            Event event = Event.parseEventInput(userInput);
            tasks.add(event);
            ui.showTaskAdded(event, tasks.size());
            storage.saveTasks(tasks);
        } catch (Exception e) {
            ui.showError(e.getMessage());
        }
    }
}
