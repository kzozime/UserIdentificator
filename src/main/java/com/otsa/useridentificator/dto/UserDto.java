package com.otsa.useridentificator.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
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
