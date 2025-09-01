package dibo.command;

import dibo.storage.Storage;
import dibo.task.Deadline;
import dibo.task.Task;
import dibo.task.TaskList;
import dibo.ui.Ui;

import java.util.ArrayList;
import java.util.List;

public class FindCommand extends Command {
    private String searchTerm;

    public FindCommand(String searchTerm) {
        this.searchTerm = searchTerm;
    }

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
