package ru.geekbrains.summer.dto;

import javax.persistence.Column;

public class RegUser {

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "passwordConfirmation")
    private String passwordConfirmation;

    @Column(name = "email")
    private String email;
}
