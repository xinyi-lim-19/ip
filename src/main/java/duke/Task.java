package duke;

public abstract class Task {
    protected String description;   // not final (DukeCore mutates)
    protected boolean isDone;

    protected Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    public String getDescription() { return description; }
    public void setDescription(String newDesc) { this.description = newDesc; }

    public boolean isDone() { return isDone; }

    // canonical mutators
    public void markDone()   { this.isDone = true; }
    public void markUndone() { this.isDone = false; }

    // compatibility aliases expected by DukeCore
    public void mark()   { markDone(); }
    public void unmark() { markUndone(); }

    /** e.g., "T | 1 | read book" */
    public abstract String serialize();

    @Override
    public String toString() {
        return "[" + (isDone ? "X" : " ") + "] " + description;
    }
}

