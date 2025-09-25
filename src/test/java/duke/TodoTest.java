package duke;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TodoTest {

    @Test
    void serialize_returnsCorrectFormat() {
        Todo todo = new Todo("read book", true);
        assertEquals("T | 1 | read book", todo.serialize());
    }
}

