package dibo.gui;

import java.io.IOException;

import dibo.Dibo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    private Dibo dibo = new Dibo();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("Dibo Chatbot");
            fxmlLoader.<MainWindow>getController().setDibo(dibo);  // inject the Dibo instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

