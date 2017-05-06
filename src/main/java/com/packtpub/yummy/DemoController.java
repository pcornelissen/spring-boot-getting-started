package com.packtpub.yummy;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping
public class DemoController {
    @RequestMapping
    public LocalDateTime sayTheTime(){
        return LocalDateTime.now();
    }
}
