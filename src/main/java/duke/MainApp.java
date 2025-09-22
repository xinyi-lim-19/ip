package duke;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application
{
    private DukeCore core;

    @Override
    public void start(final Stage stage)
    {
        core = new DukeCore();

        final TextArea display = new TextArea();
        display.setEditable(false);
        display.appendText(core.welcome() + "\n");

        final TextField input = new TextField();
        input.setPromptText("Type a command (e.g., 'todo read book', 'list', 'bye')");
        final Button send = new Button("Send");

        final HBox bottom = new HBox(8, input, send);
        final VBox root = new VBox(8, display, bottom);
        root.setStyle("-fx-padding: 12;");

        // submit on click
        send.setOnAction(e -> {
            final String line = input.getText().trim();
            if (line.isEmpty())
            {
                return;
            }
            display.appendText(">> " + line + "\n");
            final String reply = core.reply(line);
            display.appendText(reply + "\n");
            input.clear();

            if (core.isExit(line))
            {
                Platform.exit();
            }
        });

        // submit on Enter
        input.setOnAction(send.getOnAction());

        stage.setTitle(AppInfo.APP_NAME);
        stage.setScene(new Scene(root, 560, 380));
        stage.show();
    }
}

