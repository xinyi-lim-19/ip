package duke;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskList
{
    private final List<Task> tasks = new ArrayList<>();
    private final Storage storage; // may be null

    public TaskList()
    {
        this.storage = null;
    }

    public TaskList(final Storage storage)
    {
        this.storage = storage;
    }

    public int size()
    {
        return tasks.size();
    }

    public List<Task> asList()
    {
        return Collections.unmodifiableList(tasks);
    }

    public Task get(final int index)
    {
        return tasks.get(index);
    }

    public void add(final Task t) throws IOException
    {
        tasks.add(t);
        save();
    }

    /** Add without triggering save (useful for initial load). */
    public void addSilently(final Task t)
    {
        tasks.add(t);
    }

    public Task remove(final int index) throws IOException
    {
        final Task removed = tasks.remove(index);
        save();
        return removed;
    }

    /** Persist the current list if storage is present; otherwise no-op. */
    public void save() throws IOException
    {
        if (storage != null)
        {
            storage.save(tasks);
        }
    }


