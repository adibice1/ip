package dibo.parser;

import dibo.command.*;

public class Parser {

    public static Command parse(String userInput) {
        assert userInput != null : "Parser.parse: userInput must not be null";

        String lowerInput = userInput.toLowerCase().trim();

        if (lowerInput.equals("bye")) {
            return new ExitCommand();
        } else if (lowerInput.equals("list")) {
            return new ListCommand();
        } else if (lowerInput.startsWith("mark")) {
            return parseMarkCommand(userInput, true);
        } else if (lowerInput.startsWith("unmark")) {
            return parseMarkCommand(userInput, false);
        } else if (lowerInput.startsWith("todo")) {
            return parseTodoCommand(userInput);
        } else if (lowerInput.startsWith("deadline")) {
            return parseDeadlineCommand(userInput);
        } else if (lowerInput.startsWith("event")) {
            return parseEventCommand(userInput);
        } else if (lowerInput.startsWith("delete")) {
            return parseDeleteCommand(userInput);
        } else if (lowerInput.startsWith("find")) {
            return parseFindCommand(userInput);
        } else if (lowerInput.startsWith("schedule")) {
            return parseScheduleCommand(userInput);
        } else {
            return new InvalidCommand("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }

    private static Command parseMarkCommand(String userInput, boolean isMark) {
        String numberStr = userInput.substring(isMark ? 4 : 6).trim();
        // ASSERT: numberStr refers to the remainder after command keyword
        assert numberStr != null : "Parser.parseMarkCommand: numberStr should not be null";

        if (numberStr.isEmpty()) {
            return new InvalidCommand("Please specify a task number to " +
                    (isMark ? "mark" : "unmark"));
        }
        try {
            int taskNumber = Integer.parseInt(numberStr) - 1;
            return new MarkCommand(taskNumber, isMark);
        } catch (NumberFormatException e) {
            return new InvalidCommand("Please enter a valid task number.");
        }
    }

    private static Command parseDeleteCommand(String userInput) {
        String numberStr = userInput.substring(6).trim();
        // ASSERT: numberStr refers to the remainder after 'delete'
        assert numberStr != null : "Parser.parseDeleteCommand: numberStr should not be null";

        if (numberStr.isEmpty()) {
            return new InvalidCommand("Please specify a task number to delete.");
        }
        try {
            int taskNumber = Integer.parseInt(numberStr) - 1;
            return new DeleteCommand(taskNumber);
        } catch (NumberFormatException e) {
            return new InvalidCommand("Please enter a valid task number");
        }
    }

    private static Command parseTodoCommand(String userInput) {
        String description = userInput.substring(4).trim();
        if (description.isEmpty()) {
            return new InvalidCommand("OOPS!!! The description of a todo cannot be empty.");
        }
        return new AddTodoCommand(description);
    }

    private static Command parseDeadlineCommand(String userInput) {
        return new AddDeadlineCommand(userInput);
    }

    private static Command parseEventCommand(String userInput) {
        return new AddEventCommand(userInput);
    }

    private static Command parseFindCommand(String userInput) {
        String searchTerm = userInput.substring(5).trim();
        assert searchTerm != null : "Parser.parseFind: searchTerm must not be null";

        if (searchTerm.isEmpty()) {
            return new InvalidCommand("Please specify a word to search for. Format: find <word>");
        }
        return new FindCommand(searchTerm);
    }

    private static Command parseScheduleCommand(String userInput) {
        String dateStr = userInput.substring(8).trim();
        assert dateStr != null : "Parser.parseScheduleCommand: dateStr must not be null";

        if (dateStr.isEmpty()) {
            return new InvalidCommand("Please specify a date to view schedule. Format: schedule <date>");
        }
        return new ViewScheduleCommand(dateStr);
    }
}

