package duke;

public class Event extends Task {
    private String timeslot; 
    public Event(final String description, final boolean isDone, final String timeslot) {
        super(description, isDone);
        this.timeslot = timeslot;
    }
    public String getTimeslot()
    {
	return timeslot;
    }
    public void setTimeslot(final String timeslot) 
    {
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

