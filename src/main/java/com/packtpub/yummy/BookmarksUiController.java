package com.packtpub.yummy;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequestMapping
public class BookmarksUiController {
    @RequestMapping("/")
    public String helloWorld(Model model){
        model.addAttribute("theTime", LocalDateTime.now().toString());
        return "test";
    }
}
