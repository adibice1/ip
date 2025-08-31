package dibo.command;

import dibo.storage.Storage;
import dibo.task.Event;
import dibo.task.TaskList;
import dibo.ui.Ui;

public class AddEventCommand extends Command {
    private String userInput;

    public AddEventCommand(String userInput) {
        this.userInput = userInput;
    }

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
