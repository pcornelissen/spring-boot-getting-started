package com.packtpub.yummy.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public class Bookmark {
    private String description;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private String url;
    private UUID uuid;
    private int version;

    @SuppressWarnings("unused")
    public Bookmark() {

    }

    public Bookmark(String description, String url) {
        this.url = url;
        uuid = null;
        this.description = description;
    }

    @JsonCreator
    public Bookmark(@JsonProperty("uuid") UUID uuid,
                    @JsonProperty("description") String description,
                    @JsonProperty("url") String url,
                    @JsonProperty("version") int version,
                    @JsonProperty("createdOn") LocalDateTime createdOn,
                    @JsonProperty("updatedOn") LocalDateTime updatedOn) {
        this.description = description;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
        this.url = url;
        this.uuid = uuid;
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
        return new Bookmark(uuid, description, url, version, createdOn, updatedOn);
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Bookmark withUrl(String newUrl) {
        return new Bookmark(uuid, description, newUrl, version, createdOn, updatedOn);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }
}