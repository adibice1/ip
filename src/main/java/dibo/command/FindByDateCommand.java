package dibo.command;

import dibo.storage.Storage;
import dibo.task.Deadline;
import dibo.task.Event;
import dibo.task.Task;
import dibo.task.TaskList;
import dibo.ui.Ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindByDateCommand extends Command {
    private String dateString;

    public FindByDateCommand(String dateString) {
        this.dateString = dateString;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            LocalDate searchDate = parseDate(dateString);
            List<Task> matchingTasks = findTasksByDate(tasks, searchDate);
            displayResults(ui, matchingTasks, searchDate);
        } catch (DateTimeParseException e) {
            ui.showError("Invalid date format. Please use formats like: 2019-12-02, 02/12/2019, Dec 02 2019");
        } catch (Exception e) {
            ui.showError("Error searching tasks: " + e.getMessage());
        }
    }

    private static LocalDate parseDate(String dateString) {
        List<String> patterns = Arrays.asList(
                "yyyy-MM-dd", "dd/MM/yyyy", "MM/dd/yyyy", "dd-MM-yyyy",
                "MMM dd yyyy", "dd MMM yyyy"
        );
        for (String pattern : patterns) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
                return LocalDate.parse(dateString, formatter);
            } catch (DateTimeParseException e) {
                continue;
            }
        }
        throw new DateTimeParseException("Unsupported date format", dateString, 0);
    }

    private List<Task> findTasksByDate(TaskList tasks, LocalDate searchDate) {
        List<Task> matchingTasks = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task instanceof Deadline) {
                Deadline deadline = (Deadline) task;
                if (deadline.getDateTime().toLocalDate().equals(searchDate)) {
                    matchingTasks.add(task);
                }
            } else if (task instanceof Event) {
                Event event = (Event) task;
                LocalDate eventFromDate = event.getFromDateTime().toLocalDate();
                LocalDate eventToDate = event.getToDateTime().toLocalDate();
                if (!searchDate.isBefore(eventFromDate) && !searchDate.isAfter(eventToDate)) {
                    matchingTasks.add(task);
                }
            }
        }
        return matchingTasks;
    }

    private void displayResults(Ui ui, List<Task> matchingTasks, LocalDate searchDate) {
        String formattedDate = searchDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
        if (matchingTasks.isEmpty()) {
            ui.showMessage("No tasks found for " + formattedDate);
        } else {
            ui.showMessage("Tasks on " + formattedDate + ":");
            for (int i = 0; i < matchingTasks.size(); i++) {
                ui.showMessage((i + 1) + ". " + matchingTasks.get(i));
            }
        }
    }
}
