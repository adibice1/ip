package dibo.task;

import java.util.ArrayList;

/**
 * Manages a collection of Task objects.
 * Provides methods to add, remove, and manipulate tasks.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
        assert this.tasks != null : "TaskList: tasks list must be initialized";
    }

    /**
     * Constructs a TaskList with existing tasks.
     *
     * @param tasks an ArrayList of tasks to initialize the TaskList with
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the TaskList.
     *
     * @param task the task to add
     */
    public void add(Task task) {
        tasks.add(task);
        assert task != null : "TaskList.addTask: task must not be null";
    }

    /**
     * Removes a task from the TaskList at the specified index.
     *
     * @param index the index of the task to remove
     * @return the removed task
     */
    public Task remove(int index) {
        return tasks.remove(index);
    }

    /**
     * Gets the task at the specified index.
     *
     * @param index the index of the task to retrieve
     * @return the task at the specified index
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Returns all tasks in the TaskList.
     *
     * @return an ArrayList containing all tasks
     */
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    /**
     * Returns the number of tasks in the TaskList.
     *
     * @return the number of tasks
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Checks if the TaskList is empty.
     *
     * @return true if the TaskList contains no tasks, false otherwise
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Marks the task at the specified index as done.
     *
     * @param index the index of the task to mark as done
     */
    public void markAsDone(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).markAsDone();
        }
    }

    /**
     * Marks the task at the specified index as not done.
     *
     * @param index the index of the task to mark as not done
     */
    public void markAsUndone(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).markAsUndone();
        }
    }

    /**
     * Validates that the given index is within the bounds of the TaskList.
     *
     * @param index the index to validate
     * @throws IllegalArgumentException if the index is out of bounds
     */
    public void validateIndex(int index) throws IllegalArgumentException {
        if (index < 0 || index >= tasks.size()) {
            throw new IllegalArgumentException("Invalid task number. Please choose between 1 and " + tasks.size());
        }
    }
}