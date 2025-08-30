public class ExitCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            storage.saveTasks(tasks);
            ui.showGoodbye();
        } catch (Exception e) {
            ui.showError("Failed to save tasks: " + e.getMessage());
        }
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
