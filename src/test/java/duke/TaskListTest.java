package duke;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskListTest {

    @Test
    void add_increasesSize() throws Exception {
        TaskList list = new TaskList();
        int oldSize = list.size();
        list.addSilently(new Todo("read book"));
        assertEquals(oldSize + 1, list.size());
    }
}

