package duke;

public class Ui {
    private static final String LINE = "____________________________________________________________";

    public void showWelcome() {
        System.out.println(LINE);
        System.out.println(" Hello! I'm Duke");
        System.out.println(" What can I do for you?");
        System.out.println(LINE);
    }

    public void showLine() {
        System.out.println(LINE);
    }

    public void showMessage(String msg) {
        System.out.println(msg);
    }

    public void showError(String msg) {
        showLine();
        System.out.println(" " + msg);
        showLine();
    }

    public void showBye() {
        showLine();
        System.out.println(" Bye. Hope to see you again soon!");
        showLine();
    }
}
