package duke;

import java.time.LocalDateTime;
import java.util.List;

public final class ParserUtil
{
    private ParserUtil() { }

    /**
     * Convert one serialized line into a Task.
     * Unknown/corrupt lines are skipped by returning null.
     */
    public static Task parseLine(final String line)
    {
        try {
            final String[] parts = line.split("\\s*\\|\\s*");
            final String type = parts[0];
            final boolean done = "1".equals(parts[1]);
            final String desc = parts[2];

            switch (type) {
                case "T":
                    return new Todo(desc, done);
                case "D":
                {
                    final LocalDateTime by = LocalDateTime.parse(parts[3]); // ISO
                    return new Deadline(desc, done, by);
                }
                case "E":
                {
                    final String timeslot = parts[3];
                    return new Event(desc, done, timeslot);
                }
                default:
                    // unknown type (treat as corrupted)
                    return null;
            }
        } catch (final Exception e) {
            // corrupted line—skip gracefully for Level-7 stretch goal
            return null;
        }
    }

    /** Parse all lines; skip nulls. */
    public static void loadInto(final List<String> lines, final TaskList taskList)
    {
        for (final String line : lines) {
            final Task t = parseLine(line);
            if (t != null) {
                taskList.addSilently(t); // add without saving during initial load
            }
        }
    }
}

