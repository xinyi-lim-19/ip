package duke;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public final class DateTimeUtil {
    private DateTimeUtil() {}

    // Accept: "yyyy-MM-dd HHmm", "yyyy-MM-dd", "d/M/yyyy HHmm", "d/M/yyyy"
    private static final List<DateTimeFormatter> DT = List.of(
        DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),
        DateTimeFormatter.ofPattern("d/M/yyyy HHmm")
    );
    private static final List<DateTimeFormatter> D = List.of(
        DateTimeFormatter.ofPattern("yyyy-MM-dd"),
        DateTimeFormatter.ofPattern("d/M/yyyy")
    );

    public static LocalDateTime parseFlexible(String s) {
        String x = s.trim();
        for (var f : DT) { try { return LocalDateTime.parse(x, f); } catch (DateTimeParseException ignore) {} }
        for (var f : D)  { try { return LocalDate.parse(x, f).atStartOfDay(); } catch (DateTimeParseException ignore) {} }
        throw new DateTimeParseException("Unrecognized date/time", x, 0);
    }

    private static final DateTimeFormatter OUT_DATE = DateTimeFormatter.ofPattern("MMM d yyyy");
    private static final DateTimeFormatter OUT_DT   = DateTimeFormatter.ofPattern("MMM d yyyy, h:mma");

    public static String pretty(LocalDateTime dt) {
        return dt.toLocalTime().equals(LocalTime.MIDNIGHT) ? dt.toLocalDate().format(OUT_DATE)
                                                            : dt.format(OUT_DT);
    }
}

