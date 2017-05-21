package com.packtpub.yummy;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.packtpub.yummy.model.Bookmark;
import com.packtpub.yummy.service.BookmarkService;
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

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

    @Test
    public void addABookmark() throws Exception {
        Bookmark value = new Bookmark("http://packtpub.com");
        addBookmark(value);
        Mockito.verify(bookmarkService).addBookmark(Mockito.any(Bookmark.class));
    }

    @Test
    public void getAllBookmarks() throws Exception {
        addBookmark(new Bookmark("http://packtpub.com"));
        addBookmark(new Bookmark("http://orchit.de"));

        String result = mvc.perform(
                MockMvcRequestBuilders.get("/bookmarks")
                        .accept("application/hal+json;charset=UTF-8","application/json;charset=UTF-8")
        ).andDo(print())
                .andReturn().getResponse().getContentAsString();
        Resources<Bookmark> output = mapper.readValue(result, new TypeReference<Resources<Bookmark>>(){});

        assertTrue(output.getContent().size() >= 2);
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
