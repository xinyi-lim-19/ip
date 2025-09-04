package duke;

import java.time.LocalDateTime;

public class Deadline extends Task {
    private final LocalDateTime by;

    public Deadline(String description, boolean isDone, LocalDateTime by) {
        super(description, isDone);
        this.by = by;
    }

    public LocalDateTime getBy() { return by; }

    @Override
    public String serialize() {
        // Save as ISO for easy reload (e.g., 2019-12-02T18:00)
        return String.format("D | %d | %s | %s", isDone ? 1 : 0, description, by);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + DateTimeUtil.pretty(by) + ")";
    }
}


