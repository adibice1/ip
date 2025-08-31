package dibo.task;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task remove(int index) {
        return tasks.remove(index);
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    public int size() {
        return tasks.size();
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public void markAsDone(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).markAsDone();
        }
    }

    public void markAsUndone(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).markAsUndone();
        }
    }

    public void validateIndex(int index) throws IllegalArgumentException {
        if (index < 0 || index >= tasks.size()) {
            throw new IllegalArgumentException("Invalid task number. Please choose between 1 and " + tasks.size());
        }
    }
}
