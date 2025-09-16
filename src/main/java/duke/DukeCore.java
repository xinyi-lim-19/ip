package duke;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * Minimal core that parses one line at a time and returns a reply String.
 * Designed for reuse by both CLI and GUI.
 */
public class DukeCore
{
    private final TaskList tasks;

    public DukeCore()
    {
        // No storage here; the GUI can be wired to a TaskList with Storage if desired.
        this.tasks = new TaskList();
    }

    public String welcome()
    {
        return "Hello! I'm Bob\nWhat can I do for you?";
    }

    public boolean isExit(final String line)
    {
        return line != null && line.trim().equals("bye");
    }

    /** Handle a single command and return the reply text (no console printing). */
    public String reply(final String input)
    {
        if (input == null)
        {
            return "";
        }
        final String cmd = input.trim();
        if (cmd.isEmpty())
        {
            return "Please enter a command (try: todo, deadline, event, list, mark, unmark, delete, bye).";
        }

        try
        {
            if (cmd.equals("bye"))
            {
                return "Bye. Hope to see you again soon!";
            }
            else if (cmd.equals("list"))
            {
                return renderList();
            }
            else if (cmd.startsWith("mark"))
            {
                final int idx = requireIndex(cmd, "mark");
                requireInRange(idx, tasks.size(), "mark");
                tasks.get(idx - 1).mark();
                return "Nice! I've marked this task as done:\n  " + tasks.get(idx - 1);
            }
            else if (cmd.startsWith("unmark"))
            {
                final int idx = requireIndex(cmd, "unmark");
                requireInRange(idx, tasks.size(), "unmark");
                tasks.get(idx - 1).unmark();
                return "OK, I've marked this task as not done yet:\n  " + tasks.get(idx - 1);
            }
            else if (cmd.startsWith("delete"))
            {
                final int idx = requireIndex(cmd, "delete");
                requireInRange(idx, tasks.size(), "delete");
                final Task removed = tasks.remove(idx - 1);
                return "Noted. I've removed this task:\n  " + removed
                     + "\nNow you have " + tasks.size() + " tasks in the list.";
            }
            else if (cmd.startsWith("todo"))
            {
                final String desc = afterKeyword(cmd, "todo");
                if (desc.isEmpty())
                {
                    return "A todo needs a description. Example: todo borrow book";
                }
                // Using (desc, false) to match your ParserUtil constructors.
                tasks.add(new Todo(desc, false));
                return "Got it. I've added this task:\n  " + tasks.get(tasks.size() - 1)
                     + "\nNow you have " + tasks.size() + " tasks in the list.";
            }
            else if (cmd.startsWith("deadline"))
            {
                final String rest = afterKeyword(cmd, "deadline");
                final String[] p = splitOnce(rest, "/by");
                final String desc = p[0].trim();
                final String when = p[1].trim();

                if (desc.isEmpty())
                {
                    return "Deadline needs a description. Example: deadline return book /by 2019-12-02 1800";
                }
                if (when.isEmpty())
                {
                    return "Deadline needs a /by <when>. Example: deadline return book /by 2019-12-02 1800";
                }

                final LocalDateTime by = DateTimeUtil.parseFlexible(when);
                tasks.add(new Deadline(desc, false, by));
                return "Got it. I've added this task:\n  " + tasks.get(tasks.size() - 1)
                     + "\nNow you have " + tasks.size() + " tasks in the list.";
            }
            else if (cmd.startsWith("event"))
            {
                final String rest = afterKeyword(cmd, "event");
                final String[] p1 = splitOnce(rest, "/from");
                final String desc = p1[0].trim();
                final String[] p2 = splitOnce(p1[1].trim(), "/to");
                final String from = p2[0].trim();
                final String to = p2[1].trim();

                if (desc.isEmpty())
                {
                    return "Event needs a description. Example: event project meeting /from Mon 2pm /to 4pm";
                }
                if (from.isEmpty())
                {
                    return "Event needs a /from <start>. Example: event ... /from Mon 2pm /to 4pm";
                }
                if (to.isEmpty())
                {
                    return "Event needs a /to <end>. Example: event ... /from Mon 2pm /to 4pm";
                }

                final String timeslot = from + " to " + to;
                tasks.add(new Event(desc, false, timeslot));
                return "Got it. I've added this task:\n  " + tasks.get(tasks.size() - 1)
                     + "\nNow you have " + tasks.size() + " tasks in the list.";
            }
            else
            {
                return "I don't recognise that command. Try: todo, deadline, event, list, mark, unmark, delete, bye.";
            }
        }
        catch (final DateTimeParseException dtpe)
        {
            return "Sorry, I couldn't parse that date/time. Try formats like: "
                 + "2019-12-02 1800, 2019-12-02, 2/12/2019 1800, 2/12/2019.";
        }
        catch (final IOException ioe)
        {
            // In case your TaskList is later backed by Storage and save() throws.
            return "I tried to save but ran into a problem: " + ioe.getMessage();
        }
        catch (final Exception e)
        {
            return "Oops: " + e.getMessage();
        }
    }

    // ===== helpers (mirror your Bob helpers; no behavior change) =====

    private String renderList()
    {
        final StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++)
        {
            sb.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }
        if (tasks.size() == 0)
        {
            sb.append("(no tasks yet)\n");
        }
        return sb.toString().stripTrailing();
    }

    private static String afterKeyword(final String cmd, final String keyword)
    {
        if (cmd.equals(keyword))
        {
            return "";
        }
        return cmd.substring(keyword.length()).trim();
    }

    /** Split by first token like "/by", "/from", "/to". Always returns length 2. */
    private static String[] splitOnce(final String text, final String token)
    {
        final int i = indexOfToken(text, token);
        if (i < 0)
        {
            return new String[] { text, "" };
        }
        final String left = text.substring(0, i);
        final String right = text.substring(i + token.length());
        return new String[] { left, right };
    }

    /** Finds token allowing optional surrounding spaces. */
    private static int indexOfToken(final String text, final String token)
    {
        int i = text.indexOf(" " + token + " ");
        if (i >= 0)
        {
            return i + 1;
        }
        i = text.indexOf(" " + token);
        if (i >= 0)
        {
            return i + 1;
        }
        i = text.indexOf(token + " ");
        if (i >= 0)
        {
            return i;
        }
        return text.indexOf(token);
    }

    private static int parseIndex(final String s)
    {
        try
        {
            return Integer.parseInt(s.trim());
        }
        catch (final NumberFormatException e)
        {
            return -1;
        }
    }

    private static int requireIndex(final String cmd, final String keyword) throws Exception
    {
        final String rest = cmd.length() > keyword.length()
                ? cmd.substring(keyword.length()).trim()
                : "";
        final int idx = parseIndex(rest);
        if (idx <= 0)
        {
            throw new Exception("Please provide a valid index. Example: " + keyword + " 2");
        }
        return idx;
    }

    private static void requireInRange(final int idx, final int size, final String op) throws Exception
    {
        if (idx < 1 || idx > size)
        {
            throw new Exception("Index out of range for " + op + ". Use 1.." + size + ".");
        }
    }
}

