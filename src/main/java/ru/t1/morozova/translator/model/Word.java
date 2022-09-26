package ru.t1.morozova.translator.model;

import java.util.UUID;

public final class Word {

    private final String id = UUID.randomUUID().toString();

    private final String originalWord;

    private final String translateWord;

    private TranslatorData request;

    public Word(final String originalWord, final String translateWord) {
        this.originalWord = originalWord;
        this.translateWord = translateWord;
    }

    public String getId() {
        return id;
    }

    public String getOriginalWord() {
        return originalWord;
    }

    public String getTranslateWord() {
        return translateWord;
    }

    public TranslatorData getRequest() {
        return request;
    }

    public void setRequest(final TranslatorData request) {
        this.request = request;
    }

}
