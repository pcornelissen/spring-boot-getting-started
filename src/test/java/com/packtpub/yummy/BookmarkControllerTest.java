package com.packtpub.yummy;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.packtpub.yummy.model.Bookmark;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookmarkControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper mapper;

    @Test
    public void getABookmark() throws Exception {
        Bookmark input = new Bookmark("http://packtpub.com");
        String location = addBookmark(input);

        Resource<Bookmark> output = getBookmark(location);
        assertNotNull(output.getContent().getUrl());
        assertEquals(input.getUrl(), output.getContent().getUrl());
    }

    @Test
    public void deleteABookmark() throws Exception {
        Bookmark input = new Bookmark("http://packtpub.com");
        String location = addBookmark(input);

        mvc.perform(
                delete(location)
        ).andExpect(status().isGone());

        mvc.perform(
                get(location)
        ).andExpect(status().isNotFound());
    }

    @Test
    public void deleteABookmarkTwiceYieldsNotModified() throws Exception {
        Bookmark input = new Bookmark("http://packtpub.com");
        String location = addBookmark(input);

        mvc.perform(
                delete(location)
        ).andExpect(status().isGone());

        mvc.perform(
                delete(location)
        ).andExpect(status().isNotModified());
    }

    @Test
    public void updateABookmark() throws Exception {
        Bookmark input = new Bookmark("http://packtpub.com");
        String location = addBookmark(input);

        Resource<Bookmark> output = getBookmark(location);

        String result = mvc.perform(
                post(output.getId().getHref())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(output.getContent().withUrl("http://kulinariweb.de")))
        ).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        output = mapper.readValue(result, new TypeReference<Resource<Bookmark>>() {
        });

        assertEquals("http://kulinariweb.de", output.getContent().getUrl());
    }

    @Test
    public void updateABookmarkFailWrongId() throws Exception {
        Bookmark input = new Bookmark("http://packtpub.com");

        mvc.perform(
                post("/bookmark/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(input))
        ).andExpect(status().isNotFound())
                .andDo(print());
    }

    private Resource<Bookmark> getBookmark(String location) throws Exception {
        String result = mvc.perform(
                get(location)
        ).andDo(print())
                .andReturn().getResponse().getContentAsString();
        return mapper.readValue(result, new TypeReference<Resource<Bookmark>>() {
        });
    }

    private String addBookmark(Bookmark input) throws Exception {
        return mvc.perform(
                post("/bookmarks")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(input))
        ).andExpect(status().isCreated())
                .andReturn().getResponse().getHeader("Location");
    }
}
