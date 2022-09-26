package ru.t1.morozova.translator.service;

import ru.t1.morozova.translator.model.TranslatorData;
import ru.t1.morozova.translator.model.Word;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TranslationService {

    public static List<Word> translateText(final TranslatorData request) {
        final List<String> target = new ArrayList<>();
        final List<Word> words = splitWords(request.getLanguageFrom(), request.getLanguageTo(), request.getSourceText());
        for (final Word word : words) {
            target.add(word.getTranslateWord());
        }
        final String listString = String.join(" ", target);
        request.setTargetText(listString);
        return words;
    }

    private static List<Word> splitWords(final String langFrom, final String langTo, final String text) {

        final List<Word> words = new ArrayList<>();
        final var myList = Arrays.asList(text.split(" "));
        try {
            for (final String word : myList) {
                words.add(new Word(word, translate(langFrom, langTo, word)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }

    private static String translate(final String langFrom, final String langTo, final String text) throws IOException {
        final String urlStr = "https://script.google.com/macros/s/AKfycbyzkK6bFyLGCmQ2vyUEveuTiRGX8rFgw59hfIAx1d-eCmlioiB_qApe9pyFDbejgvB5ng/exec" +
                "?q=" + URLEncoder.encode(text, "UTF-8") +
                "&target=" + langTo +
                "&source=" + langFrom;
        final URL url = new URL(urlStr);
        final StringBuilder response = new StringBuilder();
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        final BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

}
