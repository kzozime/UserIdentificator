package com.otsa.useridentificator.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "flying_users")
public class User {

    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "user_name", nullable = false)
    private String name ;
    @Column(nullable = false)
    private LocalDate birthdate;
    @Column(nullable = false)
    private String country;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String genre;

    public User() {}

    public User(String name, LocalDate birthdate, String country, String number, String genre){
        this.name = name;
        this.birthdate = birthdate;
        this.country = country;
        this.phoneNumber = number;
        this.genre = genre;
    }

    public User(String name, LocalDate birthdate, String country){
        this.name = name;
        this.birthdate = birthdate;
        this.country = country;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthdate=" + birthdate +
                ", country='" + country + '\'' +
                ", number='" + phoneNumber + '\'' +
                ", genre='" + genre + '\'' +
                '}';
    }
}
