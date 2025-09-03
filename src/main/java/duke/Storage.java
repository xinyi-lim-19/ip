package duke;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class Storage {
    private final Path file = Paths.get("data").resolve("duke.txt");

    /** Loads raw lines from disk. Returns empty list if file doesn't exist. */
    public List<String> load() throws IOException {
        if (Files.notExists(file)) {
            return List.of(); // first run—no data yet
        }
        return Files.readAllLines(file, StandardCharsets.UTF_8);
    }

    /** Saves tasks to disk in a simple line-based format. */
    public void save(List<Task> tasks) throws IOException {
        Files.createDirectories(file.getParent()); // ensure ./data exists
        List<String> lines = new ArrayList<>();
        for (Task t : tasks) {
            lines.add(t.serialize()); // you’ll implement this on Task/its subclasses
        }
        Files.write(
            file,
            lines,
            StandardCharsets.UTF_8,
            StandardOpenOption.CREATE,
            StandardOpenOption.TRUNCATE_EXISTING
        );
    }
}

