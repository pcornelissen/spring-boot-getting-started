package com.packtpub.yummy.service;

import com.packtpub.yummy.model.Bookmark;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Created by pcpacktpub on 07/05/2017.
 */
@Service
public class BookmarkService {
    private Map<UUID, Bookmark> db=new HashMap<>();

    public UUID addBookmark(Bookmark bookmark) {
        UUID uuid = UUID.randomUUID();
        db.put(uuid,bookmark.withUuid(uuid));
        return uuid;
    }

    public Bookmark find(UUID id) {
        if(db.containsKey(id))
            return db.get(id);
        throw new NoSuchElementException();
    }

}
