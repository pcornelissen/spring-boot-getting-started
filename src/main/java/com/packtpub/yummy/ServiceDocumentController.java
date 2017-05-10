package com.packtpub.yummy;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.BasicLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping
public class ServiceDocumentController {
    @GetMapping("/")
    public Resource<String> getServiceDocument(){
        return new Resource<>("Yummy is the best service",
                BasicLinkBuilder.linkToCurrentMapping().withSelfRel(),
                linkTo(methodOn(BookmarksController.class).addBookmark(null)).withRel("bookmarks")
                );
    }
}
