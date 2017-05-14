package com.packtpub.yummy.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Bookmark {
    private String url;
    private UUID uuid;
    private int version;

    @SuppressWarnings("unused")
    public Bookmark(){

    }
    public Bookmark(String url) {
        this.url = url;
        uuid=null;
    }

    @JsonCreator
    public Bookmark(@JsonProperty("url") String url,@JsonProperty("uuid") UUID uuid,@JsonProperty("version") int version) {
        this.url = url;
        this.uuid=uuid;
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Bookmark withUuid(UUID uuid) {
        return new Bookmark(url,uuid, version);
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Bookmark withUrl(String newUrl) {
        return new Bookmark(newUrl, uuid, version);
    }
}