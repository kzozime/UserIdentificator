package com.otsa.useridentificator.controller;

import com.otsa.useridentificator.dto.UserDto;
import com.otsa.useridentificator.model.User;
import com.otsa.useridentificator.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * List all the users
     * @return a list of User
     */
    @Operation(summary = "List all the users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "list of Users",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class)) })}
    )
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsers(){
        List<UserDto> users = userService.getUsers();

        return ResponseEntity.ok(users);
    }

    /**
     * Get a user by its id
     * @param id user id
     * @return User
     */
    @Operation(summary = "Get a user by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class)) }),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content) }
    )
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") final Long id) {
        UserDto user = userService.getUser(id);

//        if (user.isEmpty()){
//            String errorMessage = "User {"+id+"} not found.";
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage);
//        }

        return ResponseEntity.ok(user);
    }

    /**
     * Register a new user
     * @param user the user to be registered
     * @return User
     */
    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User Registered",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class)) }),
            @ApiResponse(responseCode = "400", description = "The user needs to be major AND french to be registered",
                    content = @Content)}
    )
    @PostMapping("/users")
    public ResponseEntity<UserDto> postUser(@RequestBody UserDto user) throws Exception {
//        int userAge = Period.between(user.getBirthdate(), LocalDate.now()).getYears();
//        if(userAge < 18 || !"FR".equals( user.getCountry()) ){
//            String errorMessage = "The user needs to be major AND french to be registered. \n ";
//            errorMessage += "User age : "+userAge+" y/o \n ";
//            errorMessage += "User country : "+user.getCountry()+" y/o \n";
//
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage);
//        }

        UserDto savedUser = userService.saveUser(user);
        HttpHeaders headers = new HttpHeaders();
        headers.set("location", "users/"+savedUser.getId());

        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(savedUser);
    }
}
