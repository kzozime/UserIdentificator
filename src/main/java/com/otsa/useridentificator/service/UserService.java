package com.otsa.useridentificator.service;


import com.otsa.useridentificator.dto.UserDto;
import com.otsa.useridentificator.mapper.UserDtoMapper;
import com.otsa.useridentificator.model.User;
import com.otsa.useridentificator.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Data
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDtoMapper mapper;

    public UserDto getUser(final Long id){
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()){
            throw new RuntimeException("User {"+id+"} not found.");
        }

        return mapper.toDto(user.get());
    }

    public List<UserDto> getUsers(){
        return mapper.usersToUserDtos((List<User>) userRepository.findAll());
    }

    public UserDto saveUser(UserDto user) throws Exception {
        int userAge = Period.between(user.getBirthdate(), LocalDate.now()).getYears();
        if(userAge < 18 || !"FR".equals( user.getCountry()) ){
            String errorMessage = "The user needs to be major AND french to be registered. \n ";
            errorMessage += "User age : "+userAge+" y/o \n ";
            errorMessage += "User country : "+user.getCountry()+" y/o \n";

            throw new Exception(errorMessage);
        }

        User entity = mapper.toEntity(user);

        return mapper.toDto(userRepository.save(entity));
    }
}
