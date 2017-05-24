package com.packtpub.yummy.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ServiceDocumentControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper mapper;

    @Test
    public void getServiceDocument() throws Exception {
        String result = mvc.perform(
                MockMvcRequestBuilders.get("/")
                        .accept("application/hal+json;charset=UTF-8")
        ).andDo(print())
                .andExpect(content()
                        .contentTypeCompatibleWith("application/hal+json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        Resource<String> value = mapper.readValue(result, new TypeReference<Resource<String>>() {
        });

        List<String> linkRels = value.getLinks().stream().map(link -> link.getRel()).collect(Collectors.toList());
        assertThat(linkRels, Matchers.hasItem("self"));
        assertEquals(value.getLink("self"), value.getId());

        assertTrue(value.hasLink("bookmarks"));
    }

    @Test
    public void validateSucceeds() throws Exception {
        ServiceDocumentController.ValidateMe vm = getValidValidateMe();
        mvc.perform(
                MockMvcRequestBuilders.post("/validate")
                        .content(new ObjectMapper().writeValueAsString(vm))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept("application/hal+json;charset=UTF-8")
        ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void validateSucceedsWithInvalidChild() throws Exception {
        ServiceDocumentController.ValidateMe vm = getValidValidateMe();
        vm.getChild().setAge(0);
        mvc.perform(
                MockMvcRequestBuilders.post("/validate")
                        .content(new ObjectMapper().writeValueAsString(vm))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept("application/hal+json;charset=UTF-8")
        ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void validateFailsWithFutureDateOfBirth() throws Exception {
        ServiceDocumentController.ValidateMe vm = getValidValidateMe();
        vm.setBirthday(vm.getNextBirthday());
        mvc.perform(
                MockMvcRequestBuilders.post("/validate")
                        .content(new ObjectMapper().writeValueAsString(vm))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept("application/hal+json;charset=UTF-8")
        ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field").value("Birthday"));
    }

    @Test
    public void validateFailsWithWrongEmail() throws Exception {
        ServiceDocumentController.ValidateMe vm = getValidValidateMe();
        vm.setEmail("IAmNoEmail@");
        mvc.perform(
                MockMvcRequestBuilders.post("/validate")
                        .content(new ObjectMapper().writeValueAsString(vm))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept("application/hal+json;charset=UTF-8")
        ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field").value("email"));
    }

    private ServiceDocumentController.ValidateMe getValidValidateMe() {
        return new ServiceDocumentController.ValidateMe(
                21,
                "name",
                5,
                new ServiceDocumentController.ValidateMe(
                        30, "name2", null, null, "email@here.there",
                        Date.from(LocalDateTime.now().plusMonths(5).toInstant(ZoneOffset.UTC)),
                        Date.from(LocalDateTime.now().minusHours(5).toInstant(ZoneOffset.UTC))
                ),
                "email@from.space",
                Date.from(LocalDateTime.now().plusMonths(5).toInstant(ZoneOffset.UTC)),
                Date.from(LocalDateTime.now().minusWeeks(5).toInstant(ZoneOffset.UTC))
        );
    }
}
