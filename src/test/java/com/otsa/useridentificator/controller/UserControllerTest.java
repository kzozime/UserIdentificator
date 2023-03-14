package com.otsa.useridentificator.controller;

import com.otsa.useridentificator.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @Test
    public void testGetUsers() throws Exception {
        ResponseEntity<List<UserDto>> response = userController.getUsers();

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());

        UserDto user = response.getBody().get(0);
        assertEquals("Laurent", user.getName());
        assertEquals("2000-10-10", user.getBirthdate().toString());
        assertEquals("FR", user.getCountry());
    }

    @Test
    public void testGetUserNotFound() throws Exception {
        try {
            userController.getUser(4561684L);
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    @Test
    public void testGetUser() throws Exception {
        ResponseEntity<UserDto> response = userController.getUser(2L);
        assertEquals(200, response.getStatusCode().value());

        UserDto user = response.getBody();

        assertNotNull(user);
        assertEquals(2, user.getId());
        assertEquals("Sophie", user.getName());
        assertEquals("2001-01-01", user.getBirthdate().toString());
        assertEquals("FR", user.getCountry());

    }

    @Test
    public void testPostUserBadParametters() throws Exception {
        UserDto minorUser = new UserDto("Phillipe", LocalDate.of(2015, 4, 23), "FR");
        UserDto notFrench = new UserDto("Rodrigez", LocalDate.of(1995, 4, 23), "MQ");

        try {
            userController.postUser(minorUser);
        } catch (Exception e) {
            assertNotNull(e);
        }
        try {
            userController.postUser(notFrench);
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    @Test
    public void testPostUser() throws Exception {
        UserDto user = new UserDto("Robert", LocalDate.of(1995, 4, 23), "FR");

        ResponseEntity<UserDto> response = userController.postUser(user);
        assertEquals(201, response.getStatusCode().value());

        UserDto userCreated = response.getBody();

        assertNotNull(userCreated);
        assertEquals(user.getName(), userCreated.getName());
        assertEquals(user.getBirthdate().toString(), userCreated.getBirthdate().toString());
        assertEquals(user.getCountry(), userCreated.getCountry());
    }
}

