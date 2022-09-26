package ru.t1.morozova.translator.controller;

import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.mutiny.pgclient.PgPool;
import org.jboss.resteasy.reactive.MultipartForm;
import ru.t1.morozova.translator.model.RequestData;
import ru.t1.morozova.translator.model.TranslatorData;
import ru.t1.morozova.translator.model.Word;
import ru.t1.morozova.translator.service.SaveRequestService;
import ru.t1.morozova.translator.service.TranslationService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("translate.html")
public class TranslatorController {

    @Inject
    PgPool client;

    @Inject
    SaveRequestService requestService;

    @POST
    @Blocking
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Uni<Response> translate(@MultipartForm TranslatorData data, @MultipartForm RequestData request, @Context HttpServerRequest cx) {

        final var ipAddress = cx.remoteAddress().hostAddress();
        request.setIpAddr(ipAddress);

        data.setId(request.getId());

        var words = Uni.createFrom().item(TranslationService.translateText(data)).onItem().transform(words1 -> {
            for (final Word word : words1) {
                word.setRequest(data);
            }
            return words1;
        });

        requestService.saveInDB(client, request, data, words);

        return Uni.createFrom().item(Response
                .status(Response.Status.OK)
                .entity(data.getTargetText())
                .build());
    }

}