package com.otsa.useridentificator.controller;

import com.otsa.useridentificator.model.User;
import com.otsa.useridentificator.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.*;
import java.util.Optional;

@RestController
public class UserController {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public Iterable<User> getUsers(){
        LOGGER.info("Listing users :");
        Instant start = Instant.now();

        Iterable<User> users = userService.getUsers();

        long processingTime = Duration.between(start, Instant.now()).toMillis();
        LOGGER.info("Request succed in " + processingTime + " milliseconds");

        return users;
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable("id") final Long id) {
        LOGGER.info("Get user with id " + id);
        Instant start = Instant.now();

        Optional<User> user = userService.getUser(id);

        if (user.isEmpty()){
            LOGGER.error("Request failed! Status code : " + HttpStatus.NOT_FOUND);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user found");
        }

        long processingTime = Duration.between(start, Instant.now()).toMillis();
        LOGGER.info("Request succed in " + processingTime + " milliseconds");

        return user.get();
    }

    @PostMapping("/user")
    public User postUser(@RequestBody User user){
        LOGGER.info("Register new user");
        Instant start = Instant.now();

        if(Period.between(user.getBirthdate(), LocalDate.now()).getYears() < 18 || !"FR".equals( user.getCountry()) ){
            LOGGER.error("Request failed! Status code : " + HttpStatus.BAD_REQUEST);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user needs to be major AND french to be registered");
        }

        long processingTime = Duration.between(start, Instant.now()).toMillis();
        LOGGER.info("Request succed in " + processingTime + " milliseconds");

        return userService.saveUser(user);
    }
}
