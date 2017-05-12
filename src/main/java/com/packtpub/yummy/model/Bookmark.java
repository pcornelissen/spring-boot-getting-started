package com.packtpub.yummy.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import java.util.UUID;

@Entity
public class Bookmark {
    private String url;
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID uuid;

    @Version
    int version;

    @SuppressWarnings("unused")
    public Bookmark(){

    }
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

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Bookmark withUuid(UUID uuid) {
        return new Bookmark(url,uuid);
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Bookmark withUrl(String newUrl) {
        return new Bookmark(newUrl, uuid);
    }
}