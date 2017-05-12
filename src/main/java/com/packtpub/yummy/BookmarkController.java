package com.packtpub.yummy;

import com.packtpub.yummy.model.Bookmark;
import com.packtpub.yummy.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.UUID;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("bookmark")
public class BookmarkController {
    @Autowired
    BookmarkService bookmarkService;

    @GetMapping("{id}")
    public Resource<Bookmark> getBookmark(@PathVariable UUID id){
        return new Resource<>(bookmarkService.find(id),
                linkTo(methodOn(BookmarkController.class).getBookmark(id)).withSelfRel()
                );
    }
    @PostMapping("{id}")
    public Resource<Bookmark> updateBookmark(@PathVariable UUID id, @RequestBody Bookmark bookmark){
        return new Resource<>(bookmarkService.update(bookmark.withUuid(id)),
                linkTo(methodOn(BookmarkController.class).getBookmark(id)).withSelfRel()
                );
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteBookmark(@PathVariable UUID id){
        try {
            bookmarkService.delete(id);
            return ResponseEntity.status(HttpStatus.GONE).build();
        }catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }

}
