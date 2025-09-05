package duke;

import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private final List<Object> items = new ArrayList<>();

    public TaskList() {}

    public int size() { return items.size(); }
    public List<Object> asList() { return items; }

    // Add real methods later (add/delete/mark/unmark) once tasks are extracted
}
