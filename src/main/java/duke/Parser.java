package duke;

public class Parser {

    public static String parse(String input) {
        return input == null ? "" : input.trim();
    }

    public static String commandWord(String fullCommand) {
        String trimmed = parse(fullCommand);
        if (trimmed.isEmpty()) return "";
        int sp = trimmed.indexOf(' ');
        return sp == -1 ? trimmed : trimmed.substring(0, sp);
    }

    public static String args(String fullCommand) {
        String trimmed = parse(fullCommand);
        int sp = trimmed.indexOf(' ');
        return sp == -1 ? "" : trimmed.substring(sp + 1);
    }
}

