package com.otsa.useridentificator.controller;

import com.otsa.useridentificator.model.User;
import com.otsa.useridentificator.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.*;
import java.util.Optional;

@RestController
public class UserController {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    /**
     * List all the users
     * @return a list of User
     */
    @GetMapping("/users")
    public ResponseEntity<Iterable<User>> getUsers(){
        LOGGER.info("Listing users :");
        Instant start = Instant.now();

        Iterable<User> users = userService.getUsers();

        long processingTime = Duration.between(start, Instant.now()).toMillis();
        LOGGER.info("Request succed in " + processingTime + " milliseconds");

        return ResponseEntity.ok(users);
    }

    /**
     * Get a user by id
     * @param id user id
     * @return User
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") final Long id) {
        LOGGER.info("Get user with id " + id);
        Instant start = Instant.now();

        Optional<User> user = userService.getUser(id);

        if (user.isEmpty()){
            String errorMessage = "User {"+id+"} not found.";
            LOGGER.error("Request failed!\n Status code : " + HttpStatus.NOT_FOUND + "\n message : " + errorMessage);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage);
        }

        long processingTime = Duration.between(start, Instant.now()).toMillis();
        LOGGER.info("Request succed in " + processingTime + " milliseconds");

        return ResponseEntity.ok(user.get());
    }

    /**
     * Register a new user
     * @param user the user to be registered
     * @return User
     */
    @PostMapping("/user")
    public ResponseEntity<User> postUser(@RequestBody User user){
        LOGGER.info("Register new user");
        Instant start = Instant.now();
        int userAge = Period.between(user.getBirthdate(), LocalDate.now()).getYears();
        if(userAge < 18 || !"FR".equals( user.getCountry()) ){
            String errorMessage = "The user needs to be major AND french to be registered. \n ";
            errorMessage += "User age : "+userAge+" y/o \n ";
            errorMessage += "User country : "+user.getCountry()+" y/o \n";

            LOGGER.error("Request failed!\n Status code : " + HttpStatus.BAD_REQUEST + "\n message : " + errorMessage);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage);
        }

        long processingTime = Duration.between(start, Instant.now()).toMillis();
        LOGGER.info("Request succed in " + processingTime + " milliseconds");

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(user));
    }
}
