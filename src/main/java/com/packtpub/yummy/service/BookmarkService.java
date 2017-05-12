package com.packtpub.yummy.service;

import com.packtpub.yummy.model.Bookmark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookmarkService {

    @Autowired
    BookmarkDao dao;

    public UUID addBookmark(Bookmark bookmark) {
        return dao.save(bookmark).getUuid();
    }

    public Bookmark find(UUID id) {
        return Optional.ofNullable(dao.findOne(id)).orElseThrow(
                () -> new NoSuchElementException());
    }

    public Iterable<Bookmark> findAll() {
        return dao.findAll();
    }

    public Bookmark update(Bookmark bookmark) {
        find(bookmark.getUuid());
        return dao.save(bookmark);
    }

    public void delete(UUID id) {
        find(id);
        dao.delete(id);
    }
}
