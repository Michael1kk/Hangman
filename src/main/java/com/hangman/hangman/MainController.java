package com.hangman.hangman;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MainController implements Initializable {
    // Объявление и инициализация необходимых свойств контроллера
    protected static int gameComplexity, attemptsCount = 0;
    private final Alert info = new Alert(Alert.AlertType.INFORMATION),
            confirm = new Alert(Alert.AlertType.CONFIRMATION);
    ButtonType yes = new ButtonType("Да", ButtonBar.ButtonData.OK_DONE),
            no = new ButtonType("Нет", ButtonBar.ButtonData.CANCEL_CLOSE);
    private String wordToGuess = "";
    private StringBuilder hiddenWord = new StringBuilder();
    @FXML
    protected Label word, attempts;
    @FXML
    protected AnchorPane pane;
    @FXML
    private Line border;
    @FXML
    private Rectangle pillar, beam, rope, body, armRight, armLeft, legRight, legLeft;
    @FXML
    private Circle head;

    // Информация об игре

    /**
     * Функция описания процесса игры
     */
    @FXML
    private void aboutGame() {
        info.setTitle("Об игре");
        info.setHeaderText("""
                Добро пожаловать в игру "Виселица"!
                                
                Суть игры - отгадать слово, которое скрыто знаками вопроса.
                На это даётся определённое количество попыток, в зависимости от уровня сложности.
                Также чем выше сложность, тем слово будет длиннее и будут построены уже несколько частей виселицы.
                За каждую неудачную попытку будет отниматься одна жизнь и строиться одна часть виселицы.
                Если попытки будут закончены и виселица будет полностью построена, то вы считаетесь проигравшим.
                После игры будет предложено два варианта: начать заново с новым загаданным словом или выйти из игры.
                                
                Удачной вам игры!!!
                """);
        ((Stage) info.getDialogPane().getScene().getWindow()).getIcons().add(new Image("icon.png"));
        info.show();
    }

    // Информация о разработчике

    /**
     * Функция описания разработчика игры
     */
    @FXML
    private void aboutDeveloper() {
        info.setTitle("О разработчике");
        info.setHeaderText("""
                Лимонов Михаил Иванович, студент
                Санкт-Петербургского государственного университета телекоммуникаций
                имени профессора М. А. Бонч-Бруевича
                """);
        ((Stage) info.getDialogPane().getScene().getWindow()).getIcons().add(new Image("icon.png"));
        info.show();
    }

    // Перезапуск игры

    /**
     * Функция перезапуска пользователем игры с новым загаданным словом
     */
    @FXML
    private void restart() throws IOException {
        confirm.setTitle("Начать заново");
        confirm.setHeaderText("Вы уверены, что хотите начать игру заново?\nВам будет загадано новое слово.");
        confirm.getButtonTypes().clear();
        confirm.getButtonTypes().addAll(yes, no);
        ((Stage) confirm.getDialogPane().getScene().getWindow()).getIcons().add(new Image("icon.png"));

        Optional<ButtonType> option = confirm.showAndWait();
        if (option.isPresent() && option.get() == yes) drawStart();
    }

    // Выход из игры

    /**
     * Функция выхода пользователя из игры
     */
    @FXML
    private void exit() {
        confirm.setTitle("Выйти из игры");
        confirm.setHeaderText("Вы уверены, что хотите выйти из игры?");
        confirm.getButtonTypes().clear();
        confirm.getButtonTypes().addAll(yes, no);
        ((Stage) confirm.getDialogPane().getScene().getWindow()).getIcons().add(new Image("icon.png"));

        Optional<ButtonType> option = confirm.showAndWait();
        if (option.isPresent() && option.get() == yes) System.exit(0);
    }

    // Отрисовка начала игры

    /**
     * Функция, формирующее игровое окно
     */
    private void drawStart() throws IOException {
        List<Integer> wordLengthRange;

        switch (gameComplexity) {
            case 1: {
                wordLengthRange = Arrays.asList(3, 4, 5, 6);
                wordToGuess = WordParser.findWord(wordLengthRange).toUpperCase();
                hiddenWord = new StringBuilder("?".repeat(wordToGuess.length()));
                attemptsCount = 7;
                break;
            }
            case 2: {
                wordLengthRange = Arrays.asList(7, 8, 9);
                wordToGuess = WordParser.findWord(wordLengthRange).toUpperCase();
                hiddenWord = new StringBuilder("?".repeat(wordToGuess.length()));
                attemptsCount = 5;
                break;
            }
            case 3: {
                wordLengthRange = Arrays.asList(10, 11, 12);
                wordToGuess = WordParser.findWord(wordLengthRange).toUpperCase();
                hiddenWord = new StringBuilder("?".repeat(wordToGuess.length()));
                attemptsCount = 3;
                break;
            }
        }

        word.setText(hiddenWord.toString());
        word.setTextAlignment(TextAlignment.RIGHT);
        word.setAlignment(Pos.CENTER_RIGHT);

        attempts.setText("x" + attemptsCount);
        attempts.setTextAlignment(TextAlignment.RIGHT);
        attempts.setAlignment(Pos.CENTER_RIGHT);
    }

    // Игровые процессы

    /**
     * Функция, изменяющая игровое окно в зависимости от того, отгадал ли игрок букву или нет
     *
     * @param letter буква, которую выбрал игрок
     */
    private void updateGame(String letter) throws IOException {
        boolean checkLetter = wordToGuess.contains(letter);

        if (checkLetter) {
            List<Integer> letterIndexes = new ArrayList<>();
            StringBuilder wordToSearchLetter = new StringBuilder(wordToGuess);

            for (int i = 0; i < wordToSearchLetter.length(); i++)
                if (wordToSearchLetter.charAt(i) == letter.charAt(0)) {
                    letterIndexes.add(i);
                    wordToSearchLetter.replace(i, i + 1, " ");
                }

            for (int index : letterIndexes)
                hiddenWord.replace(index, index + 1, letter);

            if (hiddenWord.toString().toUpperCase().equals(wordToGuess)) {
                word.setText(hiddenWord.toString());

                Alert win = new Alert(Alert.AlertType.INFORMATION);
                win.setTitle("Победа");
                win.setHeaderText("Вы победили: вы смогли отгадать слово! Поздравляем!");
                win.setContentText("Не хотите попробовать ещё раз?");
                win.getButtonTypes().clear();
                win.getButtonTypes().addAll(yes, no);
                ((Stage) win.getDialogPane().getScene().getWindow()).getIcons().add(new Image("icon.png"));

                Optional<ButtonType> option = win.showAndWait();
                if (option.isPresent() && option.get() == yes) drawStart();
                else if (option.isPresent() && option.get() == no) System.exit(0);
            }
        } else if (attemptsCount - 1 == 0) {
            attempts.setText("x0");
            drawParts(gameComplexity, 0);

            Alert gameOver = new Alert(Alert.AlertType.INFORMATION);
            gameOver.setTitle("Проигрыш");
            gameOver.setHeaderText(String.format("""
                    К сожалению, вы проиграли: вы не смогли отгадать слово.
                    Загаданное слово - %s""", wordToGuess));
            gameOver.setContentText("Не хотите попробовать ещё раз?");
            gameOver.getButtonTypes().clear();
            gameOver.getButtonTypes().addAll(yes, no);
            ((Stage) gameOver.getDialogPane().getScene().getWindow()).getIcons().add(new Image("icon.png"));

            Optional<ButtonType> option = gameOver.showAndWait();
            if (option.isPresent() && option.get() == yes) drawStart();
            else if (option.isPresent() && option.get() == no) System.exit(0);
        } else drawParts(gameComplexity, --attemptsCount);

        word.setText(hiddenWord.toString());
        attempts.setText("x" + attemptsCount);
    }

    // Отрисовка частей виселицы

    /**
     * Функция, отрисовывающая в окне части виселицы в зависимости от сложности игры и количества попыток
     *
     * @param complexity сложность игры
     * @param count      количество попыток, оставшихся у игрока
     */
    private void drawParts(int complexity, int count) {
        switch (complexity) {
            case 1:
                switch (count) {
                    case 6:
                        pillar.setVisible(true);
                        break;
                    case 5:
                        beam.setVisible(true);
                        break;
                    case 4:
                        rope.setVisible(true);
                        break;
                    case 3:
                        head.setVisible(true);
                        break;
                    case 2:
                        body.setVisible(true);
                        break;
                    case 1:
                        armRight.setVisible(true);
                        armLeft.setVisible(true);
                        break;
                    case 0:
                        legRight.setVisible(true);
                        legLeft.setVisible(true);
                        break;
                }
                break;
            case 2:
                pillar.setVisible(true);
                beam.setVisible(true);
                switch (count) {
                    case 4:
                        rope.setVisible(true);
                        break;
                    case 3:
                        head.setVisible(true);
                        break;
                    case 2:
                        body.setVisible(true);
                        break;
                    case 1:
                        armRight.setVisible(true);
                        armLeft.setVisible(true);
                        break;
                    case 0:
                        legRight.setVisible(true);
                        legLeft.setVisible(true);
                        break;
                }
                break;
            case 3:
                pillar.setVisible(true);
                beam.setVisible(true);
                rope.setVisible(true);
                switch (count) {
                    case 2:
                        head.setVisible(true);
                        body.setVisible(true);
                        break;
                    case 1:
                        armRight.setVisible(true);
                        armLeft.setVisible(true);
                        break;
                    case 0:
                        legRight.setVisible(true);
                        legLeft.setVisible(true);
                        break;
                }
                break;
        }
    }

    // Создание кнопок с русскими буквами

    /**
     * Функция создания кнопок с буквами, на которые нужно нажимать, чтобы отгадать слово
     *
     * @param alphabet список букв русского алфавита
     * @return список кнопок, которые нужно отобразить на экране
     */
    private List<Button> getButtons(List<String> alphabet) {
        double x = 10, y = border.getLayoutY() + 10;
        List<Button> buttonList = new ArrayList<>();

        for (String letter : alphabet) {
            Button letterBtn = new Button(letter.toUpperCase());
            letterBtn.setLayoutX(x);
            letterBtn.setLayoutY(y);
            letterBtn.setPrefWidth(30);
            letterBtn.setPrefHeight(30);
            letterBtn.setFocusTraversable(false);
            letterBtn.setOnMouseClicked(mouseEvent -> {
                try {
                    updateGame(letter.toUpperCase());
                } catch (IOException ignored) {
                }
            });
            buttonList.add(letterBtn);

            if (x + 54 > 550) {
                x = 10;
                y += 60;
            } else x += 54;
        }
        return buttonList;
    }

    // Отображение начала игры на экране при запуске

    /**
     * Функция отображения начала игры при её запуске в окне
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> fullAlphabet = Arrays.asList("а", "б", "в", "г", "д", "е", "ё", "ж", "з", "и", "й", "к", "л", "м",
                "н", "о", "п", "р", "с", "т", "у", "ф", "х", "ц", "ч", "ш", "щ", "ъ", "ы", "ь", "э", "ю", "я");

        try {
            drawStart();
            drawParts(gameComplexity, attemptsCount);
        } catch (IOException ignored) {
        }

        List<Button> buttonList = getButtons(fullAlphabet);
        pane.getChildren().addAll(buttonList);
    }
}