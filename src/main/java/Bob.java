package duke;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Bob {
    private static final String LINE = "____________________________________________________________";

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // Print logo once
        String logo = " ____   ___   ____  \n"
                    + "| __ ) / _ \\ | __ ) \n"
                    + "|  _ \\| | | ||  _ \\ \n"
                    + "| |_) | |_| || |_) |\n"
                    + "|____/ \\___/ |____/ \n";
        System.out.println("Hello from\n" + logo);

        // Initial greet
        System.out.println(LINE);
        System.out.println(" Hello! I'm Bob");
        System.out.println(" What can I do for you?");
        System.out.println(LINE);

        while (true) {
            String input = br.readLine();
            if (input == null) { // EOF (Ctrl+D)
                break;
            }
            if (input.equals("bye")) {
                // Exit block
                System.out.println(LINE);
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println(LINE);
                break;
            }

            // Echo block
            System.out.println(LINE);
            System.out.println(" " + input);
            System.out.println(LINE);
        }
    }
}

