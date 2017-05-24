package com.packtpub.yummy;

import com.packtpub.yummy.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/bookmark")
public class BookmarkUiController {
    @Autowired
    BookmarkService bookmarkService;

    @GetMapping("{id}")
    public String helloWorld(@PathVariable UUID id, Model model){
        model.addAttribute("bookmark", bookmarkService.find(id));
        return "bookmark/details";
    }
}
