package com.hangman.hangman;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Start extends Application {
    protected static Stage complexityChoice;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("start.fxml"));
        complexityChoice = stage;
        Scene scene = new Scene(fxmlLoader.load(), 500, 300);

        complexityChoice.setTitle("Виселица");
        complexityChoice.setScene(scene);
        complexityChoice.setResizable(false);
        complexityChoice.getIcons().add(new Image("icon.png"));
        complexityChoice.show();
    }

    public static void main(String[] args) {
        launch();
    }
}