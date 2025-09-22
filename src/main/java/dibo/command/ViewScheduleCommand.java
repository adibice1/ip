package dibo.command;

import dibo.storage.Storage;
import dibo.task.Task;
import dibo.task.TaskList;
import dibo.ui.Ui;
import dibo.task.Deadline;
import dibo.task.Event;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class ViewScheduleCommand extends Command {
    private String dateString;

    public ViewScheduleCommand(String dateString) {
        this.dateString = dateString;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            LocalDate scheduleDate = parseDate(dateString);
            List<Task> scheduleTasks = getTasksForDate(tasks, scheduleDate);
            displaySchedule(ui, scheduleTasks, scheduleDate);
        } catch (DateTimeParseException e) {
            ui.showError("Invalid date format. Please use formats like: 2019-12-02, 02/12/2019, Dec 02 2019");
        } catch (Exception e) {
            ui.showError("Error viewing schedule: " + e.getMessage());
        }
    }

    private LocalDate parseDate(String dateString) {
        List<String> patterns = List.of(
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

    private List<Task> getTasksForDate(TaskList tasks, LocalDate scheduleDate) {
        List<Task> scheduleTasks = new ArrayList<>();

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);

            if (task instanceof Deadline) {
                Deadline deadline = (Deadline) task;
                if (deadline.getDateTime().toLocalDate().equals(scheduleDate)) {
                    scheduleTasks.add(task);
                }
            } else if (task instanceof Event) {
                Event event = (Event) task;
                LocalDate eventFromDate = event.getFromDateTime().toLocalDate();
                LocalDate eventToDate = event.getToDateTime().toLocalDate();

                if (!scheduleDate.isBefore(eventFromDate) && !scheduleDate.isAfter(eventToDate)) {
                    scheduleTasks.add(task);
                }
            }
        }

        return scheduleTasks;
    }

    private void displaySchedule(Ui ui, List<Task> scheduleTasks, LocalDate scheduleDate) {
        String formattedDate = scheduleDate.format(DateTimeFormatter.ofPattern("EEEE, MMM dd yyyy"));

        if (scheduleTasks.isEmpty()) {
            ui.showMessage("No scheduled tasks for " + formattedDate);
        } else {
            StringBuilder scheduleOutput = new StringBuilder();
            scheduleOutput.append("Schedule for ").append(formattedDate).append(":\n");
            scheduleOutput.append("=".repeat(33)).append("\n");

            for (int i = 0; i < scheduleTasks.size(); i++) {
                Task task = scheduleTasks.get(i);
                String timeInfo = "";

                if (task instanceof Deadline) {
                    Deadline deadline = (Deadline) task;
                    timeInfo = " [Due: " + deadline.getDateTime().format(
                            DateTimeFormatter.ofPattern("hh:mm a")) + "]";
                } else if (task instanceof Event) {
                    Event event = (Event) task;
                    timeInfo = " [" + event.getFromDateTime().format(
                            DateTimeFormatter.ofPattern("hh:mm a")) + " - " +
                            event.getToDateTime().format(DateTimeFormatter.ofPattern("hh:mm a")) + "]";
                }

                scheduleOutput.append((i + 1)).append(". ").append(task).append(timeInfo).append("\n");
            }

            scheduleOutput.append("=".repeat(33)).append("\n");
            scheduleOutput.append("Total tasks: ").append(scheduleTasks.size());

            ui.showMessage(scheduleOutput.toString());
        }
    }
}