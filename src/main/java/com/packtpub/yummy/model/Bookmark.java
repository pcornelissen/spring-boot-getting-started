package com.packtpub.yummy.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Bookmark {
    private final String url;
    private final UUID uuid;

    public Bookmark(String url) {
        this.url = url;
        uuid=null;
    }

    @JsonCreator
    public Bookmark(@JsonProperty("url") String url,@JsonProperty("uuid") UUID uuid) {
        this.url = url;
        this.uuid=uuid;
    }

    public String getUrl() {
        return url;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Bookmark withUuid(UUID uuid) {
        return new Bookmark(url,uuid);
    }
}