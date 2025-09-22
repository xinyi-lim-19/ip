package duke;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DukeCoreTest {

    @Test
    void reply_todoAndList_roundTrip() {
        DukeCore core = new DukeCore();

        String r1 = core.reply("todo read book");
        assertTrue(r1.contains("[T][ ] read book"));

        String list = core.reply("list");
        assertTrue(list.contains("1. [T][ ] read book"));
    }

    @Test
    void reply_deadline_parsesAndPrettyPrints() {
        DukeCore core = new DukeCore();

        String r1 = core.reply("deadline return book /by 2019-12-02 1800");
        // Should include pretty date (Dec 2 2019, 6:00PM) somewhere
        assertTrue(r1.contains("return book"));
        // Don’t over-specify formatting; just check parts
        String list = core.reply("list");
        assertTrue(list.contains("return book"));
        assertTrue(list.contains("Dec 2 2019")); // pretty date part
    }
}

