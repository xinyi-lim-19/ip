package duke;

public abstract class Task {
    protected final String description;
    protected boolean isDone;

    protected Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    public boolean isDone() { return isDone; }
    public void markDone() { this.isDone = true; }
    public void markUndone() { this.isDone = false; }

    /** e.g., "T | 1 | read book" */
    public abstract String serialize();

    @Override
    public String toString() {
         return "[" + (isDone ? "X" : " ") + "] " + description;
    }

}

