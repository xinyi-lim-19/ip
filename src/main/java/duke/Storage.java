package duke;

import java.io.IOException;
import java.util.List;

public class Storage {
    private final String filePath;

    public Storage() {
        this("data/duke.txt");
    }

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public List<Task> load() throws IOException {
        return List.of();
    }

    public List<String> loadLines() throws IOException {
        return List.of();
    }

    public void save(List<Task> tasks) throws IOException {
        // no-op for now (Level-7/Storage can replace this later)
    }

    public String getFilePath() {
        return filePath;
    }
}

