package com.packtpub.yummy.rest;

import com.packtpub.yummy.model.BookmarkResourceAssembler;
import com.packtpub.yummy.model.Bookmark;
import com.packtpub.yummy.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping(value = "bookmark", produces = {"application/hal+json;charset=UTF-8", MediaType.APPLICATION_JSON_UTF8_VALUE})
public class BookmarkController {
    @Autowired
    BookmarkResourceAssembler assembler;
    @Autowired
    BookmarkService bookmarkService;

    @GetMapping("{id}")
    public Resource<Bookmark> getBookmark(@PathVariable UUID id) {
        return assembler.toResource(bookmarkService.find(id));
    }

    @PostMapping("{id}")
    public Resource<Bookmark> updateBookmark(@PathVariable UUID id, @RequestBody Bookmark bookmark) {
        return assembler.toResource(bookmarkService.update(bookmark.withUuid(id)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteBookmark(@PathVariable UUID id) {
        try {
            bookmarkService.delete(id);
            return ResponseEntity.status(HttpStatus.GONE).build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }

}
