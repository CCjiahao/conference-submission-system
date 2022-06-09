package com.ccjiahao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private String username;
    private String name;
    private String password;
    private String email;
    private String school;
    private String country;
    private String expertise;
    private String role;
}
