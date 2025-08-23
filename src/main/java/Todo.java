public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    public static Todo parseTodoInput(String userInput) {
        String input = userInput.replace("todo", "").trim();
        return new Todo(input);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
