package com.otsa.useridentificator.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserDto {
    private long id;
    @NonNull
    private String name;
    @NonNull
    private LocalDate birthdate;
    @NonNull
    private String country;
    private String phoneNumber;

    private String genre;
}
