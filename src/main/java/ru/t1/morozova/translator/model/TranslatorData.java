package ru.t1.morozova.translator.model;

import javax.ws.rs.FormParam;
import java.util.UUID;

public final class TranslatorData {

    private String id = UUID.randomUUID().toString();

    @FormParam("text")
    private String sourceText;

    private String targetText;

    @FormParam("sourceLang")
    private String languageFrom;

    @FormParam("targetLang")
    private String languageTo;

    public TranslatorData() {
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getSourceText() {
        return sourceText;
    }

    public String getLanguageFrom() {
        return languageFrom;
    }

    public String getLanguageTo() {
        return languageTo;
    }

    public void setTargetText(final String targetText) {
        this.targetText = targetText;
    }

    public String getTargetText() {
        return targetText;
    }

}
