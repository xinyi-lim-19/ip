package duke;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Bob {
    private static final String LINE = "____________________________________________________________";

    private static class Task {
        private final String description;
        private boolean isDone;

        Task(String description) {
            this.description = description;
            this.isDone = false;
        }

        void mark()   { this.isDone = true; }
        void unmark() { this.isDone = false; }

        String getStatusIcon() {
            return (isDone ? "X" : " ");
        }

        @Override
        public String toString() {
            return "[" + getStatusIcon() + "] " + description;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // Logo (keep your existing BOB ASCII)
        String logo = " ____   ___   ____  \n"
                    + "| __ ) / _ \\ | __ ) \n"
                    + "|  _ \\| | | ||  _ \\ \n"
                    + "| |_) | |_| || |_) |\n"
                    + "|____/ \\___/ |____/ \n";
        System.out.println("Hello from\n" + logo);

        // Greet
        System.out.println(LINE);
        System.out.println(" Hello! I'm Bob");
        System.out.println(" What can I do for you?");
        System.out.println(LINE);

        // Storage: fixed-size as allowed by spec
        Task[] tasks = new Task[100];
        int size = 0;

        while (true) {
            String input = br.readLine();
            if (input == null) {
                // EOF -> exit politely
                System.out.println(LINE);
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println(LINE);
                break;
            }

            String cmd = input.trim();

            if (cmd.equals("bye")) {
                System.out.println(LINE);
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println(LINE);
                break;
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
                } else {
                    errorOutOfRange(size);
                }
            } else if (cmd.startsWith("unmark ")) {
                int idx = parseIndex(cmd.substring(7));
                if (idx >= 1 && idx <= size) {
                    tasks[idx - 1].unmark();
                    System.out.println(LINE);
                    System.out.println(" OK, I've marked this task as not done yet:");
                    System.out.println("   " + tasks[idx - 1]);
                    System.out.println(LINE);
                } else {
                    errorOutOfRange(size);
                }
            } else {
                // Add new task
                if (size < 100) {
                    tasks[size++] = new Task(cmd);
                    System.out.println(LINE);
                    System.out.println(" added: " + cmd);
                    System.out.println(LINE);
                } else {
                    System.out.println(LINE);
                    System.out.println(" Sorry, I can only store up to 100 items.");
                    System.out.println(LINE);
                }
            }
        }
    }

    private static int parseIndex(String s) {
        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void errorOutOfRange(int size) {
        System.out.println(LINE);
        System.out.println(" Invalid index. Use a number between 1 and " + size + ".");
        System.out.println(LINE);
    }
}

