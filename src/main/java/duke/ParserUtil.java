package duke;

import java.time.LocalDateTime;
import java.util.List;

public final class ParserUtil {
    private ParserUtil() {}

    /** Convert one serialized line into a Task. Unknown/corrupt lines are skipped by returning null. */
    public static Task parseLine(String line) {
        try {
            String[] parts = line.split("\\s*\\|\\s*");
            String type = parts[0];
            boolean done = "1".equals(parts[1]);
            String desc = parts[2];

            switch (type) {
                case "T":
                    return new Todo(desc, done);
                case "D": {
                    LocalDateTime by = LocalDateTime.parse(parts[3]); // ISO
                    return new Deadline(desc, done, by);
                }
                case "E": {
                    String timeslot = parts[3];
                    return new Event(desc, done, timeslot);
                }
                default:
                    return null; // unknown type (treat as corrupted)
            }
        } catch (Exception e) {
            // corrupted line—skip gracefully for Level-7 stretch goal
            return null;
        }
    }

    /** Parse all lines; skip nulls. */
    public static void loadInto(List<String> lines, TaskList taskList) {
        for (String line : lines) {
            Task t = parseLine(line);
            if (t != null) taskList.addSilently(t); // add without saving during initial load
        }
    }
}

