package duke;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

class DateTimeUtilTest {

    @Test
    void parseFlexible_acceptsMultipleFormats() {
        assertEquals(
                LocalDateTime.of(2019, 12, 2, 18, 0),
                DateTimeUtil.parseFlexible("2019-12-02 1800")
        );
        assertEquals(
                LocalDateTime.of(2019, 12, 2, 0, 0),
                DateTimeUtil.parseFlexible("2019-12-02")
        );
        assertEquals(
                LocalDateTime.of(2019, 12, 2, 18, 0),
                DateTimeUtil.parseFlexible("2/12/2019 1800")
        );
        assertEquals(
                LocalDate.of(2019, 12, 2).atTime(LocalTime.MIDNIGHT),
                DateTimeUtil.parseFlexible("2/12/2019")
        );
    }

    @Test
    void pretty_rendersMidnightAsDate_andOthersAsDateTime() {
        LocalDateTime midnight = LocalDate.of(2019, 12, 2).atStartOfDay();
        String prettyMidnight = DateTimeUtil.pretty(midnight);
        assertTrue(prettyMidnight.equals("Dec 2 2019") || prettyMidnight.equals("Dec 2 2019")); 

        LocalDateTime evening = LocalDateTime.of(2019, 12, 2, 18, 0);
        String prettyEvening = DateTimeUtil.pretty(evening);
        assertTrue(prettyEvening.contains("Dec 2 2019") && prettyEvening.contains("6:00"));
    }
}

