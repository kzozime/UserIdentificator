package com.otsa.useridentificator.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.otsa.useridentificator.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Laurent")))
                .andExpect(jsonPath("$[0].birthdate", is("2000-10-10")))
                .andExpect(jsonPath("$[0].country", is("FR")));
    }

    @Test
    public void testGetUserNotFound() throws Exception {
        mockMvc.perform(get("/users/201451"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetUser() throws Exception {
        mockMvc.perform(get("/users/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is("Sophie")))
                .andExpect(jsonPath("birthdate", is("2001-01-01")))
                .andExpect(jsonPath("country", is("FR")));
    }

    @Test
    public void testPostUserBadRequest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        User minorUser = new User("Phillipe", LocalDate.of(2015, 4, 23), "FR");

        mockMvc.perform(
                    post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(minorUser))
                )
                .andExpect(status().isBadRequest());

        User notFrench = new User("Rodrigez", LocalDate.of(1995, 4, 23), "MQ");
        mockMvc.perform(
                    post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(notFrench))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPostUser() throws Exception {
        User user = new User("Robert", LocalDate.of(1995, 4, 23), "FR");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mockMvc.perform(
                    post("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(user))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name", is("Robert")))
                .andExpect(jsonPath("birthdate", is("1995-04-23")))
                .andExpect(jsonPath("country", is("FR")));
    }
}

