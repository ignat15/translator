package ru.t1.morozova.translator.model;

import java.time.OffsetDateTime;
import java.util.UUID;

public final class RequestData {

    private String id = UUID.randomUUID().toString();

    private OffsetDateTime date = OffsetDateTime.now();

    private String ipAddr;

    public RequestData() {
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public OffsetDateTime getDate() {
        return date;
    }

    public void setDate(final OffsetDateTime date) {
        this.date = date;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(final String ipAddr) {
        this.ipAddr = ipAddr;
    }

}
