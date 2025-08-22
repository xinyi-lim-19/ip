package duke;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Bob {
    private static final String LINE = "____________________________________________________________";

    // ===== Model =====
    private static class Task {
        protected final String description;
        protected boolean isDone;

        Task(String description) {
            this.description = description;
            this.isDone = false;
        }
        void mark()   { this.isDone = true; }
        void unmark() { this.isDone = false; }
        String statusIcon() { return isDone ? "X" : " "; }

        @Override
        public String toString() { return "[" + statusIcon() + "] " + description; }
    }
    private static class Todo extends Task {
        Todo(String description) { super(description); }
        @Override public String toString() { return "[T]" + super.toString(); }
    }
    private static class Deadline extends Task {
        private final String by;
        Deadline(String description, String by) { super(description); this.by = by; }
        @Override public String toString() { return "[D]" + super.toString() + " (by: " + by + ")"; }
    }
    private static class Event extends Task {
        private final String from, to;
        Event(String description, String from, String to) { super(description); this.from = from; this.to = to; }
        @Override public String toString() { return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")"; }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        printLogo();
        greet();

        Task[] tasks = new Task[100];
        int size = 0;

        while (true) {
            String input = br.readLine();
            if (input == null) { exit(); break; }
            String cmd = input.trim();

            if (cmd.equals("bye")) {
                exit(); break;

            } else if (cmd.equals("list")) {
                System.out.println(LINE);
                System.out.println(" Here are the tasks in your list:");
                for (int i = 0; i < size; i++) {
                    System.out.println(" " + (i + 1) + "." + tasks[i]);
                }
                System.out.println(LINE);

            } else if (cmd.startsWith("mark ")) {
                int idx = parseIndex(cmd.substring(5));
                if (idx >= 1 && idx <= size) {
                    tasks[idx - 1].mark();
                    System.out.println(LINE);
                    System.out.println(" Nice! I've marked this task as done:");
                    System.out.println("   " + tasks[idx - 1]);
                    System.out.println(LINE);
                } else { errorOutOfRange(size); }

            } else if (cmd.startsWith("unmark ")) {
                int idx = parseIndex(cmd.substring(7));
                if (idx >= 1 && idx <= size) {
                    tasks[idx - 1].unmark();
                    System.out.println(LINE);
                    System.out.println(" OK, I've marked this task as not done yet:");
                    System.out.println("   " + tasks[idx - 1]);
                    System.out.println(LINE);
                } else { errorOutOfRange(size); }

            } else if (cmd.startsWith("todo ")) {
                String desc = cmd.substring(5).trim();
                size = addTask(tasks, size, new Todo(desc));

            } else if (cmd.startsWith("deadline ")) {
                String rest = cmd.substring(9).trim();
                String[] parts = splitOnce(rest, "/by");
                String desc = parts[0].trim();
                String by   = parts.length > 1 ? parts[1].trim() : "";
                size = addTask(tasks, size, new Deadline(desc, by));

            } else if (cmd.startsWith("event ")) {
                String rest = cmd.substring(6).trim();
                String[] p1 = splitOnce(rest, "/from");
                String desc = p1[0].trim();
                String afterFrom = p1.length > 1 ? p1[1].trim() : "";
                String[] p2 = splitOnce(afterFrom, "/to");
                String from = p2[0].trim();
                String to   = (p2.length > 1 ? p2[1] : "").trim();
                size = addTask(tasks, size, new Event(desc, from, to));

            } else {
                // keep Level-2 behavior for bare text: treat as Todo (typed)
                size = addTask(tasks, size, new Todo(cmd));
            }
        }
    }

    // ===== UI helpers =====
    private static void printLogo() {
        String logo = " ____   ___   ____  \n"
                    + "| __ ) / _ \\ | __ ) \n"
                    + "|  _ \\| | | ||  _ \\ \n"
                    + "| |_) | |_| || |_) |\n"
                    + "|____/ \\___/ |____/ \n";
        System.out.println("Hello from\n" + logo);
    }
    private static void greet() {
        System.out.println(LINE);
        System.out.println(" Hello! I'm Bob");
        System.out.println(" What can I do for you?");
        System.out.println(LINE);
    }
    private static void exit() {
        System.out.println(LINE);
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println(LINE);
    }

    // ===== logic helpers =====
    private static int addTask(Task[] tasks, int size, Task t) {
        if (size >= tasks.length) {
            System.out.println(LINE);
            System.out.println(" Sorry, I can only store up to " + tasks.length + " items.");
            System.out.println(LINE);
            return size;
        }
        tasks[size++] = t;
        System.out.println(LINE);
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + t);
        System.out.println(" Now you have " + size + " tasks in the list.");
        System.out.println(LINE);
        return size;
    }
    private static int parseIndex(String s) {
        try { return Integer.parseInt(s.trim()); } catch (NumberFormatException e) { return -1; }
    }
    private static String[] splitOnce(String text, String token) {
        int i = indexOfToken(text, token);
        if (i < 0) return new String[]{text, ""};
        String left = text.substring(0, i);
        String right = text.substring(i + token.length()).trim();
        return new String[]{left, right};
    }
    private static int indexOfToken(String text, String token) {
        int i = text.indexOf(" " + token + " ");
        if (i >= 0) return i + 1;
        i = text.indexOf(" " + token);
        if (i >= 0) return i + 1;
        i = text.indexOf(token + " ");
        if (i >= 0) return i;
        return text.indexOf(token);
    }
    private static void errorOutOfRange(int size) {
        System.out.println(LINE);
        System.out.println(" Invalid index. Use a number between 1 and " + size + ".");
        System.out.println(LINE);
    }
}
