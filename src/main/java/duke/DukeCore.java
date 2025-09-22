package duke;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * Core command engine that parses a single input line and produces a reply.
 * Stateless with respect to UI; state is held in the internal TaskList.
 */
public class DukeCore {

    // ===== Commands =====
    private static final String CMD_BYE      = "bye";
    private static final String CMD_LIST     = "list";
    private static final String CMD_MARK     = "mark";
    private static final String CMD_UNMARK   = "unmark";
    private static final String CMD_DELETE   = "delete";
    private static final String CMD_TODO     = "todo";
    private static final String CMD_DEADLINE = "deadline";
    private static final String CMD_EVENT    = "event";
    private static final String CMD_EDIT     = "edit";

    // ===== Tokens =====
    private static final String TOKEN_BY   = "/by";
    private static final String TOKEN_FROM = "/from";
    private static final String TOKEN_TO   = "/to";

    private final TaskList tasks;

    public DukeCore() {
        // If you want persistence here, construct TaskList with a Storage later.
>>>>>>> 6e18ff1 (A-CodeQuality: apply SLAP, constants, and early returns in DukeCore)
        this.tasks = new TaskList();
    }

    public String welcome() {
        return "Hello! I'm Bob\nWhat can I do for you?";
    }

    public boolean isExit(final String line) {
        return line != null && line.trim().equals(CMD_BYE);
    }

    /** Handle a single command and return the reply text (no console printing). */
    public String reply(final String input) {
        if (input == null) return "";

        final String cmd = input.trim();
        if (cmd.isEmpty()) {
            return "Please enter a command (try: todo, deadline, event, list, mark, unmark, delete, edit, bye).";
        }

        try {
            // Early dispatch (SLAP)
            if (cmd.equals(CMD_BYE)) {
                return "Bye. Hope to see you again soon!";
            }
            if (cmd.equals(CMD_LIST)) {
                return renderList();
            }
            if (cmd.startsWith(CMD_MARK)) {
                return handleMark(cmd);
            }
            if (cmd.startsWith(CMD_UNMARK)) {
                return handleUnmark(cmd);
            }
            if (cmd.startsWith(CMD_DELETE)) {
                return handleDelete(cmd);
            }
            if (cmd.startsWith(CMD_TODO)) {
                return handleTodo(cmd);
            }
            if (cmd.startsWith(CMD_DEADLINE)) {
                return handleDeadline(cmd);
            }
            if (cmd.startsWith(CMD_EVENT)) {
                return handleEvent(cmd);
            }
            if (cmd.startsWith(CMD_EDIT)) {
                return handleEdit(cmd);
            }

            return "I don't recognise that command. Try: todo, deadline, event, list, mark, unmark, delete, edit, bye.";
        } catch (final DateTimeParseException dtpe) {
            return "Sorry, I couldn't parse that date/time. Try formats like: "
                 + "2019-12-02 1800, 2019-12-02, 2/12/2019 1800, 2/12/2019.";
        } catch (final IOException ioe) {
            return "I tried to save but ran into a problem: " + ioe.getMessage();
        } catch (final Exception e) {
            return "Oops: " + e.getMessage();
        }
    }

    // ===== Handlers (SLAP) =====

    private String handleMark(final String cmd) throws Exception, IOException {
        final int idx = requireIndex(cmd, CMD_MARK);
        assert idx > 0 && idx <= tasks.size() : "Index must be 1.." + tasks.size();
        requireInRange(idx, tasks.size(), CMD_MARK);

        tasks.get(idx - 1).mark();
        tasks.save();
        return "Nice! I've marked this task as done:\n  " + tasks.get(idx - 1);
    }

    private String handleUnmark(final String cmd) throws Exception, IOException {
        final int idx = requireIndex(cmd, CMD_UNMARK);
        assert idx > 0 && idx <= tasks.size() : "Index must be 1.." + tasks.size();
        requireInRange(idx, tasks.size(), CMD_UNMARK);

        tasks.get(idx - 1).unmark();
        tasks.save();
        return "OK, I've marked this task as not done yet:\n  " + tasks.get(idx - 1);
    }

