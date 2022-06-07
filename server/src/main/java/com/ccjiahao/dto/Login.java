package com.ccjiahao.dto;

import lombok.Data;

@Data
public class Login {
    private String username;
    private String password;
    private Boolean remember;
}
