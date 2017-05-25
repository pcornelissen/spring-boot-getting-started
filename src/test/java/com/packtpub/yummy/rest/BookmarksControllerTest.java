package com.packtpub.yummy.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.packtpub.yummy.model.Bookmark;
import com.packtpub.yummy.service.BookmarkService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.internal.verification.VerificationModeFactory.atLeastOnce;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookmarksControllerTest {
    @Autowired
    MockMvc mvc;
    @SpyBean
    BookmarkService bookmarkService;
    @Autowired
    ObjectMapper mapper;

    @Before
    public void setup(){
        Mockito.reset(bookmarkService);
    }

    @Test
    public void addABookmark() throws Exception {
        Bookmark value = new Bookmark("Packt publishing","http://packtpub.com");
        addBookmark(value);
        Mockito.verify(bookmarkService, atLeastOnce()).addBookmark(Mockito.any(Bookmark.class));
    }

    @Test
    public void addABookmarkFailsBecauseDescriptionIsNull() throws Exception {
        Bookmark value = new Bookmark(null,"http://packtpub.com");
        mvc.perform(
                post("/bookmarks")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(value))
        ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field").value("description"));
        Mockito.verify(bookmarkService, never()).addBookmark(Mockito.any(Bookmark.class));
    }

    @Test
    public void addABookmarkFailsBecauseUrlIsNull() throws Exception {
        Bookmark value = new Bookmark("testtest","broken://url.com");
        mvc.perform(
                post("/bookmarks")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(value))
        ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field").value("url"));
        Mockito.verify(bookmarkService, never()).addBookmark(Mockito.any(Bookmark.class));
    }

    @Test
    public void getAllBookmarks() throws Exception {
        addBookmark(new Bookmark("Packt publishing","http://packtpub.com"));
        addBookmark(new Bookmark("orchit GmbH homepage","http://orchit.de"));

        String result = mvc.perform(
                MockMvcRequestBuilders.get("/bookmarks")
                        .accept("application/hal+json;charset=UTF-8","application/json;charset=UTF-8")
        ).andDo(print())
                .andReturn().getResponse().getContentAsString();
        Resources<Bookmark> output = mapper.readValue(result, new TypeReference<Resources<Bookmark>>(){});

        assertThat(output.getContent().size(), is(greaterThanOrEqualTo(2)));
        assertTrue(output.getContent().stream()
                .anyMatch(bookmark ->
                        bookmark.getUrl().equals("http://orchit.de")));
        assertTrue(output.getContent().stream()
                .anyMatch(bookmark ->
                        bookmark.getUrl().equals("http://packtpub.com")));
    }
    private void addBookmark(Bookmark value) throws Exception {
        mvc.perform(
                post("/bookmarks")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(value))
        ).andExpect(status().isCreated());
    }
}
