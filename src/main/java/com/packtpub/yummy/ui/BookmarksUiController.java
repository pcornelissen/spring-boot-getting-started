package com.packtpub.yummy.ui;

import com.packtpub.yummy.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class BookmarksUiController {
    @Autowired
    BookmarkService bookmarkService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public String listOfBookmarks(Model model, @RequestParam Optional<String> data){
        data.ifPresent(s -> myTestBean.setBusinessData(s));

        model.addAttribute("bookmarks", bookmarkService.findAll());

        System.out.println("BookmarksUiController: "+myTestBean.getBusinessData());
        model.addAttribute("businessData", myTestBean.getBusinessData());

        return "bookmarks/list";
    }


    @Autowired MyTestBean myTestBean;


}
