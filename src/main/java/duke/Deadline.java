package duke;

import java.time.LocalDateTime;

public class Deadline extends Task {
    private LocalDateTime by;

    public Deadline(String description, boolean isDone, LocalDateTime by) {
        super(description, isDone);
        this.by = by;
    }

    public LocalDateTime getBy() { return by; }
    public void setBy(LocalDateTime newBy) { this.by = newBy; }   // ← add this

    @Override
    public String serialize() {
        // store ISO for persistence
        return String.format("D | %d | %s | %s", isDone ? 1 : 0, description, by);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + DateTimeUtil.pretty(by) + ")";
    }
}

