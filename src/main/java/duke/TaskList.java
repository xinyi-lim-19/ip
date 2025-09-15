package duke;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskList
{
    private final List<Task> tasks = new ArrayList<>();
    private final Storage storage; // may be null

    public TaskList(final Storage storage)
    {
        this.storage = storage;
    }

    public TaskList()
    {
        this.storage = null;
    }

    public int size()
    {
        return tasks.size();
    }

    public List<Task> asList()
    {
        return Collections.unmodifiableList(tasks);
    }

    public void add(final Task t) throws IOException
    {
        tasks.add(t);
        if (storage != null) {
            storage.save(tasks);
        }
    }

    public void addSilently(final Task t)
    {
        tasks.add(t);
    }

    // Helpers you will likely need soon:
    public Task get(final int index)
    {
        return tasks.get(index);
    }

    public Task remove(final int index) throws IOException
    {
        final Task removed = tasks.remove(index);
        if (storage != null) {
            storage.save(tasks);
        }
        return removed;
    }
}

