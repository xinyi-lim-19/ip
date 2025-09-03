package duke;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private final List<Task> tasks = new ArrayList<>();
    private final Storage storage;

    public TaskList(Storage storage) {
        this.storage = storage;
    }

    /* Used during startup load so we don't save after each insert */
    void addSilently(Task t) {
        tasks.add(t);
    }

    public void add(Task t) throws IOException {
        tasks.add(t);
        storage.save(tasks);
    }

    public Task delete(int index) throws IOException {
        Task removed = tasks.remove(index);
        storage.save(tasks);
        return removed;
    }

    public void mark(int index) throws IOException {
        tasks.get(index).markDone();
        storage.save(tasks);
    }

    public void unmark(int index) throws IOException {
        tasks.get(index).markUndone();
        storage.save(tasks);
    }

    public List<Task> asList() { return tasks; }
}

