package com.ccjiahao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;

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

    public boolean isExpertise(String expertise) {
        return Arrays.asList(this.expertise.split(",")).contains(expertise);
    }
}
