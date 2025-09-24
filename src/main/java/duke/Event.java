package duke;

public class Event extends Task {
    private String timeslot;   // not final (DukeCore mutates)

    public Event(String description, boolean isDone, String timeslot) {
        super(description, isDone);
        this.timeslot = timeslot;
    }

    public String getTimeslot() { return timeslot; }
    public void setTimeslot(String timeslot) { this.timeslot = timeslot; }

    @Override
    public String serialize() {
        return String.format("E | %d | %s | %s", isDone ? 1 : 0, description, timeslot);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: " + timeslot + ")";
    }
}

