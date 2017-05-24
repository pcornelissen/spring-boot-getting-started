package com.packtpub.yummy.rest;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.BasicLinkBuilder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Past;
import java.net.URISyntaxException;
import java.util.Date;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(produces = "application/hal+json;charset=UTF-8")
public class ServiceDocumentController {
    @GetMapping("/")
    public Resource<String> getServiceDocument() throws URISyntaxException {
        return new Resource<>("Yummy is the best service",
                BasicLinkBuilder.linkToCurrentMapping().withSelfRel(),
                linkTo(methodOn(BookmarksController.class).addBookmark(null)).withRel("bookmarks")
        );
    }

    @PostMapping("/validate")
    public Resource<String> validate(@RequestBody @Valid ValidateMe validateMe) {
        return new Resource<>("OK");
    }

    public static class ValidateMe {
        @Min(21)
        int age;
        @Length(min = 4, max = 6)
        String name;
        @Min(5)
        @Max(1000)
        Integer arbitraryValue;
        ValidateMe child;
        @Email
        String email;
        @Future
        Date nextBirthday;
        @Past
        Date Birthday;

        public ValidateMe(int age, String name, Integer arbitraryValue, ValidateMe child, String email, Date nextBirthday, Date birthday) {
            this.age = age;
            this.name = name;
            this.arbitraryValue = arbitraryValue;
            this.child = child;
            this.email = email;
            this.nextBirthday = nextBirthday;
            Birthday = birthday;
        }

        public ValidateMe() {
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getArbitraryValue() {
            return arbitraryValue;
        }

        public void setArbitraryValue(Integer arbitraryValue) {
            this.arbitraryValue = arbitraryValue;
        }

        public ValidateMe getChild() {
            return child;
        }

        public void setChild(ValidateMe child) {
            this.child = child;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Date getNextBirthday() {
            return nextBirthday;
        }

        public void setNextBirthday(Date nextBirthday) {
            this.nextBirthday = nextBirthday;
        }

        public Date getBirthday() {
            return Birthday;
        }

        public void setBirthday(Date birthday) {
            Birthday = birthday;
        }
    }
}
