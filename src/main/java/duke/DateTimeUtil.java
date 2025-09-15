package duke;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public final class DateTimeUtil
{
    private DateTimeUtil()
    {
    }

    // Accept: "yyyy-MM-dd HHmm", "yyyy-MM-dd", "d/M/yyyy HHmm", "d/M/yyyy"
    private static final List<DateTimeFormatter> DT = List.of(
        DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),
        DateTimeFormatter.ofPattern("d/M/yyyy HHmm")
    );

    private static final List<DateTimeFormatter> D = List.of(
        DateTimeFormatter.ofPattern("yyyy-MM-dd"),
        DateTimeFormatter.ofPattern("d/M/yyyy")
    );

    public static LocalDateTime parseFlexible(final String s)
    {
        final String x = s.trim();

        for (final DateTimeFormatter f : DT)
        {
            try
            {
                return LocalDateTime.parse(x, f);
            }
            catch (final DateTimeParseException ignore)
            {
                // continue
            }
        }

        for (final DateTimeFormatter f : D)
        {
            try
            {
                return LocalDate.parse(x, f).atStartOfDay();
            }
            catch (final DateTimeParseException ignore)
            {
                // continue
            }
        }

        throw new DateTimeParseException("Unrecognized date/time", x, 0);
    }

    private static final DateTimeFormatter OUT_DATE = DateTimeFormatter.ofPattern("MMM d yyyy");
    private static final DateTimeFormatter OUT_DT = DateTimeFormatter.ofPattern("MMM d yyyy, h:mma");

    public static String pretty(final LocalDateTime dt)
    {
        if (dt.toLocalTime().equals(LocalTime.MIDNIGHT))
        {
            return dt.toLocalDate().format(OUT_DATE);
        }
        else
        {
            return dt.format(OUT_DT);
        }
    }
}

