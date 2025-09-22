package dibo.command;

import dibo.storage.Storage;
import dibo.task.Deadline;
import dibo.task.Task;
import dibo.task.TaskList;
import dibo.ui.Ui;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a command that searches for tasks matching a keyword.
 */
public class FindCommand extends Command {
    private String searchTerm;

    /**
     * Creates a new FindCommand.
     *
     * @param searchTerm searchTerm parameter.
     */
    public FindCommand(String searchTerm) {
        this.searchTerm = searchTerm;
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
            List<Task> matchingTasks = findTasksByTerm(tasks, searchTerm);
            ui.showSearchResults(matchingTasks, searchTerm);
        } catch (Exception e) {
            ui.showError("Error searching word: " + e.getMessage());
        }
    }

    private List<Task> findTasksByTerm(TaskList tasks, String searchTerm) {
        List<Task> matchingTasks = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.getDescription().toLowerCase().contains(searchTerm.toLowerCase())) {
                matchingTasks.add(task);
            }
        }
        return matchingTasks;
    }
}
