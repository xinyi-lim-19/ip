package duke;

public class Event extends Task {
    private final String timeslot; // simple "start/end" string; refine later if you wish
    public Event(String description, boolean isDone, String timeslot) {
        super(description, isDone);
        this.timeslot = timeslot;
    }
    @Override public String serialize() {
        return String.format("E | %d | %s | %s", isDone ? 1 : 0, description, timeslot);
    }
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: " + timeslot + ")";
    }

}

