package dibo.parser;

import dibo.command.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    public void testParseExitCommand() {
        Command command = Parser.parse("bye");
        assertTrue(command instanceof ExitCommand);

        command = Parser.parse("BYE");
        assertTrue(command instanceof ExitCommand);

        command = Parser.parse("  bye  ");
        assertTrue(command instanceof ExitCommand);
    }

    @Test
    public void testParseListCommand() {
        Command command = Parser.parse("list");
        assertTrue(command instanceof ListCommand);

        command = Parser.parse("LIST");
        assertTrue(command instanceof ListCommand);

        command = Parser.parse("  list  ");
        assertTrue(command instanceof ListCommand);
    }

    @Test
    public void testParseMarkCommand() {
        Command command = Parser.parse("mark 1");
        assertTrue(command instanceof MarkCommand);

        command = Parser.parse("mark 5");
        assertTrue(command instanceof MarkCommand);

        command = Parser.parse("MARK 2");
        assertTrue(command instanceof MarkCommand);
    }

    @Test
    public void testParseUnmarkCommand() {
        Command command = Parser.parse("unmark 1");
        assertTrue(command instanceof MarkCommand);

        command = Parser.parse("unmark 3");
        assertTrue(command instanceof MarkCommand);

        command = Parser.parse("UNMARK 2");
        assertTrue(command instanceof MarkCommand);
    }

    @Test
    public void testParseMarkCommand_invalid() {
        Command command = Parser.parse("mark");
        assertTrue(command instanceof InvalidCommand);

        command = Parser.parse("mark ");
        assertTrue(command instanceof InvalidCommand);

        command = Parser.parse("mark abc");
        assertTrue(command instanceof InvalidCommand);
    }

    @Test
    public void testParseUnmarkCommand_invalid() {
        Command command = Parser.parse("unmark");
        assertTrue(command instanceof InvalidCommand);

        command = Parser.parse("unmark ");
        assertTrue(command instanceof InvalidCommand);

        command = Parser.parse("unmark abc");
        assertTrue(command instanceof InvalidCommand);
    }

    @Test
    public void testParseDeleteCommand() {
        Command command = Parser.parse("delete 1");
        assertTrue(command instanceof DeleteCommand);

        command = Parser.parse("delete 5");
        assertTrue(command instanceof DeleteCommand);

        command = Parser.parse("DELETE 2");
        assertTrue(command instanceof DeleteCommand);
    }

    @Test
    public void testParseDeleteCommand_invalid() {
        Command command = Parser.parse("delete");
        assertTrue(command instanceof InvalidCommand);

        command = Parser.parse("delete ");
        assertTrue(command instanceof InvalidCommand);

        command = Parser.parse("delete abc");
        assertTrue(command instanceof InvalidCommand);
    }

    @Test
    public void testParseTodoCommand() {
        Command command = Parser.parse("todo Read book");
        assertTrue(command instanceof AddTodoCommand);

        command = Parser.parse("todo   Write essay  ");
        assertTrue(command instanceof AddTodoCommand);

        command = Parser.parse("TODO Complete assignment");
        assertTrue(command instanceof AddTodoCommand);
    }

    @Test
    public void testParseTodoCommand_invalid() {
        Command command = Parser.parse("todo");
        assertTrue(command instanceof InvalidCommand);

        command = Parser.parse("todo ");
        assertTrue(command instanceof InvalidCommand);

        command = Parser.parse("todo    ");
        assertTrue(command instanceof InvalidCommand);
    }

    @Test
    public void testParseDeadlineCommand() {
        Command command = Parser.parse("deadline Submit report /by 2023-12-25");
        assertTrue(command instanceof AddDeadlineCommand);

        command = Parser.parse("DEADLINE Finish project /by tomorrow");
        assertTrue(command instanceof AddDeadlineCommand);

        command = Parser.parse("deadline Task with /by in description /by 2023-12-25");
        assertTrue(command instanceof AddDeadlineCommand);
    }

    @Test
    public void testParseEventCommand() {
        Command command = Parser.parse("event Team meeting /from 2023-12-25 /to 2023-12-26");
        assertTrue(command instanceof AddEventCommand);

        command = Parser.parse("EVENT Conference /from Monday /to Tuesday");
        assertTrue(command instanceof AddEventCommand);

        command = Parser.parse("event Party /from 2023-12-31 /to 2024-01-01");
        assertTrue(command instanceof AddEventCommand);
    }

    @Test
    public void testParseFindByDateCommand() {
        Command command = Parser.parse("find date 2023-12-25");
        assertTrue(command instanceof FindByDateCommand);

        command = Parser.parse("find date tomorrow");
        assertTrue(command instanceof FindByDateCommand);

        command = Parser.parse("FIND DATE 2023-12-31");
        assertTrue(command instanceof FindByDateCommand);
    }

    @Test
    public void testParseFindByDateCommand_invalid() {
        Command command = Parser.parse("find date");
        assertTrue(command instanceof InvalidCommand);

        command = Parser.parse("find date ");
        assertTrue(command instanceof InvalidCommand);
    }

    @Test
    public void testParseInvalidCommand() {
        Command command = Parser.parse("invalid command");
        assertTrue(command instanceof InvalidCommand);

        command = Parser.parse("");
        assertTrue(command instanceof InvalidCommand);

        command = Parser.parse("   ");
        assertTrue(command instanceof InvalidCommand);

        command = Parser.parse("random text");
        assertTrue(command instanceof InvalidCommand);
    }

    @Test
    public void testParseMixedCaseCommands() {
        Command command = Parser.parse("ByE");
        assertTrue(command instanceof ExitCommand);

        command = Parser.parse("LiSt");
        assertTrue(command instanceof ListCommand);

        command = Parser.parse("mArk 1");
        assertTrue(command instanceof MarkCommand);

        command = Parser.parse("UnMaRk 1");
        assertTrue(command instanceof MarkCommand);
    }

    @Test
    public void testParseCommandWithExtraSpaces() {
        Command command = Parser.parse("  mark   3  ");
        assertTrue(command instanceof MarkCommand);

        command = Parser.parse("  todo    Read book   ");
        assertTrue(command instanceof AddTodoCommand);

        command = Parser.parse("  delete   2  ");
        assertTrue(command instanceof DeleteCommand);

        command = Parser.parse("  event   Meeting  /from  now  /to  later  ");
        assertTrue(command instanceof AddEventCommand);
    }

    @Test
    public void testParseEdgeCases() {
        // Test very large task numbers
        Command command = Parser.parse("mark 999999");
        assertTrue(command instanceof MarkCommand);

        // Test negative numbers
        Command negativeCommand = Parser.parse("mark -1");
        assertTrue(negativeCommand instanceof MarkCommand);

        // Test zero
        Command zeroCommand = Parser.parse("mark 0");
        assertTrue(zeroCommand instanceof MarkCommand);

        // Test decimal numbers
        Command decimalCommand = Parser.parse("mark 1.5");
        assertTrue(decimalCommand instanceof InvalidCommand);
    }

