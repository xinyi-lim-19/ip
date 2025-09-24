package duke;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Utilities for parsing and formatting dates/times for the chatbot.
 */
public final class DateTimeUtil
{
    /**
     * Parses a date/time string in one of several accepted formats into a LocalDateTime.
     *
     * @param s date/time text.
     * @return parsed LocalDateTime.
     * @throws java.time.format.DateTimeParseException if not in an accepted format.
     */
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

