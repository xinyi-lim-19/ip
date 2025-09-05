package duke;

import java.io.IOException;
import java.util.List;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public List<String> load() throws IOException {
        // placeholder: implement real persistence later
        return List.of();
    }

    public void save(List<String> lines) throws IOException {
        // placeholder: implement real persistence later
    }

    public String getFilePath() {
        return filePath;
    }
}
