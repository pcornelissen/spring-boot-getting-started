package com.packtpub.yummy;

import com.packtpub.yummy.model.Bookmark;
import com.packtpub.yummy.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.BasicLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("bookmarks")
public class BookmarksController {
    @Autowired
    BookmarkService bookmarkService;
    @PostMapping
    public ResponseEntity<Void> addBookmark(@RequestBody Bookmark bookmark){
        UUID uuid = bookmarkService.addBookmark(bookmark);
        return ResponseEntity.created(
                BasicLinkBuilder.linkToCurrentMapping()
                        .slash("bookmark")
                        .slash(uuid)
                        .toUri())
                .build();
    }

    @GetMapping
    public Resources<Bookmark> findAllBookmarks(){
        return new Resources<>(bookmarkService.findAll(),
                BasicLinkBuilder.linkToCurrentMapping()
                        .slash("bookmarks").withSelfRel()
                );
    }
}
