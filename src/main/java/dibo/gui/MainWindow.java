package dibo.gui;

import dibo.Dibo;
import dibo.ui.Ui;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Dibo dibo;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image diboImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        showGreeting();
    }

    /** Injects the Duke instance */
    public void setDibo(Dibo d) {
        dibo = d;
    }

    private void showGreeting() {
        Ui tempUi = new Ui();
        tempUi.showWelcome();
        String greeting = tempUi.returnOutput();

        dialogContainer.getChildren().add(
                DialogBox.getDiboDialog(greeting, diboImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = dibo.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDiboDialog(response, diboImage)
        );
        userInput.clear();

        if (input.trim().equalsIgnoreCase("bye")) {
            closeAppWithDelay();
        }
    }

    /**
     * Closes the application after a 3-second delay.
     */
    private void closeAppWithDelay() {
        // Disable input to prevent further commands
        userInput.setDisable(true);
        sendButton.setDisable(true);

        // Create a timeline to close the app after 3 seconds
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(3),
                e -> Platform.exit()
        ));
        timeline.play();
    }
}

