package com.packtpub.yummy.ui;

import com.packtpub.yummy.model.Bookmark;
import com.packtpub.yummy.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/bookmark/{id}")
public class BookmarkUiController {
    @Autowired
    BookmarkService bookmarkService;

    @GetMapping
    public String details(@PathVariable UUID id, @RequestParam(value = "edit", defaultValue = "false") boolean editMode,
                          Model model) {
        model.addAttribute("bookmark", bookmarkService.find(id));
        return editMode ? "bookmark/edit" : "bookmark/details";
    }

    @PostMapping
    public String saveBookmark(@PathVariable UUID uuid, Bookmark bookmark) {
        bookmark.setUuid(uuid);
        bookmarkService.update(bookmark);
        return "redirect:/";
    }

    @PostMapping("delete")
    public String delete(@PathVariable UUID id) {
        bookmarkService.delete(id);
        return "redirect:/";
    }
}
