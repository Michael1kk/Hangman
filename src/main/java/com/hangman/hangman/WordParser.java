package com.hangman.hangman;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class WordParser {
    // Объявление и инициализация необходимых свойств класса
    private static final List<String> alphabet = Arrays.asList("а", "б", "в", "г", "д", "е", "ё", "ж", "з", "и", "й",
            "к", "л", "м", "н", "о", "п", "р", "с", "т", "у", "ф", "х", "ц", "ч", "ш", "щ", "э", "ю", "я");
    private static final Random randomizer = new Random();

    // Парсинг слова онлайн

    /**
     * Функция парсинга в онлайн базе данных
     *
     * @param range список чисел, отвечающих за длину слова, которая выбирается случайно
     * @return слово, которое нужно отгадать игроку
     * @throws IOException при неудачном соединении с базой данных
     */
    protected static String findWord(List<Integer> range) throws IOException {
        List<String> wordList = new ArrayList<>();

        int length = range.get(randomizer.nextInt(range.size()));
        String letter = alphabet.get(randomizer.nextInt(alphabet.size()));
        String url = String.format("https://поиск-слов.рф/suschestvitelnye/%d/%s", length, letter);

        int wordsCount = 0;
        Document data = Jsoup.connect(url).get();
        Elements container = data.select("div.info-view");

        for (Element element : container) {
            String[] wordsInfo = element.ownText().split(" ");
            wordsCount = Integer.parseInt(wordsInfo[1]);
        }

        Elements words;
        if (wordsCount > 80) {
            Document page = Jsoup.connect(String.format("https://поиск-слов.рф/suschestvitelnye/%d/%s?page=%d",
                    length, letter, randomizer.nextInt(wordsCount / 3) + 1)).get();
            words = page.select("div.row");
        } else words = data.select("div.row");

        for (Element word : words) wordList.add(word.text());

        return wordList.get(randomizer.nextInt(wordList.size()));
    }
}