    private String handleDelete(final String cmd) throws Exception, IOException {
        final int idx = requireIndex(cmd, CMD_DELETE);
        assert idx > 0 && idx <= tasks.size() : "Index must be 1.." + tasks.size();
        requireInRange(idx, tasks.size(), CMD_DELETE);

        final Task removed = tasks.remove(idx - 1);
        tasks.save();
        return "Noted. I've removed this task:\n  " + removed
             + "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    private String handleTodo(final String cmd) throws IOException {
        final String desc = afterKeyword(cmd, CMD_TODO);
        if (desc.isEmpty()) {
            return "A todo needs a description. Example: todo borrow book";
        }
        tasks.add(new Todo(desc, false)); // Todo(String, boolean)
        tasks.save();
        return "Got it. I've added this task:\n  " + tasks.get(tasks.size() - 1)
             + "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    private String handleDeadline(final String cmd) throws IOException {
        final String rest = afterKeyword(cmd, CMD_DEADLINE);
        final String[] p = splitOnce(rest, TOKEN_BY);
        final String desc = p[0].trim();
        final String when = p[1].trim();

        if (desc.isEmpty()) {
            return "Deadline needs a description. Example: deadline return book /by 2019-12-02 1800";
        }
        if (when.isEmpty()) {
            return "Deadline needs a /by <when>. Example: deadline return book /by 2019-12-02 1800";
        }

        final LocalDateTime by = DateTimeUtil.parseFlexible(when);
        tasks.add(new Deadline(desc, false, by)); // Deadline(String, boolean, LocalDateTime)
        tasks.save();
        return "Got it. I've added this task:\n  " + tasks.get(tasks.size() - 1)
             + "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    private String handleEvent(final String cmd) throws IOException {
        final String rest = afterKeyword(cmd, CMD_EVENT);
        final String[] p1 = splitOnce(rest, TOKEN_FROM);
        final String desc = p1[0].trim();

        final String[] p2 = splitOnce(p1[1].trim(), TOKEN_TO);
        final String from = p2[0].trim();
        final String to = p2[1].trim();

        if (desc.isEmpty()) {
            return "Event needs a description. Example: event project meeting /from Mon 2pm /to 4pm";
        }
        if (from.isEmpty()) {
            return "Event needs a /from <start>. Example: event ... /from Mon 2pm /to 4pm";
        }
        if (to.isEmpty()) {
            return "Event needs a /to <end>. Example: event ... /from Mon 2pm /to 4pm";
        }

        final String timeslot = from + " /to " + to;
        tasks.add(new Event(desc, false, timeslot)); // Event(String, boolean, String)
        tasks.save();
        return "Got it. I've added this task:\n  " + tasks.get(tasks.size() - 1)
             + "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    // ===== C-Update: edit command (kept from your code) =====
    private String handleEdit(final String cmd) throws IOException {
        final String rest = afterKeyword(cmd, CMD_EDIT);
        final String[] firstTwo = rest.split("\\s+", 3); // idx, field, payload

        if (firstTwo.length < 3) {
            return "Usage:\n"
                 + "  edit INDEX desc NEW_DESCRIPTION\n"
                 + "  edit INDEX by NEW_DATE_OR_DATETIME\n"
                 + "  edit INDEX timeslot NEW_FROM /to NEW_TO";
        }

        final int idx = parseIndex(firstTwo[0]);
        assert idx > 0 && idx <= tasks.size() : "Index must be 1.." + tasks.size();
        if (idx <= 0 || idx > tasks.size()) {
            return "Please provide a valid index within 1.." + tasks.size() + ".";
        }

        final String field = firstTwo[1].toLowerCase();
        final String payload = firstTwo[2].trim();
        final Task t = tasks.get(idx - 1);

        switch (field) {
        case "desc":
            if (payload.isEmpty()) return "Description cannot be empty.";
            t.setDescription(payload);   // requires Task#setDescription
            tasks.save();
            return "Updated description:\n  " + t;

        case "by":
            if (!(t instanceof Deadline)) return "Task #" + idx + " is not a deadline.";
            if (payload.isEmpty()) return "Please provide a date/time (e.g., 2019-12-02 1800).";
            final LocalDateTime newBy = DateTimeUtil.parseFlexible(payload);
            ((Deadline) t).setBy(newBy);   // requires Deadline#setBy
            tasks.save();
            return "Updated deadline time:\n  " + t;

        case "timeslot":
            if (!(t instanceof Event)) return "Task #" + idx + " is not an event.";
            if (payload.isEmpty()) return "Please provide a timeslot, e.g., 'Mon 2pm /to 4pm'.";
            ((Event) t).setTimeslot(payload);  // requires Event#setTimeslot
            tasks.save();
            return "Updated event time:\n  " + t;

        default:
            return "Unknown field '" + field + "'. Use: desc | by | timeslot";
        }
    }

    // ===== helpers =====

    private String renderList() {
        final StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }
        if (tasks.size() == 0) {
            sb.append("(no tasks yet)\n");
        }
        return sb.toString().stripTrailing();
    }

    private static String afterKeyword(final String cmd, final String keyword) {
        if (cmd.equals(keyword)) {
            return "";
        }
        return cmd.substring(keyword.length()).trim();
    }

    /** Split by first token like "/by", "/from", "/to". Always returns length 2. */
    private static String[] splitOnce(final String text, final String token) {
        final int i = indexOfToken(text, token);
        if (i < 0) {
            return new String[] { text, "" };
        }
        final String left = text.substring(0, i);
        final String right = text.substring(i + token.length());
        return new String[] { left, right };
    }

    /** Finds token allowing optional surrounding spaces. */
    private static int indexOfToken(final String text, final String token) {
        int i = text.indexOf(" " + token + " ");
        if (i >= 0) return i + 1;
        i = text.indexOf(" " + token);
        if (i >= 0) return i + 1;
        i = text.indexOf(token + " ");
        if (i >= 0) return i;
        return text.indexOf(token);
    }

    private static int parseIndex(final String s) {
        try {
            return Integer.parseInt(s.trim());
        } catch (final NumberFormatException e) {
            return -1;
        }
    }

    private static int requireIndex(final String cmd, final String keyword) throws Exception {
        final String rest = cmd.length() > keyword.length()
                ? cmd.substring(keyword.length()).trim()
                : "";
        final int idx = parseIndex(rest);
        if (idx <= 0) {
            throw new Exception("Please provide a valid index. Example: " + keyword + " 2");
        }
        return idx;
    }

    private static void requireInRange(final int idx, final int size, final String op) throws Exception {
        if (idx < 1 || idx > size) {
            throw new Exception("Index out of range for " + op + ". Use 1.." + size + ".");
        }
    }
}

