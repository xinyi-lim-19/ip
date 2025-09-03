package duke;

import java.io.IOException;
import java.util.Scanner;

public class Duke {
    private final Storage storage;
    private final TaskList tasks;

    public Duke() {
        storage = new Storage();
        tasks = new TaskList(storage);
        try {
            // Load saved tasks from duke.txt into the task list
            ParserUtil.loadInto(storage.load(), tasks);
        } catch (IOException e) {
            System.err.println("Warning: could not load saved tasks: " + e.getMessage());
        }
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Hello! I'm Duke");
        System.out.println("What can I do for you?");

        while (true) {
            String line = sc.nextLine().trim();
            if (line.equalsIgnoreCase("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            } else if (line.equalsIgnoreCase("list")) {
                int i = 1;
                for (Task t : tasks.asList()) {
                    System.out.println(i + ". " + t);
                    i++;
                }
            } else if (line.startsWith("todo ")) {
                String desc = line.substring(5).trim();
                try {
                    tasks.add(new Todo(desc, false));
                    System.out.println("Added: " + desc);
                } catch (IOException e) {
                    System.err.println("Failed to save after adding: " + e.getMessage());
                }
            } else {
                System.out.println("Sorry, I don't understand that command.");
            }
        }
        sc.close();
    }

    public static void main(String[] args) {
        new Duke().run();
    }
}

