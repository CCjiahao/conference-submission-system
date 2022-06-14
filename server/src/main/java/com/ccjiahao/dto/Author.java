package com.ccjiahao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Author {
    private String username;
    private String name;
    private String email;
    private String school;
    private String country;
}
