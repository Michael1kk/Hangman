package com.hangman.hangman;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class StartController {
    // Описание и инициализация необходимых свойств контроллера
    private final Stage playground = new Stage();
    private final Alert alert = new Alert(Alert.AlertType.ERROR);

    // Кнопка начала игры на лёгкой сложности

    /**
     * Функция обработки событий при нажатии кнопки, которая создаёт окно игры на лёгкой сложности
     */
    @FXML
    private void handleEasyBtn() {
        MainController.gameComplexity = 1;

        if (isNetAvailable()) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));

                Start.complexityChoice.close();
                playground.setScene(new Scene(fxmlLoader.load(), 589, 500));
                playground.setResizable(false);
                playground.setTitle("Виселица - Лёгкий уровень сложности");
                playground.getIcons().add(new Image("icon.png"));
                playground.show();
            } catch (IOException e) {
                alert.setTitle("Ошибка");
                alert.setHeaderText("Произошла ошибка при запуске окна. Попробуйте ещё раз");
                ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("icon.png"));
                alert.show();
            }
        } else {
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не удалось подключиться к Интернету");
            alert.setContentText("Проверьте подключение и попробуйте ещё раз");
            ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("icon.png"));
            alert.show();
        }
    }

    // Кнопка начала игры на средней сложности

    /**
     * Функция обработки событий при нажатии кнопки, которая создаёт окно игры на средней сложности
     */
    @FXML
    private void handleMediumBtn() {
        MainController.gameComplexity = 2;

        if (isNetAvailable()) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));

                Start.complexityChoice.close();
                playground.setScene(new Scene(fxmlLoader.load(), 589, 500));
                playground.setResizable(false);
                playground.setTitle("Виселица - Средний уровень сложности");
                playground.getIcons().add(new Image("icon.png"));
                playground.show();
            } catch (IOException ignored) {
                alert.setTitle("Ошибка");
                alert.setHeaderText("Произошла ошибка при запуске окна. Попробуйте ещё раз");
                ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("icon.png"));
                alert.show();
            }
        } else {
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не удалось подключиться к Интернету");
            alert.setContentText("Проверьте подключение и попробуйте ещё раз");
            ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("icon.png"));
            alert.show();
        }
    }

    // Кнопка начала игры на трудной сложности

    /**
     * Функция обработки событий при нажатии кнопки, которая создаёт окно игры на трудной сложности
     */
    @FXML
    private void handleHardBtn() {
        MainController.gameComplexity = 3;

        if (isNetAvailable()) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));

                Start.complexityChoice.close();
                playground.setScene(new Scene(fxmlLoader.load(), 589, 500));
                playground.setResizable(false);
                playground.setTitle("Виселица - Сложный уровень сложности");
                playground.getIcons().add(new Image("icon.png"));
                playground.show();
            } catch (IOException e) {
                alert.setTitle("Ошибка");
                alert.setHeaderText("Произошла ошибка при запуске окна. Попробуйте ещё раз");
                ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("icon.png"));
                alert.show();
            }
        } else {
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не удалось подключиться к Интернету");
            alert.setContentText("Проверьте подключение и попробуйте ещё раз");
            ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("icon.png"));
            alert.show();
        }
    }

    // Проверка подключения игрока к Интернету

    /**
     * Функция проверки подключение игрока к Интернету для парсинга слова
     *
     * @return true, если игрок подключён к Интернету, иначе false
     */
    private boolean isNetAvailable() {
        try {
            URL url = new URL("https://ya.ru");
            URLConnection connection = url.openConnection();
            connection.connect();

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}