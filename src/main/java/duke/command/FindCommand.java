package duke.command;

import duke.TaskList;
import duke.Task;
import java.util.List;

public final class FindCommand {
    private FindCommand() {} // utility class

    /** Runs a find and returns the formatted result text. */
    public static String run(TaskList tasks, String keyword) {
        String q = keyword == null ? "" : keyword.trim();
        List<Task> matches = tasks.find(q);
        if (matches.isEmpty()) {
            return "No matching tasks found.";
        }
        StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
        for (int i = 0; i < matches.size(); i++) {
            sb.append(String.format(" %d.%s%n", i + 1, matches.get(i)));
        }
        return sb.toString().trim();
    }
}