/*    @Test
    public void testParseCommandExecution() {
        // Test that commands can be executed without throwing exceptions
        Command exitCommand = Parser.parse("bye");
        assertDoesNotThrow(() -> exitCommand.execute());

        Command listCommand = Parser.parse("list");
        assertDoesNotThrow(() -> listCommand.execute());

        Command invalidCommand = Parser.parse("invalid");
        assertDoesNotThrow(() -> invalidCommand.execute());
    }*/

    @Test
    public void testParseCommandOrderIndependence() {
        // Test that command parsing is not order-dependent
        Command command1 = Parser.parse("mark 1");
        Command command2 = Parser.parse("todo test");
        Command command3 = Parser.parse("list");

        assertTrue(command1 instanceof MarkCommand);
        assertTrue(command2 instanceof AddTodoCommand);
        assertTrue(command3 instanceof ListCommand);

        // Test same commands in different order
        Command command4 = Parser.parse("list");
        Command command5 = Parser.parse("todo test");
        Command command6 = Parser.parse("mark 1");

        assertTrue(command4 instanceof ListCommand);
        assertTrue(command5 instanceof AddTodoCommand);
        assertTrue(command6 instanceof MarkCommand);
    }

    @Test
    public void testParseCommandWithSpecialCharacters() {
        Command command = Parser.parse("todo Read book @ library");
        assertTrue(command instanceof AddTodoCommand);

        command = Parser.parse("todo Task with symbols !@#$%^&*()");
        assertTrue(command instanceof AddTodoCommand);

        command = Parser.parse("deadline Submit report /by 2023-12-25 18:00");
        assertTrue(command instanceof AddDeadlineCommand);

        command = Parser.parse("event Meeting /from 2023-12-25 10:00 /to 2023-12-25 12:00");
        assertTrue(command instanceof AddEventCommand);
    }

    @Test
    public void testParseCommandWithEmptyInput() {
        Command command = Parser.parse("");
        assertTrue(command instanceof InvalidCommand);

        command = Parser.parse("   ");
        assertTrue(command instanceof InvalidCommand);

        command = Parser.parse("\t");
        assertTrue(command instanceof InvalidCommand);

        command = Parser.parse("\n");
        assertTrue(command instanceof InvalidCommand);
    }

    @Test
    public void testParseCommandWithMultipleSpaces() {
        Command command = Parser.parse("mark    5");
        assertTrue(command instanceof MarkCommand);

        command = Parser.parse("todo     Multiple     Spaces");
        assertTrue(command instanceof AddTodoCommand);

        command = Parser.parse("delete     3");
        assertTrue(command instanceof DeleteCommand);
    }
}