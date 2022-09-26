package ru.t1.morozova.translator.service;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;
import ru.t1.morozova.translator.model.RequestData;
import ru.t1.morozova.translator.model.TranslatorData;
import ru.t1.morozova.translator.model.Word;

import javax.enterprise.context.ApplicationScoped;
import java.time.Duration;
import java.util.List;

@ApplicationScoped
public class SaveRequestService {

    public void saveInDB(
            final PgPool client,
            final RequestData requestData,
            final TranslatorData translatorData,
            Uni<List<Word>> words
    ) {

        Uni.combine().all().unis(
                        insertRequestData(client, requestData),
                        insertTranslatorData(client, translatorData),
                        insertWords(client, words)
                )
                .asTuple()
                .await().atMost(Duration.ofSeconds(5));

        /*Multi.createFrom().iterable(words)
                .onItem()
                .transformToUniAndMerge((word) -> insertWords(client, word)).collect();*/

    }

    private Uni<RowSet<Row>> insertRequestData(final PgPool client, final RequestData requestData) {
        return client
                .preparedQuery("INSERT INTO requestdata (id, circulationdata, ipaddr)" +
                        "VALUES ($1, $2, $3)")
                .execute(Tuple.of(
                        requestData.getId(),
                        requestData.getDate(),
                        requestData.getIpAddr())
                );
    }

    private Uni<RowSet<Row>> insertTranslatorData(final PgPool client, final TranslatorData data) {
        return client
                .preparedQuery("INSERT INTO translatordata (id, languagefrom, languageto, sourcetext, targettext)" +
                        "VALUES ($1, $2, $3, $4, $5)")
                .execute(Tuple.of(
                        data.getId(),
                        data.getLanguageFrom(),
                        data.getLanguageTo(),
                        data.getSourceText(),
                        data.getTargetText())
                );
    }

    private Uni<List<Word>> insertWords(final PgPool client, final Uni<List<Word>> words) {
        return words.onItem().invoke((words1) -> {
            for (Word word : words1) {
                client
                        .preparedQuery("INSERT INTO word (id, originalword, translateword, request_id)" +
                                "VALUES ($1, $2, $3, $4)")
                        .execute(Tuple.of(
                                word.getId(),
                                word.getOriginalWord(),
                                word.getTranslateWord(),
                                word.getRequest().getId())
                        ).await().atMost(Duration.ofSeconds(3));
            }
        });
    }

}
