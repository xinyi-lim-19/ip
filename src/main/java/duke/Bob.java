package duke;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

// Level-8 date/time
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Bob
{
    private static final String LINE = "____________________________________________________________";

    // ===== Exceptions =====
    private static class DukeException extends Exception
    {
        DukeException(final String message)
        {
            super(message);
        }
    }

    // ===== Model =====
    private static class Task
    {
        protected final String description;
        protected boolean isDone;

        Task(final String description)
        {
            this.description = description;
            this.isDone = false;
        }

        void mark()
        {
            this.isDone = true;
        }

        void unmark()
        {
            this.isDone = false;
        }

        String statusIcon()
        {
            return isDone ? "X" : " ";
        }

        @Override
        public String toString()
        {
            return "[" + statusIcon() + "] " + description;
        }
    }

    private static class Todo extends Task
    {
        Todo(final String description)
        {
            super(description);
        }

        @Override
        public String toString()
        {
            return "[T]" + super.toString();
        }
    }

    /** Level-8: store a real LocalDateTime and pretty-print it */
    private static class Deadline extends Task
    {
        private final LocalDateTime by;

        Deadline(final String description, final LocalDateTime by)
        {
            super(description);
            this.by = by;
        }

        @Override
        public String toString()
        {
            return "[D]" + super.toString() + " (by: " + pretty(by) + ")";
        }
    }

    private static class Event extends Task
    {
        private final String from;
        private final String to;

        Event(final String description, final String from, final String to)
        {
            super(description);
            this.from = from;
            this.to = to;
        }

        @Override
        public String toString()
        {
            return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
        }
    }

    public static void main(final String[] args) throws IOException
    {
        final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        printLogo();
        greet();

        final ArrayList<Task> tasks = new ArrayList<>(100);

        while (true)
        {
            final String input = br.readLine();
            if (input == null)
            {
                exit();
                break;
            }

            final String cmd = input.trim();
            if (cmd.isEmpty())
            {
                error("Please enter a command (try: todo, deadline, event, list, mark, unmark, delete, bye).");
                continue;
            }

            try
            {
                if (cmd.equals("bye"))
                {
                    exit();
                    break;
                }
                else if (cmd.equals("list"))
                {
                    printList(tasks);
                }
                else if (cmd.startsWith("mark"))
                {
                    final int idx = requireIndex(cmd, "mark");
                    requireInRange(idx, tasks.size(), "mark");
                    tasks.get(idx - 1).mark();
                    block(" Nice! I've marked this task as done:\n   " + tasks.get(idx - 1));
                }
                else if (cmd.startsWith("unmark"))
                {
                    final int idx = requireIndex(cmd, "unmark");
                    requireInRange(idx, tasks.size(), "unmark");
                    tasks.get(idx - 1).unmark();
                    block(" OK, I've marked this task as not done yet:\n   " + tasks.get(idx - 1));
                }
                else if (cmd.startsWith("delete"))
                {
                    final int idx = requireIndex(cmd, "delete");
                    requireInRange(idx, tasks.size(), "delete");
                    final Task removed = tasks.remove(idx - 1);
                    block(
                        " Noted. I've removed this task:\n   " + removed
                        + "\n Now you have " + tasks.size() + " tasks in the list."
                    );
                }
                else if (cmd.startsWith("todo"))
                {
                    final String desc = afterKeyword(cmd, "todo");
                    if (desc.isEmpty())
                    {
                        throw new DukeException("A todo needs a description. Example: todo borrow book");
                    }
                    addTask(tasks, new Todo(desc));
                }
                else if (cmd.startsWith("deadline"))
                {
                    // Level-8: parse '/by <date or date time>' and store LocalDateTime
                    final String rest = afterKeyword(cmd, "deadline");
                    final String[] p = splitOnce(rest, "/by");
                    final String desc = p[0].trim();
                    final String when = p[1].trim();
                    if (desc.isEmpty())
                    {
                        throw new DukeException(
                            "Deadline needs a description. Example: deadline return book /by 2019-12-02 1800"
                        );
                    }
                    if (when.isEmpty())
                    {
                        throw new DukeException(
                            "Deadline needs a /by <when>. Example: deadline return book /by 2019-12-02 1800"
                        );
                    }

                    final LocalDateTime by = parseFlexibleDateTime(when);
                    addTask(tasks, new Deadline(desc, by));
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
                        throw new DukeException(
                            "Event needs a description. Example: event project meeting /from Mon 2pm /to 4pm"
                        );
                    }
                    if (from.isEmpty())
                    {
                        throw new DukeException(
                            "Event needs a /from <start>. Example: event ... /from Mon 2pm /to 4pm"
                        );
                    }
                    if (to.isEmpty())
                    {
                        throw new DukeException(
                            "Event needs a /to <end>. Example: event ... /from Mon 2pm /to 4pm"
                        );
                    }
                    addTask(tasks, new Event(desc, from, to));
                }
                else
                {
                    throw new DukeException(
                        "I don't recognise that command. Try: todo, deadline, event, list, mark, unmark, delete, bye."
                    );
                }
            }
            catch (final DukeException ex)
            {
                error(ex.getMessage());
            }
            catch (final DateTimeParseException dtpe)
            {
                error(
                    "Sorry, I couldn't parse that date/time. Try formats like: "
                    + "2019-12-02 1800, 2019-12-02, 2/12/2019 1800, 2/12/2019."
                );
            }
        }
    }

    // ===== UI helpers =====
    private static void printLogo()
    {
        final String logo =
              " ____   ___   ____  \n"
            + "| __ ) / _ \\ | __ ) \n"
            + "|  _ \\| | | ||  _ \\ \n"
            + "| |_) | |_| || |_) |\n"
            + "|____/ \\___/ |____/ \n";
        System.out.println("Hello from\n" + logo);
    }

    private static void greet()
    {
        block(" Hello! I'm Bob\n What can I do for you?");
    }

    private static void exit()
    {
        block(" Bye. Hope to see you again soon!");
    }

    private static void block(final String body)
    {
        System.out.println(LINE);
        for (final String s : body.split("\n", -1))
        {
            System.out.println(s);
        }
        System.out.println(LINE);
    }

    private static void error(final String msg)
    {
        block(" " + msg);
    }

    private static void printList(final ArrayList<Task> tasks)
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(" Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++)
        {
            sb.append(" ")
              .append(i + 1)
              .append(".")
              .append(tasks.get(i))
              .append("\n");
        }
        final String body = sb.toString().endsWith("\n")
            ? sb.substring(0, sb.length() - 1)
            : sb.toString();
        block(body);
    }

    // ===== logic helpers =====
    private static void addTask(final ArrayList<Task> tasks, final Task t) throws DukeException
    {
        if (tasks.size() >= 100)
        {
            throw new DukeException("Sorry, I can only store up to 100 items.");
        }
        tasks.add(t);
        block(
            " Got it. I've added this task:\n   " + t
            + "\n Now you have " + tasks.size() + " tasks in the list."
        );
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

    private static int requireIndex(final String cmd, final String keyword) throws DukeException
    {
        final String rest = cmd.length() > keyword.length()
                ? cmd.substring(keyword.length()).trim()
                : "";
        final int idx = parseIndex(rest);
        if (idx <= 0)
        {
            throw new DukeException("Please provide a valid index. Example: " + keyword + " 2");
        }
        return idx;
    }

    private static void requireInRange(final int idx, final int size, final String op) throws DukeException
    {
        if (idx < 1 || idx > size)
        {
            throw new DukeException("Index out of range for " + op + ". Use 1.." + size + ".");
        }
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

    // ===== Level-8 date/time helpers =====
    private static final DateTimeFormatter OUT_DATE = DateTimeFormatter.ofPattern("MMM d yyyy");
    private static final DateTimeFormatter OUT_DT = DateTimeFormatter.ofPattern("MMM d yyyy, h:mma");

    private static String pretty(final LocalDateTime dt)
    {
        return dt.toLocalTime().equals(LocalTime.MIDNIGHT)
                ? dt.toLocalDate().format(OUT_DATE)
                : dt.format(OUT_DT);
    }

    /** Accepts: "yyyy-MM-dd HHmm", "yyyy-MM-dd", "d/M/yyyy HHmm", "d/M/yyyy". */
    private static LocalDateTime parseFlexibleDateTime(final String s)
    {
        final String x = s.trim();
        // datetime forms
        try
        {
            return LocalDateTime.parse(x, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
        }
        catch (final DateTimeParseException ignore)
        {
        }
        try
        {
            return LocalDateTime.parse(x, DateTimeFormatter.ofPattern("d/M/yyyy HHmm"));
        }
        catch (final DateTimeParseException ignore)
        {
        }
        // date-only forms -> midnight
        try
        {
            return LocalDate.parse(x, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
        }
        catch (final DateTimeParseException ignore)
        {
        }
        try
        {
            return LocalDate.parse(x, DateTimeFormatter.ofPattern("d/M/yyyy")).atStartOfDay();
        }
        catch (final DateTimeParseException ignore)
        {
        }
        throw new DateTimeParseException("Unrecognized date/time", x, 0);
    }
}

