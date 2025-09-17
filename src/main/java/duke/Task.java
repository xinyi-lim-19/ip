package duke;

public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    // --- getter ---
    public String getDescription() {
        return description;
    }

    // --- setter ---
    public void setDescription(final String newDesc) {
	this.description = newDesc;
    }

    public boolean isDone() {
        return isDone;
    }

    // --- mutators ---
    public void mark() {
        this.isDone = true;
    }

    public void unmark() {
        this.isDone = false;
    }

    protected String statusIcon() {
        return isDone ? "X" : " ";
    }

    @Override
    public String toString() {
        return "[" + statusIcon() + "] " + description;
    }

    // Each subclass must define its own save format
    public abstract String serialize();
}

