module com.hangman.hangman {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jsoup;


    opens com.hangman.hangman to javafx.fxml;
    exports com.hangman.hangman;
}